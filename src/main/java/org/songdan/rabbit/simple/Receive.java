package org.songdan.rabbit.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Songdan
 * @date 2017/7/17 15:45
 */
public class Receive {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.6.101");
        factory.setPort(5672);
        factory.setUsername("rabbit");
        factory.setPassword("rabbit");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("consumerTag is :" + consumerTag);
                System.out.println("properties is :" + properties);
                System.out.println("[x] Received:" + message);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //如果autoAck设置为true，消息从队列中发出后，就会被移除；设置为false，需要显式的向broker发送acknowledge
        boolean autoAck = false;
        channel.basicConsume("hello", autoAck, consumer);
    }
}
