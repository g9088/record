package com.record.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.record.rabbitmq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author guming
 * @date 2021/8/4 10:45
 */
public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收消息"+new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println(consumerTag +"取消消费消息");
        };
        System.out.println("c2接收消息");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
