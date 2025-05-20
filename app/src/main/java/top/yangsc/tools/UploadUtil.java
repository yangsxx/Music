package top.yangsc.tools;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;


/**
 * 描述：top.yangsc.tools
 *
 * @author yang
 * @date 2025/5/20 16:07
 */
@Component
public class UploadUtil {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.endpointOutside}")
    private String endpointOutside;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件到MinIo
     * @param inputStream 文件输入流
     * @param contentType 文件类型
     * @return 文件访问URL
     */
    public String uploadFile( InputStream inputStream, String contentType ,Long fileSize,  String fileType) {
        try {
            String  objectName = UUID.randomUUID().toString()+"."+fileType;
            String format = TimestampUtil.formatMon(TimestampUtil.current());

            String pathName = format+"/"+objectName;
            // 创建MinIO客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查存储桶是否存在，不存在则创建
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(pathName)
                            .stream(inputStream, fileSize, -1)
                            .contentType(contentType)
                            .build());

            // 返回文件访问URL
            return endpointOutside + "/" + bucketName + "/" + objectName;

        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }
}