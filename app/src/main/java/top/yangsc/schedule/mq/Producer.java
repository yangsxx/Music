package top.yangsc.schedule.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final String QUEUE_NAME = "downloadTask.queue";

    private static final String Count_TASK = "countTask.queue";
    
    private final RabbitTemplate rabbitTemplate;
    
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void DownloadTaskProducer(String message) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
    }

    public void CountTaskProducer(String message) {
        rabbitTemplate.convertAndSend(Count_TASK, message);
    }

}
