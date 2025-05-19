package top.yangsc.login;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yangsc.base.ResultData;
import top.yangsc.base.pojo.Users;
import top.yangsc.controller.bean.LoginRespVO;
import top.yangsc.controller.bean.LoginVO;
import top.yangsc.controller.bean.RegisterVO;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-19
 */
public interface IUserService extends IService<Users> {

    ResultData<LoginRespVO> login(LoginVO loginVO);

    ResultData register(RegisterVO registerVO);
}
