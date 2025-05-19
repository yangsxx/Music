package top.yangsc.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * 描述：top.yangsc.swiftcache.base.mapper
 *
 * @author yang
 * @date 2025/5/16 21:04
 */
public interface EasyBaseMapper<T> extends BaseMapper<T> {

    Integer insertBatchSomeColumn(Collection<T> entityList);
}
