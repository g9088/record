package com.record.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author guming
 * @date 2021/8/5 13:23
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = "QD")
    public void deadLetterQueue(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到私信队列：{}",new Date().toString(),msg);

    }
}
