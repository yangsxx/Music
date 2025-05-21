package top.yangsc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("top.yangsc.base.mapper")
@EnableScheduling
public class MusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}

	//musicApp todo
	// 1.定时任务，检查登录状态
	// 2. qq支持
	// 3.长期任务，完善音乐功能




	// framework todo
	// 1. es支持
	// 2. 配置缓存


}
