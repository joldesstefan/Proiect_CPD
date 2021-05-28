package src.comm.socket.comm_server;

import src.comm.socket.SocketControllerInterface;
import src.comm.MessageHandleControlClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread implements SocketControllerInterface {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandleControlClass messageHandleControlClass;
    public ClientHandler(Socket clientSocket, MessageHandleControlClass messageHandleControlClass)
    {
        this.clientSocket = clientSocket;
        this.messageHandleControlClass = messageHandleControlClass;
    }
    public void sendMessage(String message)
    {
        if(this.clientSocket!=null)
        {
            if( this.clientSocket.isConnected())
            {
                if(this.out!=null) {
                    this.out.println(message);
                }
            }
        }
    }

    @Override
    public void stopConnection() {
        if( clientSocket.isConnected()) {
            System.out.println("[ClientHandler] closing client connection");

            try {
                this.clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     }

    @Override
    public void run() {
        System.out.println("[ClientHandler] client intercepted: " +clientSocket.getRemoteSocketAddress().toString());
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine = null;
        while(clientSocket.isConnected()&& !clientSocket.isClosed())
        {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (".".equals(inputLine)) {
                out.println("Closed");
                break;
            }
            messageHandleControlClass.handleSocketMessage(inputLine,this);
           // System.out.println("[ClientHandler] message received: "+ inputLine);
        }
        System.out.println("[ClientHandler] shutted down");

    }
    public boolean isOpened()
    {
        return clientSocket.isConnected() && ! clientSocket.isClosed() && super.isAlive();

    }
    public boolean isClient()
    {
        return false;
    }
}
