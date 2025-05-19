package top.yangsc.config.ThreadLocalTools;

import top.yangsc.base.pojo.Users;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal dLocal线程上下文
 * 用于存储当前上下文信息
 *
 * @author yang
 * @date 2025/5/13 18:44
 *
 * @return
 */
public class CurrentContext {

    private static final ThreadLocal<Map<String, Object>> current = ThreadLocal.withInitial(() -> {
        Map<String, Object> map = new HashMap<>();
        map.put("sqlQueryCount", 0);
        map.put("sqlUpdateCount", 0);
        map.put("user", null);
        return map;
    });

    public static void setCurrentUser(Users user) {
        Map<String, Object> map = current.get();
        map.put("user", user);
    }
    public static Users getCurrentUser() {
        return current.get().get("user") == null? new Users().setId(10000124L) :(Users) current.get().get("user");
    }

    public static void clearSqlCount(){
        Map<String, Object> stringObjectMap = current.get();
        stringObjectMap.put("sqlQueryCount", 0);
        stringObjectMap.put("sqlUpdateCount", 0);
    }

    public static void setSqlCount(int isQuery){
        Map<String, Object> stringObjectMap = current.get();
        if (isQuery == 1){
            int i = (int)stringObjectMap.get("sqlQueryCount") + 1;
            stringObjectMap.put("sqlQueryCount", i);
        }else if (isQuery == 2){
            int j = (int)stringObjectMap.get("sqlUpdateCount") + 1;
            stringObjectMap.put("sqlUpdateCount", j);
        }
    }
    public static int getSqlQueryCount() {
        return (int) current.get().get("sqlQueryCount");
    }

    public static int getSqlUpdateCount() {
        return (int) current.get().get("sqlUpdateCount");
    }

    public static int getTotalSqlCount() {
        return getSqlQueryCount() + getSqlUpdateCount();
     }

}


