package top.yangsc.parameterValidation;


//此接口仅作为标识使用，参数验证针对此接口的实现
public interface BaseVO {
    //扩展验证需求可以重写此方法，当条件不符合时，使用ParameterValidationException抛出信息
    default void validation(){}
}
