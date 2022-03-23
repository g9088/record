package com.record.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.record.rabbitmq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author guming
 * @date 2021/8/4 15:45
 */
public class Confirm {
    private static final int sum = 1000;

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        sentMessage();

    }
    //单一确认发布
    public static void sentMessage() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtil.getChannel();
        String uuid = UUID.randomUUID().toString();
        channel.queueDeclare(uuid,false,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i <sum ; i++) {
            String message = i+"";
            channel.basicPublish("",uuid,null,message.getBytes());
            boolean b = channel.waitForConfirms();
            if (b){
                System.out.println("确认发布"+i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("时间：" +(end-begin));

    }
}

