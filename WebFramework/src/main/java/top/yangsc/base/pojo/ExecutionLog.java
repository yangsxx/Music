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
@Schema(description = "方法执行日志记录表")
public class ExecutionLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID，自增长")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "类名")
    private String className;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "执行时间(ms)")
    private Long executionTime;

    @Schema(description = "执行时间(ms)")
    private Long validatorTime;

    @Schema(description = "记录创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;
    @Schema(description = "查询次数")
    private Integer sqlQueryTime;
    @Schema(description = "更新次数")
    private Integer sqlUpdateTime;
    @Schema(description = "总次数")
    private Integer totalSqlTimes;

    @Schema(description = "执行结果")
    @TableField(fill = FieldFill.INSERT)
    private String systemInfo;

}
