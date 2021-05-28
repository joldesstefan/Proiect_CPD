package src.others_testing;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class COnsume {
    private static final String EXCHANGE_NAME = "logs";

    public static void c1() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://uolhucnk:4WCvaHBFOAdo6-U_wY-rCQIrhprzNYEk@squid.rmq.cloudamqp.com/uolhucnk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
    public static void main(String[] argv) throws Exception {
        COnsume.c1();
        COnsume.c1();
    }

}