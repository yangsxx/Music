package top.yangsc.cache;

import top.yangsc.base.field.RedisPreFix;
import top.yangsc.base.mapper.UserMapper;
import top.yangsc.base.pojo.Users;
import top.yangsc.tools.RedisUtil;
import top.yangsc.tools.SpringContextUtil;

/**
 * 描述：top.yangsc.swiftcache.base.cache
 *
 * @author yang
 * @date 2025/5/13 18:08
 */

public class FindUserWithCache {

    /**
     * 通过redis查询用户信息，具有简单的更新机制
     *
     * @author yang
     * @date 2025/5/13 18:42
     *
     * @return
     */
    public static Users findUserById(Long idLong) {
        String id = idLong.toString();

        //  判断缓存是否存在，不存在则查询数据库并放入缓存
        if (!RedisUtil.hasKey(RedisPreFix.USERINFOKEY)
            || !RedisUtil.hasKey(RedisPreFix.USERISUPDATEKEY)
            || !RedisUtil.hasHashKey(RedisPreFix.USERISUPDATEKEY,id)
            || !RedisUtil.hasHashKey(RedisPreFix.USERINFOKEY,id)
            || (boolean)(RedisUtil.getHash(RedisPreFix.USERISUPDATEKEY,id))) {

            Users users = getUserById(idLong);
            RedisUtil.putHash(RedisPreFix.USERINFOKEY,id, users);
            RedisUtil.putHash(RedisPreFix.USERISUPDATEKEY,id, false);
            return users;
        }
        else {
            return RedisUtil.getHash(RedisPreFix.USERINFOKEY, id);
        }

    }

    private static Users getUserById(Long id) {
        // 从数据库中查询用户信息,因为static无法注入，所以只能通过SpringContextUtil获取bean
        UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
        Users users = userMapper.selectById(id);
        return users;
    }
}
