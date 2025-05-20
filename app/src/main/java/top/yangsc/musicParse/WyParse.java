package top.yangsc.musicParse;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import top.yangsc.base.pojo.MusicMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：top.yangsc.musicParse
 *
 * @author yang
 * @date 2025/5/20 12:40
 */
public class WyParse {

    private static final String URL = "https://music.163.com/song?id=";

    public static MusicMeta getInfo(String songId) {
        HttpRequest get = HttpUtil.createGet(URL);
        String s = get.addHeaders(getHeaders()).execute().body();
        Document doc = Jsoup.parse(s);
        Element jsons = doc.select("script[type=application/ld+json]").first();
        Element duration = doc.select("meta[property=music:duration]").first();
        Element singer = doc.select("meta[property=og:music:artist]").first();
        MusicMeta musicMeta = new MusicMeta();
        if (jsons != null){
            String trim = jsons.html().trim();
            JSONObject entries = JSONUtil.parseObj(trim);
            JSONArray images = (JSONArray)entries.get("images");
            musicMeta.setDescribe(entries.getStr("description"));
            musicMeta.setPlatform(4);
            musicMeta.setPicUrl(images.getStr(0));
            musicMeta.setSongName(entries.getStr("title"));
            musicMeta.setSongId(songId);
        }
        if (duration != null){
            String content = duration.attr("content");
            musicMeta.setDuration(Integer.valueOf(content));
        }
        if (singer != null){
            String content = singer.attr("content");
            musicMeta.setSingerName(content);
        }
        return musicMeta;

    }

    private static Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"136\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"136\"");
        headers.put("sec-ch-ua-platform", "\"macOS\"");
        headers.put("host", "music.163.com");
        return headers;
    }
}
