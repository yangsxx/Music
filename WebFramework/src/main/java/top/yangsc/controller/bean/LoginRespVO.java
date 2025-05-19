package top.yangsc.controller.bean;

import lombok.Data;
import top.yangsc.base.pojo.Users;
import top.yangsc.parameterValidation.BaseVO;


@Data
public class LoginRespVO implements BaseVO {
    private Users user;
    private String token;
    private int role;
}
