package com.record.rabbitmq.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.record.rabbitmq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author guming
 * @date 2021/8/4 13:40
 */
public class worker02 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        //开启发布确认
        channel.confirmSelect();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("c1接收消息"+new String(message.getBody(),"UTF-8"));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = consumer->{
            System.out.println("消息取消消费"+consumer);
        };
        //不公平分发
        //channel.basicQos(1);
        //公平分发
        //channel.basicQos(0);
        //预取值分发
        channel.basicQos(2);

        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
