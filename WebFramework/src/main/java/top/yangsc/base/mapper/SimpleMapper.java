package top.yangsc.base.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yangsc.base.pojo.ExecutionLogHistory;

import java.util.List;
import java.util.Map;

@Mapper
public interface SimpleMapper {

    boolean isExist(@Param("TabName") String TabName, @Param("CName") String CName,@Param("data")String data);

    boolean isExistByWhere(@Param("TabName") String TabName,@Param("condition")String condition);

    List<ExecutionLogHistory> countHistory();
}
