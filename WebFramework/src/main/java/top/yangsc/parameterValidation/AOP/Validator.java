package top.yangsc.parameterValidation.AOP;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yangsc.base.Exception.ParameterValidationException;
import top.yangsc.base.mapper.SimpleMapper;
import top.yangsc.parameterValidation.BaseVO;
import top.yangsc.parameterValidation.annotation.Condition;
import top.yangsc.parameterValidation.annotation.Entity;
import top.yangsc.parameterValidation.annotation.NotNull;
import top.yangsc.parameterValidation.annotation.RegexValidator;
import top.yangsc.tools.TableUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 参数验证器，负责校验传输层对象的字段是否符合注解定义的规则
 */
@Component
public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    // 缓存字段元数据（字段和注解）
    private static final Cache<Class<?>, Field[]> fieldCache = Caffeine.newBuilder()
            .maximumSize(100)
            .build();
    private static final Cache<Field, Annotation[]> annotationCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();
    // 缓存预编译正则表达式
    private static final Cache<String, Pattern> regexCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();

    private final SimpleMapper mapper;

    @Autowired
    public Validator(SimpleMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 验证对象的字段是否符合注解规则
     *
     * @param clazz    对象类
     * @param instance 对象实例
     * @throws ParameterValidationException 如果验证失败
     */
    public void doValidator(Class<?> clazz, Object instance) {
        if (instance == null) {
            throw new ParameterValidationException("验证对象不能为空");
        }

        // 从缓存获取字段，减少反射调用
        Field[] fields = fieldCache.get(clazz, clz -> clz.getDeclaredFields());
        List<ConditionCheck> conditionChecks = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true); // 直接设置可访问，无需缓存
            Object fieldValue;
            try {
                fieldValue = field.get(instance);
            } catch (IllegalAccessException e) {
                log.error("Failed to access field: {}", field.getName(), e);
                throw new ParameterValidationException("无法访问字段: " + field.getName());
            }

            // 从缓存获取注解
            Annotation[] annotations = annotationCache.get(field, Field::getAnnotations);
            for (Annotation annotation : annotations) {
                if (annotation instanceof Entity entity) {
                    if (fieldValue != null) {
                        doValidator(entity.clz(), fieldValue);
                    }
                } else if (annotation instanceof NotNull notNull) {
                    if (notNull.value() && isEmpty(fieldValue)) {
                        throw new ParameterValidationException(field.getName() + "字段不可为空");
                    }
                } else if (annotation instanceof Condition condition) {
                    if (fieldValue != null) {
                        String CName = condition.fieldName().isBlank() ?
                                TableUtil.toTab(field.getName()) : condition.fieldName();
                        String TabName = condition.table();
                        // 收集需要检查的条件，延迟数据库查询
                        if (condition.unique() || condition.absent()) {
                            conditionChecks.add(new ConditionCheck(TabName, condition, String.valueOf(fieldValue),CName));
                        }
                        if (!StringUtils.isEmpty(condition.where())) {
                            if (mapper.isExistByWhere(TableUtil.toTab(CName), condition.where())) {
                                throw new ParameterValidationException(field.getName() + "不符合参数条件");
                            }
                        }
                    }
                } else if (annotation instanceof RegexValidator regexValidator) {
                    String regex = regexValidator.regex();
                    if (!StringUtils.isEmpty(regex) && fieldValue != null) {
                        Pattern pattern = regexCache.get(regex, Pattern::compile);
                        if (!pattern.matcher(fieldValue.toString()).matches()) {
                            throw new ParameterValidationException(field.getName() + "参数格式不规则");
                        }
                    }
                }
            }
        }

        // 批量检查唯一性和存在性
        if (!conditionChecks.isEmpty()) {
            checkConditions(conditionChecks);
        }

        // 验证 BaseVO 的自定义逻辑
        if (instance instanceof BaseVO baseVO) {
            try {
                baseVO.validation();
            } catch (Exception e) {
                log.error("BaseVO validation failed for {}", instance.getClass().getSimpleName(), e);
                throw new ParameterValidationException("BaseVO验证失败: " + e.getMessage());
            }
        }
    }

    /**
     * 检查字段值是否为空，支持多种类型
     */
    private boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String str) {
            return StringUtils.isEmpty(str);
        }
        if (value instanceof Collection<?> collection) {
            return collection.isEmpty();
        }
        if (value instanceof Map<?, ?> map) {
            return map.isEmpty();
        }
        return false;
    }

    /**
     * 批量检查唯一性和存在性条件
     */
    private void checkConditions(List<ConditionCheck> checks) {
        // 假设 SimpleMapper 提供批量查询方法
        for (ConditionCheck check : checks) {
            boolean exists = mapper.isExist(check.tableName, check.CName, check.value);
            if (check.condition.unique() && exists) {
                throw new ParameterValidationException(check.tableName + "已存在相同的数据");
            }
            if (check.condition.absent() && !exists) {
                throw new ParameterValidationException(check.tableName + "数据不存在");
            }
        }
    }

    /**
     * 内部类，用于存储条件检查信息
     */
    private static class ConditionCheck {
        String tableName;
        Condition condition;
        String value;
        String CName;

        ConditionCheck(String tableName, Condition condition, String value, String CName) {
            this.tableName = tableName;
            this.condition = condition;
            this.value = value;
            this.CName = CName;
        }
    }
}