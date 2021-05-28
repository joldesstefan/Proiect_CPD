package src.comm.socket.comm_server;

import src.comm.MessageHandleControlClass;

import java.io.IOException;

public class ServerSocketModuleWrapper {
    private ServerSocketModule serverSocketModule;
    private Class clientHandlerClass;
    private MessageHandleControlClass messageHandleControlClass;
    private int port;
    public ServerSocketModuleWrapper(int port, MessageHandleControlClass messageHandleControlClass)
    {
        this.port = port;
        this.clientHandlerClass = ClientHandler.class;
        this.messageHandleControlClass = messageHandleControlClass;

    }
    public ServerSocketModuleWrapper(int port, Class clientHandlerClass, MessageHandleControlClass messageHandleControlClass)
    {
        this.port = port;
        this.clientHandlerClass = ClientHandler.class;
        this.messageHandleControlClass = messageHandleControlClass;

    }
    public void initialize() throws IOException {
        this.serverSocketModule= new ServerSocketModule(this.port, this.clientHandlerClass, this.messageHandleControlClass);
        this.serverSocketModule.initialize();
    }
    public void shutDown()
    {
        try {
            this.serverSocketModule.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
