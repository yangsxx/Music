package top.yangsc.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yangsc.base.Exception.ParameterValidationException;
import top.yangsc.base.Exception.PermissionException;
import top.yangsc.base.ResultData;
import top.yangsc.base.field.HttpCode;


import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(ParameterValidationException.class)
    public ResultData<String> ParameterValidationExceptionCatcher(ParameterValidationException exception){
        return ResultData.paramException(StringUtils.isEmpty(exception.getMessage())?"系统繁忙中，请稍后再试":"参数校验异常："+exception.getMessage());
    }
    @ExceptionHandler(PermissionException.class)
    public ResultData<String> PermissionCatcher(PermissionException exception){
        exception.fillInStackTrace();
        exception.printStackTrace();
        return ResultData.Exception(HttpCode.FORBIDDEN_CODE,StringUtils.isEmpty(exception.getMessage())?"系统繁忙中，请稍后再试":"权限异常："+exception.getMessage());

    }
    @ExceptionHandler(SQLException.class)
    public ResultData<String> SqlExceptionCatcher(SQLException exception){
        exception.fillInStackTrace();
        exception.printStackTrace();
        return ResultData.error("数据库异常，请联系管理员处理");
    }


    @ExceptionHandler(RuntimeException.class)
    public ResultData<String> runtimeExceptionCatcher(RuntimeException exception){
        exception.fillInStackTrace();
        exception.printStackTrace();
        return ResultData.error(StringUtils.isEmpty(exception.getMessage())?"系统繁忙中，请稍后再试":exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResultData<String> exceptionCatcher(Exception exception){
        exception.fillInStackTrace();
        exception.printStackTrace();
        return ResultData.error(StringUtils.isEmpty(exception.getMessage())?"系统繁忙中，请稍后再试":exception.getMessage());
    }

}
