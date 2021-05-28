package src.comm.socket.comm_client;

import src.comm.socket.SocketControllerInterface;
import src.comm.MessageHandleControlClass;

public class MessageHandleControlClassClient  extends MessageHandleControlClass {

     public void handleSocketMessage(String message)
     {
         System.out.println("[Default MessageHandleControlClass] received message: "+message);


     }
   public void handleSocketMessage(String message, SocketControllerInterface socketControllerInterface)
    {
        System.out.println("[Default MessageHandleControlClass] received message: "+message);
        socketControllerInterface.sendMessage("Echo from  server "+ message);

    }


}
