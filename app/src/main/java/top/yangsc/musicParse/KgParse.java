package top.yangsc.musicParse;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import top.yangsc.base.pojo.MusicMeta;
import top.yangsc.base.pojo.ObjectFileUrl;

import javax.validation.constraints.Max;

/**
 * 描述：top.yangsc.musicParse
 *
 * @author yang
 * @date 2025/5/20 12:42
 */
public class KgParse {

    static private String apiUrl = "http://ip.yangsc.top:3000/song/url/new?hash=";

    public static MusicMeta getInfo(String songId) {
        String s = HttpUtil.get(apiUrl + songId);
        Integer errorCode = (Integer) JSONUtil.parseObj(s).get("error_code");
        if (0 == errorCode) {
            MusicMeta musicMeta = new MusicMeta();
            JSONObject data =  JSONUtil.parseObj(s).getJSONArray("data").getJSONObject(0);
            // 找到最大的图片
            JSONArray o = (JSONArray)(data.getJSONObject("info").get("imgsize"));
            int max = 0;
            for (Object item : o){
                int size = (int)item;
                if (max == 0){
                    max = size;
                }
                if (max < size){
                    max = size;
                }
            };

            // 获取时长
            Integer anInt = data.getJSONObject("info").getInt("duration");
            musicMeta.setDuration(Math.round(anInt/1000f));
            musicMeta.setSingerName(data.getStr("singername"));
            musicMeta.setPicUrl(data.getJSONObject("info").getStr("image")
                    .replace("{size}",String.valueOf(max)));
            musicMeta.setSongName(data.getStr("name")
                    .replace(data.getStr("singername"),"")
                    .replace(" - ","").trim());
            musicMeta.setPlatform(2);
            musicMeta.setDescribe(data.getStr("albumname"));
            return musicMeta;
        }
        return null;
    }

}
