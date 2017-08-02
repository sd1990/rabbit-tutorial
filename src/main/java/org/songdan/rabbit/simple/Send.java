package org.songdan.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Songdan
 * @date 2017/7/17 14:47
 */
public class Send {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("172.16.6.101");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("rabbit");
        connectionFactory.setPassword("rabbit");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i < 1; i++) {
            String queueName = "hello";
            channel.queueDeclare(queueName, true, false, false, null);
            String message = "Hello World:" + i + "!";
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
