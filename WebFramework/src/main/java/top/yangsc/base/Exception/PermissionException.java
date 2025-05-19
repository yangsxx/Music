package top.yangsc.base.Exception;

/**
 * 描述：top.yangsc.swiftcache.base.Exception
 * 权限异常
 *
 * @author yang
 * @date 2025/5/13 16:50
 */
public class PermissionException extends RuntimeException{
    public PermissionException(String meg) {
        super(meg);
    }
}
