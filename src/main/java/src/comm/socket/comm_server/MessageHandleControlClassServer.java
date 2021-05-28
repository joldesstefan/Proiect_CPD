package src.comm.socket.comm_server;

import src.comm.socket.SocketControllerInterface;
import src.comm.MessageHandleControlClass;

import java.util.concurrent.Callable;

public class MessageHandleControlClassServer extends MessageHandleControlClass {


    @Override
    public void handleSocketMessage(String message) {
        super.handleSocketMessage(message);
    }

    @Override
    public void handleSocketMessage(String message, SocketControllerInterface socketControllerInterface) {
        socketControllerInterface.sendMessage("Bravo " + message);
       // socketControllerInterface.stopConnection();
    }
}
