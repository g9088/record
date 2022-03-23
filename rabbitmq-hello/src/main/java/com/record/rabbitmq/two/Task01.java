package com.record.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.record.rabbitmq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author guming
 * @date 2021/8/4 10:53
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,false,null,message.getBytes());
        }

    }
}
