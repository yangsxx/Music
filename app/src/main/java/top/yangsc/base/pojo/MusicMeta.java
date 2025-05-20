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
@Schema(description = "音乐元数据表")
public class MusicMeta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "歌曲名")
    private String songName;

    @Schema(description = "歌手名")
    private String singerName;

    @Schema(description = "歌曲时长，秒")
    private Integer duration;

    @Schema(description = "信息字符串哈希，认为歌曲名、歌手、歌曲时长均相同则为同一首歌")
    private byte[] metaHash;

    @Schema(description = "平台: 1-酷我 2-酷狗 3-qq 4-网易云 5-其他")
    private Integer platform;

    @Schema(description = "记录创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;

    @Schema(description = "专辑描述")
    private String describe;

    @Schema(description = "专辑封面")
    private String picUrl;
}
