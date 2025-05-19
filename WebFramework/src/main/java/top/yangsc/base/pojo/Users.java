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
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "登录凭证")
    private String voucher;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户头像")
    private String pic;

    @Schema(description = "创建时间")
    @TableField( fill = FieldFill.INSERT)
    private Timestamp createdAt;

    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updatedAt;

    @Schema(description = "账户可用状态：0：注销 1：可用 2：停用")
    private Integer used;
}