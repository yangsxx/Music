package top.yangsc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    //总条数
    private Long counts;
    //当前页数
    private Long currentPage;
    //页大小
    private Long pageSize;
    //总页数
    private Long totalPage;

    private List<T> data;

    /**
     *
     * @param counts 总记录数
     * @param pageSize 每页显示条数
     * @param currentPage // 当前页
     * @param data // 数据
     * @return
     */
    public static <T> PageResult<T> init(Long counts, Long pageSize, Long currentPage, List<T> data){
        long totalPage = counts % pageSize == 0 ? counts/pageSize : counts/pageSize + 1;
        return new PageResult<>(counts, currentPage, pageSize, totalPage, data);
    }
}
