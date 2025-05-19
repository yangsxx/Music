package top.yangsc.tools;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectUtil {
//    public static Resource[] resources = new Resource[0];
//    public static Resource[] resourcesVO = new Resource[0];
//
//    // 添加缓存避免重复加载类
//    private static final ConcurrentHashMap<String, Class<?>> classCache = new ConcurrentHashMap<>();
//    // 复用元数据读取工厂（主要性能优化点）
//    private static final SimpleMetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
//
//    static {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            // 更精确的资源匹配模式
//            resources = resolver.getResources("classpath*:top/yangsc/swiftcache/**/*.class");
//            resourcesVO = resolver.getResources("classpath*:top/yangsc/swiftcache/**/VO*.class");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    //通过对象和类路径扫描，获取class对象
    // 通过反射对象获取class的优化实现
    public static Class<?> getClassByObject(Object o) {
        if (o == null) return null;

        // 处理Spring代理对象的情况
        Class<?> clazz = o.getClass();
        if (org.springframework.aop.support.AopUtils.isAopProxy(o)) {
            clazz = org.springframework.aop.support.AopUtils.getTargetClass(o);
        }
        return clazz;
    }

    public static <T> T getObjectByClass(Object o,Class<T> clz){
        if (clz.isInstance(o)){
            return (T)o;
        }
        return null;
    }

}

