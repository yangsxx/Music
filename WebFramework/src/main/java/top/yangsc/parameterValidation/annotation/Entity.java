package top.yangsc.parameterValidation.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标记为实体类，用于后续的操作
 *
 * @author yang
 * @date 2025/5/13 18:47
 *
 * @return
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    /**
     *
     *
     * @author yang
     * @date 2025/5/13 10:08
     *
     * @return
     */
    Class<? extends Serializable> clz() ;
}

