package top.yangsc.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate<String, Object> template;
    public static long defineExpireTime = 3600;

    static {
        RedisTemplate<String, Object> bean = SpringContextUtil.getBean("redisTemplate");
        template = bean;
    }

    public static <T> void setValue(String key, T data) {
        setValue(key, data, defineExpireTime);
    }

    public static <T> void setValue(String key, T data, Long expireTime) {
        ValueOperations<String, T> ops = (ValueOperations<String, T>) template.opsForValue();
        ops.set(key, data, expireTime, TimeUnit.MINUTES);
    }

    public static Object getValue(String key) {
        return template.opsForValue().get(key);
    }

    public static void increment(String key, int i) {
        template.opsForValue().increment(key, i);
    }

    public static void expire(String key, int i, TimeUnit timeUnit) {
        template.expire(key, i, timeUnit);
    }

    public static void delete(String key) {
        template.delete(key);
    }

    // 新增方法

    /**
     * 批量删除key
     */
    public static void delete(Collection<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            template.delete(keys);
        }
    }

    /**
     * 设置过期时间(分钟)
     */
    public static void expireMinutes(String key, long minutes) {
        template.expire(key, minutes, TimeUnit.MINUTES);
    }

    /**
     * 设置过期时间(小时)
     */
    public static void expireHours(String key, long hours) {
        template.expire(key, hours, TimeUnit.HOURS);
    }

    /**
     * 设置过期时间(天)
     */
    public static void expireDays(String key, long days) {
        template.expire(key, days, TimeUnit.DAYS);
    }

    /**
     * 获取剩余过期时间(秒)
     */
    public static Long getExpire(String key) {
        return template.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     */
    public static Boolean hasKey(String key) {
        return template.hasKey(key);
    }

    /**
     * 批量获取值
     */
    public static <T> List<T> multiGet(List<String> keys) {
        ValueOperations<String, T> ops = (ValueOperations<String, T>) template.opsForValue();
        return ops.multiGet(keys);
    }

    /**
     * 批量设置值
     */
    public static <T> void multiSet(Map<String, T> map) {
        ValueOperations<String, T> ops = (ValueOperations<String, T>) template.opsForValue();
        ops.multiSet(map);
    }

    /**
     * 设置Hash类型值
     */
    public static <HK, HV> void putHash(String key, HK hashKey, HV value) {
        template.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量设置Hash类型值
     */
    public static <HK, HV> void putAllHash(String key, Map<HK, HV> map) {
        template.opsForHash().putAll(key, map);
    }

    /**
     * 获取Hash类型值
     */
    public static <HK, HV> HV getHash(String key, HK hashKey) {
        return (HV) template.opsForHash().get(key, hashKey);
    }

    /**
     * 获取Hash类型所有键值对
     */
    public static <HK, HV> Map<HK, HV> entriesHash(String key) {
        return (Map<HK, HV>) template.opsForHash().entries(key);
    }

    /**
     * 删除Hash类型中的键
     */
    public static <HK> void deleteHash(String key, HK... hashKeys) {
        template.opsForHash().delete(key, (Object[]) hashKeys);
    }

    /**
     * 判断Hash类型中是否存在键
     */
    public static <HK> boolean hasHashKey(String key, HK hashKey) {
        return template.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取Hash类型中所有键
     */
    public static <HK> Set<HK> hashKeys(String key) {
        return (Set<HK>) template.opsForHash().keys(key);
    }

    /**
     * 获取Hash类型中所有值
     */
    public static <HV> List<HV> hashValues(String key) {
        return (List<HV>) template.opsForHash().values(key);
    }

    /**
     * 获取Hash类型大小
     */
    public static Long hashSize(String key) {
        return template.opsForHash().size(key);
    }
}
