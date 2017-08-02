package org.songdan.rabbit.fanout;

import com.rabbitmq.client.*;

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
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        for (int i = 0; i < 100; i++) {
            String message = "Hello Fanout:" + i + "!";
            //向fanout类型的exchange中发送消息
            channel.basicPublish("logs", "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
