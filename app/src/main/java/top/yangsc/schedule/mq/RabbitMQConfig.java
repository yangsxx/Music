package top.yangsc.schedule.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue aiClipboardQueue() {
        return new Queue("downloadTask.queue");
    }
}