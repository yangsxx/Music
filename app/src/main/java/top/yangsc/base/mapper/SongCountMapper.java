package top.yangsc.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yangsc.base.pojo.SongCount;

/**
 * 描述：top.yangsc.base.mapper
 *
 * @author yang
 * @date 2025/5/20 23:04
 */
@Mapper
public interface SongCountMapper  extends BaseMapper<SongCount> {
}
