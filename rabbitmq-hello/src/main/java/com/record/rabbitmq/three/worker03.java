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
public class worker03 {
    //队列名称
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取信道
        Channel channel = RabbitMqUtil.getChannel();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("c2接收消息"+new String(message.getBody(),"UTF-8"));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = consumer->{
            System.out.println("消息取消消费"+consumer);
        };
        channel.basicQos(5);
        //1队列名称2是否自动应答3接收消息的处理4取消消息的回调
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
