package top.yangsc.controller.bean;

import lombok.Data;
import top.yangsc.parameterValidation.annotation.Condition;
import top.yangsc.parameterValidation.annotation.NotNull;
import top.yangsc.parameterValidation.annotation.RegexValidator;


@Data
public class RegisterVO {

    @NotNull
    @Condition(clz = String.class,unique = true, table = "users")
    private String userName;
    /**
     * 手机号
     */
    @NotNull
    @Condition(clz = String.class,unique = true, table = "users")
    @RegexValidator(regex = "^1[3-9]\\d{9}$")
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private String email;

    /**
     * 用户头像
     */
    private String pic;
}
