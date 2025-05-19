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

/**
 * 描述：top.yangsc.swiftcache.base.pojo
 *
 * @author yang
 * @date 2025/5/16 20:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "方法执行日志统计表")
public class ExecutionLogHistory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID，自增长")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "执行日期")
    private String day;

    @Schema(description = "平均时间")
    private Long avgTime;
    @Schema(description = "平均时间")
    private Long maxTime;

    @Schema(description = "执行次数")
    private Integer times;

    @Schema(description = "记录创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;

}
