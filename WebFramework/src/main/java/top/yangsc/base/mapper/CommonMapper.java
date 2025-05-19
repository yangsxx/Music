package top.yangsc.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang
 * @since 2023-06-19
 */
@Mapper
public interface CommonMapper {
    boolean isExist(@Param("TabName") String TabName, @Param("CName") String CName, @Param("data")String data);

    long getId();
}
