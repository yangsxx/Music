package top.yangsc.parameterValidation.annotation;


import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实现常用的一些入参校验
 *
 * @author yang
 * @date 2025/5/13 20:25
 * 
 * @return 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {


    /**
     * 此选项标识数据库中此字段不可重复，重复会抛异常
     * @return
     */
    boolean unique() default false;

    /**
     * 此选项标识数据库中此字段必须存在于数据库中，不存在会抛异常
     * @return
     */
    boolean absent() default false;

    /**
     * 对应的实体类
     * @return
     */
    Class<? extends Serializable> clz() ;

    /**
     * 对应表字段，不填按照小驼峰转换，例如：userName  -> user_name
     * @return
     */
    String fieldName() default "";

    /**
     * 自定义条件，会拼接where进行使用，${value}如果需要属性的值请使用此字符表示。如需使用必须填写正确的fieldName值，否则将不会生效
     * 不涉及表逻辑的请重写BaseVO方法
     * @return
     */
    String where() default "";
    String table() default "";


}
