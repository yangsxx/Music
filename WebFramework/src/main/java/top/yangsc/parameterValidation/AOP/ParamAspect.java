package top.yangsc.parameterValidation.AOP;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.yangsc.base.mapper.ExecutionLogMapper;
import top.yangsc.base.pojo.ExecutionLog;
import top.yangsc.config.ThreadLocalTools.CurrentContext;
import top.yangsc.tools.ObjectUtil;
import top.yangsc.tools.SpringContextUtil;
import javax.annotation.Resource;



@Aspect
@Component
public class ParamAspect {
    // 添加包路径常量
    private static final String VO_PACKAGE = "top.yangsc.controller.bean";

    @Resource
    private Validator validator;

    @Pointcut("execution(* top.yangsc.controller.*Controller.*(..))")
    public void paramPointCut(){

    }

    @Around("paramPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        CurrentContext.clearSqlCount();
        Object[] args = joinPoint.getArgs();
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取类名 方法名
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getName();


        Object o = null;
        if (args.length>0){
                for (Object arg : args) {
                        Class voClass = null;
                        try {
                            voClass = getVOClass(arg);
                        }catch (Exception e){
                        }
                    // 修改验证调用逻辑
                    if (voClass != null
                            && voClass.getPackage() != null
                            && voClass.getPackage().getName().startsWith(VO_PACKAGE)) {
                        validator.doValidator(voClass, arg);
                    }
                }
            }
        Long validatorTime = System.currentTimeMillis();
            o = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        recordRuntime(endTime-startTime,validatorTime-startTime,declaringTypeName,name);
        return o;

    }
    private Class getVOClass(Object o) {
        return ObjectUtil.getClassByObject(o);
    }
    private void recordRuntime(Long time,long  validatorTime,String clazzName,String methodName) {
        ExecutionLogMapper bean = SpringContextUtil.getBean(ExecutionLogMapper.class);
        ExecutionLog executionLog = new ExecutionLog();
        executionLog.setClassName(clazzName);
        executionLog.setMethodName(methodName);
        executionLog.setExecutionTime(time);
        executionLog.setValidatorTime(validatorTime);
        executionLog.setSqlQueryTime(CurrentContext.getSqlQueryCount());
        executionLog.setSqlUpdateTime(CurrentContext.getSqlUpdateCount());
        executionLog.setTotalSqlTimes(CurrentContext.getTotalSqlCount());
        bean.insert(executionLog);

    }

}

