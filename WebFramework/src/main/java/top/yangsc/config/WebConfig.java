package top.yangsc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yangsc.interceptor.LoginInterceptor;


@Configuration
//定制SpringMVC的一些功能都使用WebMvcConfigurer
public class WebConfig implements WebMvcConfigurer {
    String[] excludes={"/user/login",
            "/web/**",
            "/favicon.ico",
            "/user/register",
            "/demo/**",
            "/link/**",
            "/word.dic",
            "/common/**"};



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(excludes)
                // 新增以下Swagger 3.x和Knife4j的放行路径
                .excludePathPatterns(
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "swagger-resources",
                        "/v3/api-docs/**",
                        "/favicon.ico",
                        "/error"
                )
                .addPathPatterns("/**");
    }


}
