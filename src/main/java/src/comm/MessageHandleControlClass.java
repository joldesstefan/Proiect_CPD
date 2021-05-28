package src.comm;

import src.comm.amqp.QueueControllerInterface;
import src.comm.socket.SocketControllerInterface;

public class MessageHandleControlClass {

     public void handleSocketMessage(String message)
     {
         System.out.println("[Default MessageHandleControlClass] received message: "+message);

     }
    public void handleSocketMessage(String message, SocketControllerInterface socketController)
    {
        System.out.println("[Default MessageHandleControlClass] received message: "+message);
        socketController.sendMessage("Echo from default handler "+ message);

    }
    public void handleQueueMessage(String queueName, String message)
    {
        System.out.println("[Default MessageHandleControlClass] received message: "+message);

    }
    public void handleQueueMessage(String queueName, String message, QueueControllerInterface queueController)
    {
        System.out.println("[Default MessageHandleControlClass] received message on queue "+ queueName+ ": " +message);

    }


}
