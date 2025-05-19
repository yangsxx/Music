package top.yangsc.config;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;


/*
设置tomcat协议为apr提升并发能力
 */
@Configuration
public class PortalTomcatWebServerCustomizer implements WebServerFactoryCustomizer<WebServerFactory> {

    @Override
    public void customize(WebServerFactory factory) {
        TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) factory;
        containerFactory.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
    }

}