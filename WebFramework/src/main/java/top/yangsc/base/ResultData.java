package top.yangsc.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author yan.zhou
 * @date 2022/1/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData<T>{
    private Integer errno;
    private String msg;
    private T data;

    public static <T> ResultData<T> ok(T data){
        return new ResultData<T>(200, "", data);
    }
    public static <T> ResultData<T> ok(String msg ,T data){
        return new ResultData<>(200, msg, data);
    }

    public static ResultData<String> error(String errmsg){
        return new ResultData<>(500, errmsg, null);
    }

    public static ResultData<String> notLogin(){
        return new ResultData<>(401, "未登录！", null);
    }

    public static ResultData<String> paramException(String errmsg){
        return new ResultData<>(405, errmsg, null);
    }

    public static ResultData<String> Exception(Integer code,String errmsg){
        return new ResultData<>(code, errmsg, null);
    }


}
