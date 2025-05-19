package top.yangsc.config.ThreadLocalTools.sqlCount;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import top.yangsc.config.ThreadLocalTools.CurrentContext;

import java.sql.Statement;

/**
 * 描述：top.yangsc.swiftcache.config
 *
 * @author yang
 * @date 2025/5/13 19:21
 */
// 添加MyBatis拦截器
@Intercepts({
        @Signature(type= StatementHandler.class, method="query", args={Statement.class, ResultHandler.class})
})
public class SqlCountQuery implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        CurrentContext.setSqlCount(1);
        return invocation.proceed();
    }



}
