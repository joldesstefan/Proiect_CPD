package src.comm.amqp;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

 
public class Main {
    private static final String QUEUE_NAME = "home";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://uolhucnk:4WCvaHBFOAdo6-U_wY-rCQIrhprzNYEk@squid.rmq.cloudamqp.com/uolhucnk");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {


           // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            while(true) {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }

        } catch (TimeoutException | InterruptedException timeoutException) {
            timeoutException.printStackTrace();
        }
    }

}
