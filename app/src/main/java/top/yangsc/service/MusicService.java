package top.yangsc.service;

import top.yangsc.controller.bean.RespVO;

public interface MusicService {
    RespVO queryWithQualify(String source, String songId, String quality);
    RespVO querySong(String source, String songId);
    String type(String type);
    String script(String key, String checkUpdate);
    String common();
    public void repairData();
}
