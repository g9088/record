package com.record.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guming
 * @date 2021/8/5 10:41
 */
@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";

    /**
     * 普通交换机声明
     * @return
     */
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 死信交换机声明
     * @return
     */
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明队列A ttl10s 绑定到对应的死信交换机Y
     * @return
     */
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key","YD");
        args.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }
    /**
     * 声明队列B ttl40s 绑定到对应的死信交换机Y
     * @return
     */
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key","YD");
        args.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }
    /**
     * 声明队列B ttl40s 绑定到对应的死信交换机Y
     * @return
     */
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    /**
     * 声明死信队列qd
     * @return
     */
    @Bean("queueD")
    public Queue queueD(){
        return new Queue(DEAD_LETTER_QUEUE);
    }

    /**
     * 队列A绑定到交换机x
     * @param queueA
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueABindingx(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    /**
     * 队列B绑定到交换机x
     * @param queueB
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueBBindingx(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    /**
     * 队列C绑定到交换机x
     * @param queueC
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueCBindingx(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    /**
     * 队列D绑定到交换机Y
     * @param queueD
     * @param yExchange
     * @return
     */
    @Bean
    public Binding queueDBindingQD(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange")DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }


}
