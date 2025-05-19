package top.yangsc.base.field;

/**
 * 描述：top.yangsc.swiftcache.base.emun
 *
 * @author yang
 * @date 2025/5/13 16:54
 */

public class HttpCode {
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "成功";

    public static final int UNAUTHORIZED_CODE = 401;
    public static final String UNAUTHORIZED_MSG = "未授权/未登录";

    public static final int FORBIDDEN_CODE = 403;
    public static final String FORBIDDEN_MSG = "无权限访问";

    public static final int METHOD_NOT_ALLOWED_CODE = 405;
    public static final String METHOD_NOT_ALLOWED_MSG = "参数校验异常";

    public static final int INTERNAL_SERVER_ERROR_CODE = 500;
    public static final String INTERNAL_SERVER_ERROR_MSG = "服务器内部错误";

    private HttpCode() {} // 私有构造防止实例化


}
