package top.yangsc.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.yangsc.base.mapper.MusicMetaMapper;
import top.yangsc.base.mapper.ObjectFileUrlMapper;
import top.yangsc.base.pojo.MusicMeta;
import top.yangsc.base.pojo.ObjectFileUrl;
import top.yangsc.controller.bean.ExtraVo;
import top.yangsc.controller.bean.RespVO;
import top.yangsc.musicParse.KgParse;
import top.yangsc.musicParse.KwParse;
import top.yangsc.musicParse.TxParse;
import top.yangsc.musicParse.WyParse;
import top.yangsc.schedule.mq.Producer;
import top.yangsc.service.MusicService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    private static final String API_URL = "https://88.lxmusic.xn--fiqs8s" + "/lxmusicv3/url";

    @Resource
    private MusicMetaMapper musicMetaMapper;
    @Resource
    private ObjectFileUrlMapper objectFileUrlMapper;

    @Resource
    private Producer producer;

    @Override
    public RespVO queryWithQualify(String source, String songId, String quality) {
        String requesrUrl = "/" + source + "/" + songId + "/" + quality;

        // 先查询缓存
        List<ObjectFileUrl> objectFileUrls = objectFileUrlMapper.selectList(new LambdaQueryWrapper<ObjectFileUrl>()
                .eq(ObjectFileUrl::getOriginLink, requesrUrl)
                .eq(ObjectFileUrl::getAbandon, false));
        if (objectFileUrls.size() > 0) {
            return RespVO.BuilderSuccess(objectFileUrls.get(0).getFileLink(), quality, objectFileUrls.get(0).getQualify());
        }

        // 缓存不存在，插歌曲名+歌手名+时长做md5，查询数据库
        MusicMeta musicMeta = getMusicMeta(source, songId);

        if (musicMeta != null) {
            ObjectFileUrl songUrlByLxNoMeta = getSongUrlByLxNoMeta(requesrUrl);
            return RespVO.BuilderSuccess(songUrlByLxNoMeta.getFileLink(), quality, songUrlByLxNoMeta.getQualify());
        }
        byte[] md5 = buildMd5(musicMeta);
        musicMeta.setMetaHash(md5);
        List<ObjectFileUrl> objectFileUrlList = objectFileUrlMapper.selectList(new LambdaQueryWrapper<ObjectFileUrl>()
                .eq(ObjectFileUrl::getSongMetaHash, md5)
                .eq(ObjectFileUrl::getAbandon, false));
        // 如果存在
        if (!objectFileUrlList.isEmpty()) {
            //  返回指定音质的链接
            for (ObjectFileUrl objectFileUrl : objectFileUrlList) {
                if (objectFileUrl.getQualify().equals(quality)) {
                    return RespVO.BuilderSuccess(objectFileUrl.getFileLink(), quality);
                }
            }
            // 如果存在但是不存在指定音质，则从上级源获取
            ObjectFileUrl songUrlByLx = getSongUrlByLx(requesrUrl, musicMeta);
            if (songUrlByLx != null) {
                return RespVO.BuilderSuccess(songUrlByLx.getFileLink(), quality);
            } else {
                // 如果不存在指定音质，则返回从上到下的音质
                ObjectFileUrl maxQualify = getMaxQualify(objectFileUrlList);
                return RespVO.BuilderSuccess(maxQualify.getFileLink(), quality);
            }
        } else {
            ObjectFileUrl songUrlByLx = getSongUrlByLx(requesrUrl, musicMeta);
            return RespVO.BuilderSuccess(songUrlByLx.getFileLink(), quality);
        }

    }

    @Override
    public RespVO querySong(String source, String songId) {
        return queryWithQualify(source, songId, "flac");
    }

    @Override
    public String type(String type) {
        return HttpUtil.get(API_URL + "/" + type);
    }

    @Override
    public String script(String key, String checkUpdate) {
        return HttpUtil.get(API_URL + "/script?key=" + key + "&checkUpdate=" + checkUpdate);
    }

    @Override
    public String common() {
        return "{\n" +
                "\t\"code\": 0,\n" +
                "\t\"msg\": \"success\",\n" +
                "\t\"data\": null\n" +
                "}";
    }

    private byte[] buildMd5(MusicMeta musicMeta) {
        String Info = musicMeta.getSongName() + musicMeta.getSingerName() + musicMeta.getDuration();
        String s = MD5.create().digestHex(Info);
        return s.getBytes();
    }

    private ObjectFileUrl getMaxQualify(List<ObjectFileUrl> objectFileUrlList) {
        int max = 0;
        int index = 0;
        for (int i = 0; i < objectFileUrlList.size(); i++) {
            String qualify = objectFileUrlList.get(i).getQualify();
            int q = 0;
            switch (qualify) {
                case "flac24bit":
                    q = 4;
                    break;
                case "flac":
                    q = 3;
                    break;
                case "320k":
                    q = 2;
                    break;
                default:
                    q = 1;
            }
            if (q > max) {
                max = q;
                index = i;
            }
        }
        return objectFileUrlList.get(index);

    }

    private MusicMeta getMusicMeta(String platform, String songId) {
        if (platform.equals("kw")) {
            return KwParse.getInfo(songId);
        } else if (platform.equals("tx")) {
            return TxParse.getInfo(songId);
        } else if (platform.equals("wy")) {
            return WyParse.getInfo(songId);
        } else if (platform.equals("kg")) {
            return KgParse.getInfo(songId);
        }
        return null;
    }

    private ObjectFileUrl getSongUrlByLx(String originLink, MusicMeta musicMeta) {
        MusicMeta musicMeta1 = musicMetaMapper.selectOne(
                new LambdaQueryWrapper<MusicMeta>()
                        .eq(MusicMeta::getMetaHash, musicMeta.getMetaHash()));
        if (musicMeta1 == null) {
            musicMetaMapper.insert(musicMeta);
        }

        String s = HttpUtil.get(API_URL + originLink);
        RespVO bean = JSONUtil.toBean(s, RespVO.class);

        if (bean != null && bean.getCode() == 0) {
            JSONObject extra = (JSONObject) bean.getExtra();
            String result = extra.getJSONObject("quality").getStr("result");
            ObjectFileUrl objectFileUrl = new ObjectFileUrl();
            objectFileUrl.setOriginLink(originLink);
            objectFileUrl.setQualify(result);
            objectFileUrl.setSongMetaHash(musicMeta.getMetaHash());
            objectFileUrl.setFileLink(bean.getData());
            producer.DownloadTaskProducer(JSONUtil.toJsonStr(objectFileUrl));
            return objectFileUrl;
        }
        return new ObjectFileUrl();
    }

    private ObjectFileUrl getSongUrlByLxNoMeta(String originLink) {

        String s = HttpUtil.get(API_URL + originLink);
        RespVO bean = JSONUtil.toBean(s, RespVO.class);

        if (bean != null && bean.getCode() == 0) {
            JSONObject extra = (JSONObject) bean.getExtra();
            String result = extra.getJSONObject("quality").getStr("result");
            ObjectFileUrl objectFileUrl = new ObjectFileUrl();
            objectFileUrl.setOriginLink(originLink);
            objectFileUrl.setQualify(result);
            objectFileUrl.setSongMetaHash("0000000000".getBytes());
            objectFileUrl.setFileLink(bean.getData());
            producer.DownloadTaskProducer(JSONUtil.toJsonStr(objectFileUrl));
            return objectFileUrl;
        }
        return new ObjectFileUrl();
    }

    public void repairData() {

        //修复没有匹配信息的歌曲
        byte[] bytes = "0000000000".getBytes();

        List<ObjectFileUrl> objectFileUrls = objectFileUrlMapper.selectList(new LambdaQueryWrapper<ObjectFileUrl>()
                .eq(ObjectFileUrl::getSongMetaHash, bytes)
                .eq(ObjectFileUrl::getAbandon, false));

        if (!objectFileUrls.isEmpty()){
            for (ObjectFileUrl objectFileUrl : objectFileUrls) {
                String originLink = objectFileUrl.getOriginLink();
                String[] split = originLink.split("/");
                if ("kg".equals(split[1])) {
                    MusicMeta info = KgParse.getInfo(split[2]);
                    byte[] bytes1 = buildMd5(info);
                    // 更新md5
                    objectFileUrl.setSongMetaHash(bytes1);
                    objectFileUrlMapper.updateById(objectFileUrl);

                    info.setMetaHash(bytes1);
                    musicMetaMapper.insert(info);
                }

            }
        }



        //修复图片获取失败的歌曲
        List<MusicMeta> musicMetas = musicMetaMapper.selectList(new LambdaQueryWrapper<MusicMeta>());
        for (MusicMeta musicMeta : musicMetas) {

            List<ObjectFileUrl> objectFileUrl = objectFileUrlMapper.selectList(new LambdaQueryWrapper<ObjectFileUrl>()
                    .eq(ObjectFileUrl::getSongMetaHash, musicMeta.getMetaHash()));

            String s = objectFileUrl.getFirst().getOriginLink().split("/")[2];
            String s1 = objectFileUrl.getFirst().getOriginLink().split("/")[1];
            musicMeta.setSongId(s);
            if (StringUtils.isBlank(musicMeta.getPicUrl())){
               if ("kg".equals(s1)){
                   musicMeta.setPicUrl(KgParse.getInfo(s).getPicUrl());
               }
               if ("wy".equals(s1)){
                   musicMeta.setPicUrl(WyParse.getInfo(s).getPicUrl());
               }
               if ("kw".equals(s1)){
                   musicMeta.setPicUrl(KwParse.getInfo(s).getPicUrl());
               }
           }
            musicMetaMapper.updateById(musicMeta);
        }

    }
}
