package top.yangsc.controller.bean;

import lombok.Data;
import top.yangsc.parameterValidation.annotation.NotNull;
import top.yangsc.parameterValidation.annotation.RegexValidator;


@Data
public class LoginVO  {

    @NotNull(value = true)
    @RegexValidator(regex = "^1[3-9]\\d{9}$")
    private String phone;
    @NotNull(value = true)
    private String passwd;
}
