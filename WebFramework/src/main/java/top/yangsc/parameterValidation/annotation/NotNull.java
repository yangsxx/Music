package top.yangsc.parameterValidation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记为不可为空
 *
 * @author yang
 * @date 2025/5/13 20:25
 * 
 * @return 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    boolean value() default true;
}
