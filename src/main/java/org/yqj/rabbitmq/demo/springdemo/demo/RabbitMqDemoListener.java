package org.yqj.rabbitmq.demo.springdemo.demo;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Created by yaoqijun on 2017/5/10.
 */
public class RabbitMqDemoListener {
    public static void Main(String[] args) throws Exception {
        ConnectionFactory cf = buildConnectionFactory();

        // set up the queue, exchange, binding on the broker
        RabbitAdmin admin = new RabbitAdmin(cf);

        // set up the listener and container
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(cf);
        Object listener = new Object() {
            public void handleMessage(String foo) {
                System.out.println(foo);
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueueNames("myQueue4");
        container.start();

        Thread.sleep(1000000);
        container.stop();
    }


    private static ConnectionFactory buildConnectionFactory(){
        com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);
        return new CachingConnectionFactory(cf);
    }
}
