package top.yangsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yangsc.version.GitVersionService;


import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/common")
@Tag(name ="公共接口")
public class VersionController {
    
    @Resource
    private GitVersionService gitVersionService;

    @GetMapping("/version")
    @Operation(summary = "获取版本号")
    public ResponseEntity<Map<String,String>> getVersion() {
        return ResponseEntity.ok(gitVersionService.getVersion());
    }

    @GetMapping("/thread-check")
    public String threadCheck() {
        Thread thread = Thread.currentThread();
        return String.format("Thread: %s, isVirtual: %b", thread, thread.isVirtual());
    }

    @PostMapping("/test/{url}")
    public void test(String name, @PathVariable String url){
        System.out.println(name);
        System.out.println(url);
    }
}
