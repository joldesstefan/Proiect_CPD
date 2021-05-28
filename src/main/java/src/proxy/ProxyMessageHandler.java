package src.proxy;

import src.message_utils.Message;
import src.message_utils.Serializer;
import src.comm.MessageHandleControlClass;
import src.comm.amqp.QueueControllerInterface;
import src.comm.socket.SocketControllerInterface;

import java.io.IOException;

public class ProxyMessageHandler extends MessageHandleControlClass {
    ProxyModule proxyModule;
    public  ProxyMessageHandler(ProxyModule proxyModule)
    {
        this.proxyModule = proxyModule;
    }

    public void handleSocketMessage(String message)
    {
        System.out.println("[Proxy] id= " + this.proxyModule.getMyDataBlock().getId()+" received message: "+message);

    }
    public void handleSocketMessage(String message, SocketControllerInterface socketController)
    {
        try {
            Message message1 = (Message) Serializer.fromString(message);
            System.out.println("[Proxy] id= " + this.proxyModule.getMyDataBlock().getId()+" received "+message1.getMessageType().toString()+" : "+message1.getMessage() +" from: "+message1.getParticipantId());
           if( !socketController.isClient()) {
               if (message1.getParticipantId() == this.proxyModule.getLeftParticipantDataBlock().getId()) {
                    this.proxyModule.getLeftParticipantDataBlock().setFromClientConnection(socketController);
                    System.out.println("Register left client for "+ this.proxyModule.getMyDataBlock().getId()+ " with id " + message1.getParticipantId());
               }
               if (message1.getParticipantId() == this.proxyModule.getRightParticipantDataBlock().getId()) {
                   this.proxyModule.getRightParticipantDataBlock().setFromClientConnection(socketController);
                   System.out.println("Register right client for "+ this.proxyModule.getMyDataBlock().getId()+ " with id " + message1.getParticipantId());

               }
           }
           if(message1.getMessageType().equals(Message.MessageType.TOKEN_PASS) && message1.isContainsToken())
           {
               System.out.println("[Got the token] "+ this.proxyModule.getMyDataBlock().getId() + " from : "  + message1.getParticipantId());
               this.proxyModule.setToken(message1.getToken());
               this.proxyModule.setHaveToken(true);
           }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //socketController.sendMessage("Echo from default handler "+ message);

    }
    public void handleQueueMessage(String queueName, String message)
    {
        System.out.println("[Default MessageHandleControlClass] received message: "+message);

    }
    public void handleQueueMessage(String queueName, String message, QueueControllerInterface queueController)
    {
        System.out.println("[Default MessageHandleControlClass] received message: "+message);

        this.proxyModule.getClientQueueMessageHandler().handleMessage(queueName,message);
    }
}
