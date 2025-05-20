package top.yangsc.schedule.mq;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.yangsc.base.mapper.ObjectFileUrlMapper;
import top.yangsc.base.pojo.ObjectFileUrl;
import top.yangsc.tools.UploadUtil;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Consumer {
    @Resource
    private UploadUtil uploadUtil;

    @Resource
    private ObjectFileUrlMapper objectFileUrlMapper;

    @RabbitListener(queues = "downloadTask.queue")
    public void receiveMessage(String message) {
        try {
            ObjectFileUrl entries = JSONUtil.toBean(message, ObjectFileUrl.class);
            String url = entries.getFileLink();
            String[] split = url.split("\\.");

            // 创建HTTP连接并设置缓冲区
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            // 获取文件大小和类型
            long fileSize = connection.getContentLengthLong();
            String contentType = connection.getContentType();

            String minioUrl="";
            // 使用缓冲区下载文件
            try (InputStream inputStream = new BufferedInputStream(connection.getInputStream(), 8192)) {
                 minioUrl = uploadUtil.uploadFile(inputStream, contentType ,fileSize,split[split.length-1]);
            }
            entries.setFileLink(minioUrl);
            objectFileUrlMapper.insert(entries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
