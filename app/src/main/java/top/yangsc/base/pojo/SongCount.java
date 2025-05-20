package top.yangsc.base.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述：top.yangsc.base.pojo
 *
 * @author yang
 * @date 2025/5/20 23:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "歌曲数据统计")
public class SongCount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "歌曲ID")
    private String songId;
    @Schema(description = "统计数量")
    private int count;


}
