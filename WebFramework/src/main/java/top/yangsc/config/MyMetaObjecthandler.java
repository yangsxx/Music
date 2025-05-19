package top.yangsc.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.yangsc.base.mapper.CommonMapper;
import top.yangsc.config.ThreadLocalTools.CurrentContext;
import top.yangsc.tools.SpringContextUtil;
import top.yangsc.tools.getSystemInfo;


import java.sql.Timestamp;

/**
 * 自定义元数据对象处理器
 * 自动填充字段
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {




    /**
     * 插入操作，自动填充
     *
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        CommonMapper mapper = SpringContextUtil.getBean(CommonMapper.class);
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());

        // 只填充存在的字段
        if (metaObject.hasGetter("createdAt")) {
            metaObject.setValue("createdAt", new Timestamp(System.currentTimeMillis()));
        }
        if (metaObject.hasGetter("id")) {
            metaObject.setValue("id", mapper.getId());
        }
        if (metaObject.hasGetter("userId")) {
                metaObject.setValue("userId", getUserId());
        }
        if (metaObject.hasGetter("createdBy") ) {
            metaObject.setValue("createdBy", getUserId());
        }
        if (metaObject.hasGetter("systemInfo")) {
            metaObject.setValue("systemInfo", getSystemInfo.getSySInfo());
        }

    }

    /**
     * 更新操作，自动填充
     *
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());

        // 只填充存在的字段
        if (metaObject.hasGetter("updatedAt")) {
            metaObject.setValue("updatedAt", new Timestamp(System.currentTimeMillis()));
        }
        if (metaObject.hasGetter("updatedBy")) {
            metaObject.setValue("updatedBy", getUserId());
        }
    }

    private Long  getUserId(){
//        return CurrentContext.getCurrentUser().getId() == null ? 10000124L:CurrentContext.getCurrentUser().getId();
        return CurrentContext.getCurrentUser().getId();
    }


}
