package top.yangsc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import top.yangsc.schedule.CountTask;
import top.yangsc.schedule.mq.Producer;

import javax.annotation.Resource;
import java.net.http.HttpRequest;

/**
 * 描述：top.yangsc.controller
 *
 * @author yang
 * @date 2025/5/21 10:37
 */

@RestController
@RequestMapping("/music/")
@Tag(name = "文件请求统计")
public class CountController {

    private final String API_URL = "http://ip.yangsc.top:9000/music/";

    @Resource
    private Producer  task;
    @GetMapping("/{data}/{url}")
    public RedirectView count(@PathVariable String url, @PathVariable String data) {
         String  toUrl = API_URL + data+"/"+url;
        task.CountTaskProducer(toUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(toUrl);
        return redirectView;
    }
}
