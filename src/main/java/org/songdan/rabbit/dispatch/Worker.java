package org.songdan.rabbit.dispatch;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 启动两个消费者实例，测试默认的轮询分发策略
 * @author Songdan
 * @date 2017/7/17 15:45
 */
public class Worker {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.6.101");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("NEW_TASK", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Received:" + message);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("consume done");
            }
        };
//        mq服务器发送完消息后立即从队列中移除消息
//        channel.basicConsume("hello", true, consumer);
//        mq服务器发送完消息后等待worker执行ACK，收到ack后才会移除消息
        channel.basicConsume("hello", false, consumer);
    }
}
