package top.yangsc.login;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yangsc.base.ResultData;
import top.yangsc.base.field.RedisPreFix;
import top.yangsc.base.mapper.UserMapper;
import top.yangsc.base.pojo.Users;
import top.yangsc.controller.bean.LoginRespVO;
import top.yangsc.controller.bean.LoginVO;
import top.yangsc.controller.bean.RegisterVO;
import top.yangsc.tools.RedisUtil;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-06-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements IUserService {
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final String LOGIN_ATTEMPT_KEY = "login_attempt:";
    private static final String JWT_SECRET = "your_strong_secret_key_here"; // 替换为更强的密钥

    @Resource
    private UserMapper userMapper;


    @Override
    public ResultData<LoginRespVO> login(LoginVO loginVO) {
        // 检查登录尝试次数
        String attemptKey = LOGIN_ATTEMPT_KEY + loginVO.getPhone();
        Integer attempts = (Integer) RedisUtil.getValue(attemptKey);
        if (attempts != null && attempts >= MAX_LOGIN_ATTEMPTS) {
            throw new RuntimeException("登录尝试次数过多，请稍后再试");
        }

        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getPhone, loginVO.getPhone())
               .eq(Users::getUsed, 1);
        Users user = userMapper.selectOne(wrapper);

        if (user == null) {
            incrementLoginAttempt(attemptKey);
            throw new RuntimeException("用户名或密码错误");
        }

        // 密码验证（哈希）
        String hashedInput = MD5.create().digestHex( loginVO.getPasswd());
        if (!hashedInput.equals(user.getVoucher())) {
            incrementLoginAttempt(attemptKey);
            throw new RuntimeException("用户名或密码错误");
        }

        // 生成JWT token
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        payload.put("exp", System.currentTimeMillis() + 1000 * 60 * 60 * 72);
        String token = JWTUtil.createToken(payload, JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        // 重置登录尝试次数
        RedisUtil.delete(attemptKey);

        // 存储token到Redis
        RedisUtil.setValue(RedisPreFix.USERLONGIN + token, user, 60*72L);

        LoginRespVO respVO = new LoginRespVO();
        user.setVoucher("");
        respVO.setUser(user);
        respVO.setToken(token);

        return ResultData.ok("登录", respVO);
    }


    @Override
    public ResultData<String> register(RegisterVO registerVO) {
        Users user=new Users();
        user.setUserName(registerVO.getUserName());
        user.setPhone(registerVO.getPhone());
        user.setVoucher(MD5.create().digestHex(registerVO.getPassword()));
        userMapper.insert(user);
        return ResultData.ok("注册成功",null);
    }

    private void incrementLoginAttempt(String key) {
        RedisUtil.increment(key, 1);
        RedisUtil.expire(key, 1, TimeUnit.HOURS);
    }
}
