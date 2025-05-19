package top.yangsc.config.ThreadLocalTools.sqlCount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：top.yangsc.swiftcache.config.ThreadLocalTools.sqlCount
 *
 * @author yang
 * @date 2025/5/13 19:55
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public SqlCountQuery sqlCountQuery() {
        return new SqlCountQuery();
    }

    @Bean
    public SqlCountUpdate sqlCountUpdate() {
        return new SqlCountUpdate();
    }
}
