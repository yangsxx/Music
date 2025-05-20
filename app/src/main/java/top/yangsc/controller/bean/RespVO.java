package top.yangsc.controller.bean;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 描述：top.yangsc.controller.bean
 *
 * @author yang
 * @date 2025/5/20 11:35
 */
@Data
public class RespVO {
    private Integer code;
    private String msg;
    private String data;
    private Object extra;

    public static RespVO BuilderSuccess(String data,String quality) {
        return getRespVO(data, quality, quality);
    }
    public static RespVO BuilderSuccess(String data,String quality,String quality2) {
        return getRespVO(data, quality, quality2);
    }

    @NotNull
    private static RespVO getRespVO(String data, String quality, String quality2) {
        if (StringUtils.isAllBlank(data)){
            return RespVO.BuilderFail();
        }
        RespVO respVO = new RespVO();
        respVO.setCode(0);
        respVO.setMsg("success");
        respVO.setData(data);
        respVO.setExtra(ExtraVo.Builder(quality,quality2));
        return respVO;
    }

    public static RespVO BuilderFail() {
        RespVO respVO = new RespVO();
        respVO.setCode(2);
        respVO.setMsg("failed");
        respVO.setData(null);
        return respVO;
    }
}
