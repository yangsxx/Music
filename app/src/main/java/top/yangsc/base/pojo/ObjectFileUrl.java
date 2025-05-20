package top.yangsc.base.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "音乐文件对应对象存储URL地址")
public class ObjectFileUrl implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "音乐请求的原始链接")
    private String originLink;

    @Schema(description = "文件资源URL链接")
    private String fileLink;

    @Schema(description = "记录创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;

    @Schema(description = "是否弃用")
    private Boolean abandon;

    @Schema(description = "16字节固定长度的MD5哈希值(二进制格式)，用于快速比对")
    private byte[] songMetaHash;

    @Schema(description = "元数据信息ID")
    private Long metaInfoId;

    @Schema(description = "歌曲质量")
    private String qualify;
}
