package src.comm.socket.comm_server;

import src.comm.MessageHandleControlClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketModule extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private Class<Thread> clientHandlerClass;
    private Class[] clientHandlerConstructorArgumentsClass;
    private MessageHandleControlClass messageHandleControlClass;

    public ServerSocketModule(int port, Class clientHandlerClass, MessageHandleControlClass messageHandleControlClass)
    {
        this.port=port;
        this.clientHandlerClass = clientHandlerClass;
        this.clientHandlerConstructorArgumentsClass = new Class[] {Socket.class, MessageHandleControlClass.class};
        this.messageHandleControlClass = messageHandleControlClass;



    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for connection");
                this.clientHandlerClass.getConstructor(this.clientHandlerConstructorArgumentsClass).newInstance(serverSocket.accept(), messageHandleControlClass).start();
            } catch (IOException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() throws IOException {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server started at " + this.port);
        } catch (IOException e) {
            // e.printStackTrace();
            throw (new IOException("[Server side] Could not create socket"));
        }
        start();
    }


    public void stopConnection() throws IOException {

        serverSocket.close();
    }
}
