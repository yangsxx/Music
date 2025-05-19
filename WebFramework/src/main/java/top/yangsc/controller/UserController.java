package top.yangsc.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangsc.base.ResultData;
import top.yangsc.login.IUserService;
import top.yangsc.controller.bean.LoginRespVO;
import top.yangsc.controller.bean.LoginVO;
import top.yangsc.controller.bean.RegisterVO;


import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResultData<LoginRespVO> login(@RequestBody LoginVO loginVO){

        return userService.login(loginVO);
    }
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResultData register(@RequestBody RegisterVO registerVO){

        return userService.register(registerVO);
    }

}
