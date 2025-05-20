package top.yangsc.controller.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：top.yangsc.controller
 *
 * @author yang
 * @date 2025/5/20 11:36
 */
@Data
public class ExtraVo {
    private Boolean cache;
    private Map<String,Object> quality;
    private Map<String,Object> expire;

    public static ExtraVo Builder(String tag,  String res) {
        ExtraVo extraVo = new ExtraVo();
        Map<String,Object> q = new HashMap<>();
        q.put("target", tag);
        q.put("result", res);

        Map<String,Object> e = new HashMap<>();
        e.put("time", System.currentTimeMillis());
        e.put("expire", true);

        extraVo.setCache(false);
        extraVo.setQuality(q);
        extraVo.setExpire(e);
        return extraVo;

    }
}
