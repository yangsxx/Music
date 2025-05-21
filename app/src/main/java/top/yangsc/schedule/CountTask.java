package top.yangsc.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yangsc.base.mapper.ExecutionLogHistoryMapper;
import top.yangsc.base.mapper.ObjectFileUrlMapper;
import top.yangsc.base.mapper.SimpleMapper;
import top.yangsc.base.pojo.ExecutionLogHistory;
import top.yangsc.base.pojo.ObjectFileUrl;
import top.yangsc.tools.TimestampUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

@Component
public class CountTask {

    @Resource
    private ExecutionLogHistoryMapper mapper;
    @Resource
    private SimpleMapper simpleMapper;

    @Resource
    private ObjectFileUrlMapper objectFileUrlMapper;

    // 添加定时注解（每天凌晨3点执行）
    @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Shanghai")
    public void count() {
        List<ExecutionLogHistory> executionLogHistory =  simpleMapper.countHistory();

        for (ExecutionLogHistory logHistory : executionLogHistory){
            logHistory.setDay(TimestampUtil.format(new Timestamp(System.currentTimeMillis())));
        }
        mapper.insertBatchSomeColumn(executionLogHistory);

        check();


    }

    public void  check(){
        List<ObjectFileUrl> objectFileUrls = objectFileUrlMapper.selectList(new LambdaQueryWrapper<ObjectFileUrl>()
                .eq(ObjectFileUrl::getChecked, false));
        for (ObjectFileUrl objectFileUrl : objectFileUrls) {
            boolean urlAccessible = isUrlAccessible(objectFileUrl.getFileLink());
            if (!urlAccessible){
                objectFileUrlMapper.deleteById(objectFileUrl);
            }
            else {
                objectFileUrl.setChecked(true);
                objectFileUrlMapper.updateById(objectFileUrl);
            }
        }
    }

    public static boolean isUrlAccessible(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }

}
