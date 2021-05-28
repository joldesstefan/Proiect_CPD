package src.comm.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import src.comm.MessageHandleControlClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class QueueClientModule implements QueueControllerInterface {
    private Channel channel;
    private String serverURI;
    private MessageHandleControlClass messageHandleControlClass;

    public QueueClientModule(String serverURI, MessageHandleControlClass messageHandleControlClass) {
        this.serverURI = serverURI;
        this.messageHandleControlClass = messageHandleControlClass;
    }

    public void initialize() {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(this.serverURI);
            Connection connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareQueue(String exchangeName) {
        try {
            channel.exchangeDeclare(exchangeName, "fanout");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void publish(String exchangeName, String message) {
        try {
            //this.channel.basicPublish("", queueName,null,  message.getBytes());
            channel.basicPublish(exchangeName, "", null, message.getBytes("UTF-8"));
            System.out.println("[Publih] " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String exchangeName) {
        try {
            System.out.println("[Subscribe]");
            // this.channel.queueDeclare(queueName,false, false, false, null);
            this.channel.exchangeDeclare(exchangeName, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, "");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                this.messageHandleControlClass.handleQueueMessage(exchangeName, message, this);
            };
            try {
                this.channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe(String queueName) {
    }


}
