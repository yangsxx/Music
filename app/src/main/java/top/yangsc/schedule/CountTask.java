package top.yangsc.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.yangsc.base.mapper.ExecutionLogHistoryMapper;
import top.yangsc.base.mapper.SimpleMapper;
import top.yangsc.base.pojo.ExecutionLogHistory;
import top.yangsc.tools.TimestampUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Component
public class CountTask {

    @Resource
    private ExecutionLogHistoryMapper mapper;
    @Resource
    private SimpleMapper simpleMapper;

    // 添加定时注解（每天凌晨3点执行）
    @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Shanghai")
    public void count() {
        List<ExecutionLogHistory> executionLogHistory =  simpleMapper.countHistory();

        for (ExecutionLogHistory logHistory : executionLogHistory){
            logHistory.setDay(TimestampUtil.format(new Timestamp(System.currentTimeMillis())));
        }
        mapper.insertBatchSomeColumn(executionLogHistory);


    }
}
