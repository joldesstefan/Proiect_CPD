package src.others_testing;

import src.comm.socket.comm_client.ClientSocketModule;
import src.comm.socket.comm_client.MessageHandleControlClassClient;
import src.comm.socket.comm_server.MessageHandleControlClassServer;
import src.comm.socket.comm_server.ServerSocketModuleWrapper;

import java.io.IOException;

public class ServerSocketMain {
    public static void main(String[] args) throws IOException {
        //deci in server clasamhandler este daca sca si clasa deoarece nu o customizz decat cat este deja, clientii acestui modul isi dustomizeaza nevoile doar prin instana unei clase de top message hanlder
       ServerSocketModuleWrapper serverSocketModuleWrapper = new ServerSocketModuleWrapper(8989, new MessageHandleControlClassServer());
        try {
            serverSocketModuleWrapper.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Opa");
        ClientSocketModule clientSocketModule = new ClientSocketModule("localhost", 8989, new MessageHandleControlClassClient());
        clientSocketModule.initialize();
        clientSocketModule.sendMessage("Bqaaaa");
    }

}
