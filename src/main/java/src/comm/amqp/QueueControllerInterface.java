package src.comm.amqp;

public interface QueueControllerInterface {
        public void declareQueue(String queueName);
        public void publish(String queueName, String message);
        public void subscribe(String queueName);
        public void unsubscribe(String queueName);


}
