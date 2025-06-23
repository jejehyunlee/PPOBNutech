package com.testtech.nutech.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    private final static String QUEUE_NAME = "email";

    public static void main(String[] args) {
        try {
            Channel channel = getChannel();
            System.out.println(" [*] Menunggu pesan. Tekan CTRL+C untuk keluar");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Diterima: '" + message + "'");
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

            // Biar program tetap jalan
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Tampilkan error detail
        }
    }

    private static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672); // gunakan port AMQP, bukan port UI
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        Map<String, Object> typeQueue = new HashMap<>();
        typeQueue.put("x-queue-type", "quorum");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, typeQueue);
        return channel;
    }

}
