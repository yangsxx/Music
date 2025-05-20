package top.yangsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import top.yangsc.controller.bean.RespVO;
import top.yangsc.service.MusicService;

@RestController
@RequestMapping("/lxmusicv3/url")
@Tag(name = "lx音乐服务基础接口")
public class MusicController {
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/{source}/{songId}/{quality}")
    @Operation(summary = "获取歌曲链接")
    public RespVO queryWithQualify(@PathVariable String source, @PathVariable String songId, @PathVariable String quality) {
        return musicService.queryWithQualify(source, songId, quality);
    }

    @GetMapping("/{source}/{songId}")
    @Operation(summary = "获取歌曲链接")
    public RespVO querySong(@PathVariable String source, @PathVariable String songId) {
        return musicService.querySong(source, songId);
    }

    @GetMapping("/local/{type}")
    @Operation(summary = "type")
    public String type(@PathVariable String type) {
        return musicService.type(type);
    }

    @GetMapping("/script")
    @Operation(summary = "获取脚本")
    public String script(@RequestParam String key, @RequestParam String checkUpdate) {
        return musicService.script(key, checkUpdate);
    }

    @GetMapping("*")
    public String common() {
        return musicService.common();
    }

    @GetMapping("/repairData")
    public String repairData() {
        musicService.repairData();
        return "success";
    }
}
