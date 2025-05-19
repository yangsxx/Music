package top.yangsc.parameterValidation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正则校验
 *
 * @author yang
 * @date 2025/5/13 18:49
 *
 * @return
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexValidator {
    String regex();
}
