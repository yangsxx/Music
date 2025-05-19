package top.yangsc.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yangsc.base.pojo.Users;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yang
 * @since 2023-06-19
 */
@Mapper
public interface UserMapper extends BaseMapper<Users> {

}
