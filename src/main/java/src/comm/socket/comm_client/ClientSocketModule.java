package src.comm.socket.comm_client;
import src.comm.socket.SocketControllerInterface;
import src.comm.MessageHandleControlClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketModule extends Thread implements SocketControllerInterface {
    private Socket clientSocket;
    private String ip;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandleControlClass messageHandleControlClass;


    public ClientSocketModule(String ip,int port, MessageHandleControlClass messageHandleControlClass )
    {
        this.ip = ip;
        this.port= port;
        this.messageHandleControlClass = messageHandleControlClass;
    }
public void initialize()    {
        System.out.println("Client starting " + this.port);
    try {
        clientSocket = new Socket(this.ip, this.port);
    } catch (IOException e) {
        e.printStackTrace();
    }


    try {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
        e.printStackTrace();
    }

    try {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException e) {
        e.printStackTrace();
    }
    this.messageHandleControlClass = messageHandleControlClass;
        start();

    }

    @Override
    public void run() {
        String inputLine = null;
        while(clientSocket.isConnected() && ! clientSocket.isClosed())
        { try {
            if (!((inputLine = in.readLine()) != null)) break;
        } catch (IOException e) {
            e.printStackTrace();
        }
            if (".".equals(inputLine)) {
                out.println("Closed");
                break;
            }
            this.messageHandleControlClass.handleSocketMessage(inputLine, this);

        }
        System.out.println("[Client] shutted down");
    }

    public  void sendMessage(String msg)     {
        out.println(msg);

    }

    public void stopConnection()  {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean isOpened()
    {
        return clientSocket.isConnected() && ! clientSocket.isClosed() && super.isAlive();
     }
     public boolean isClient()
     {
         return true;
     }

}