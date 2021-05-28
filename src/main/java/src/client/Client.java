package src.client;

import src.proxy.ParticipantDataBlock;
import src.proxy.ProxyModule;

import java.util.List;

public class Client {
    private ClientView clientView;
    private ProxyModule proxyModule;
    private ClientQueueMessageHandler clientQueueMessageHandler;

    public Client(String clientName, List<String> toSubscribeQueues, ParticipantDataBlock myDataBlock, ParticipantDataBlock leftParticipantDataBlock, ParticipantDataBlock rightParticipantDataBlock, boolean haveToken, String token) {
        this.clientView = new ClientView(toSubscribeQueues, clientName,this);
        this.clientQueueMessageHandler = new ClientQueueMessageHandler(clientView);
        this.proxyModule = new ProxyModule(leftParticipantDataBlock, rightParticipantDataBlock,myDataBlock,haveToken,token,toSubscribeQueues,toSubscribeQueues,clientQueueMessageHandler);
        this.proxyModule.initialize();
        this.clientView.initialize();
    }
    public void publish(String queueName, String message)
    {
        this.proxyModule.publish(queueName,message);
    }
    public void passToken()
    {
        this.proxyModule.passToken();
    }
}
