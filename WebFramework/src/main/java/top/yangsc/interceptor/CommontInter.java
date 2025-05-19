package top.yangsc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;



public class CommontInter implements HandlerInterceptor {
    //解决跨域问题
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,

                             Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Methods", "*");

        response.setHeader("Access-Control-Max-Age", "3600");

        response.setHeader("Access-Control-Allow-Headers",

                "Origin, X-Requested-With, Content-Type, Accept");

        return true;

    }
}
