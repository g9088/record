package com.record.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.record.rabbitmq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author guming
 * @date 2021/8/4 13:27
 */
public class Task02 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        //1队列名称2是否持久化3是否共享4是否自动删除
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            //MessageProperties.MINIMAL_PERSISTENT_BASIC消息发送持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.MINIMAL_PERSISTENT_BASIC,message.getBytes("UTF-8"));

        }
    }
}
