package top.yangsc.musicParse;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.yangsc.base.pojo.MusicMeta;
import top.yangsc.tools.ParseScript;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：top.yangsc.musicParse
 *
 * @author yang
 * @date 2025/5/20 11:50
 */
public class KwParse {

    private static final String SCRIPT_URL = "https://www.kuwo.cn/play_detail/";

    public static void main(String[] args) {
        getInfo("284892811");
    }
    public static MusicMeta getInfo(String songId) {

        HttpRequest get = HttpUtil.createGet(SCRIPT_URL + songId);
        String s = get.addHeaders(getHeaders()).execute().body();
        Document doc = Jsoup.parse(s);
        Elements scripts = doc.getElementsByTag("script");
        String scriptContent = "";
        Element elementById = doc.getElementById("songinfo-name");
        String songName = elementById.attr("value");

        for (Element script : scripts) {
            String content = script.html();
            if (content.contains("window.__NUXT__") && content.contains("albuminfo:")) {
                scriptContent = content;
                break;
            }
        }

        String[] songinfos = ParseScript.parseToJson(scriptContent, "songinfo");
        if (songinfos == null || songinfos.length < 1) {
            return null;
        }
        JSONObject entries = JSONUtil.parseObj(songinfos[0]);
        MusicMeta musicMeta = new MusicMeta();
        musicMeta.setSongName(songName);
        musicMeta.setSingerName(entries.getStr("artist"));
        musicMeta.setDuration(entries.getInt("duration"));
        musicMeta.setDescribe(entries.getStr("albuminfo"));
        musicMeta.setPicUrl(entries.getStr("pic120"));
        musicMeta.setPlatform(1);

        return musicMeta;
    }

    private static Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");
        headers.put("host", "www.kuwo.cn");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"136\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"136\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"macOS\"");

        return headers;
    }
}
