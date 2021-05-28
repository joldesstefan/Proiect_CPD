package src.proxy;

import src.client.ClientQueueMessageHandler;
import src.message_utils.Message;
import src.message_utils.Serializer;
import src.comm.MessageHandleControlClass;
import src.comm.amqp.QueueClientModule;
import src.comm.socket.comm_client.ClientSocketModule;
import src.comm.socket.comm_server.ServerSocketModuleWrapper;

import java.io.IOException;
import java.util.List;

public class ProxyModule extends Thread{
    private ParticipantDataBlock leftParticipantDataBlock;
    private ParticipantDataBlock rightParticipantDataBlock;
    private ParticipantDataBlock myDataBlock;
    private boolean haveToken;
    private String  token ;
    private List<String> toPublishTopics;
    private List<String> toSubscribeTopics;
    private QueueClientModule queueClientModule;
    private ServerSocketModuleWrapper serverSocketModule;
    private ClientSocketModule leftClientSocketModule;
    private ClientSocketModule rightClientSocketModule;
    private MessageHandleControlClass messageHandleControlClass;
    private ClientQueueMessageHandler clientQueueMessageHandler;
    private int counter = 0;
    public ProxyModule(ParticipantDataBlock leftParticipantDataBlock, ParticipantDataBlock rightParticipantDataBlock, ParticipantDataBlock myDataBlock, boolean haveToken, String token, List<String> toPublishTopics, List<String> toSubscribeTopics, ClientQueueMessageHandler clientQueueMessageHandler) {
        this.leftParticipantDataBlock = leftParticipantDataBlock;
        this.rightParticipantDataBlock = rightParticipantDataBlock;
        this.myDataBlock = myDataBlock;
        this.haveToken = haveToken;
        this.token = token;

        this.toPublishTopics = toPublishTopics;
        this.toSubscribeTopics = toSubscribeTopics;
        this.messageHandleControlClass= new ProxyMessageHandler(this);
        this.queueClientModule = new QueueClientModule("amqps://uolhucnk:4WCvaHBFOAdo6-U_wY-rCQIrhprzNYEk@squid.rmq.cloudamqp.com/uolhucnk", this.messageHandleControlClass);
        this.queueClientModule.initialize();
        this.clientQueueMessageHandler = clientQueueMessageHandler;
        if(haveToken)
        {
            this.counter= 0;
            this.clientQueueMessageHandler.gotToken();
        }
        else
        {
            this.clientQueueMessageHandler.passedToken();
        }

        start();
    }
    public void initialize()
    {
        this.serverSocketModule = new ServerSocketModuleWrapper(this.myDataBlock.getSocketServerPort(), this.messageHandleControlClass);
        try {
            this.serverSocketModule.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for ( String s : this.toPublishTopics)
        {
            this.declareQueue(s);
            this.subscribe(s);
        }

    }
    public void refreshConnections()
    {
        if( this.leftParticipantDataBlock.getToServerConnection() == null || !this.leftParticipantDataBlock.getToServerConnection().isOpened())
        {
            try {
                ClientSocketModule clientSocketModule = new ClientSocketModule(this.leftParticipantDataBlock.getSocketServerAddress(), this.leftParticipantDataBlock.getSocketServerPort(), this.messageHandleControlClass);
                clientSocketModule.initialize();
                this.leftParticipantDataBlock.setToServerConnection(clientSocketModule);
                System.out.println(this.myDataBlock.getId() + " Created connection to server " + this.leftParticipantDataBlock.getId() + " ");
            }catch (Exception e)
            {

                System.out.println("Exception during connection with "+ this.leftParticipantDataBlock.getSocketServerPort());
            }

        }
        else
        {
            Message message = new Message();
            message.setParticipantId(this.myDataBlock.getId());
            message.setMessageType(Message.MessageType.HEARTBEAT);
            message.setMessage("left");
            try {
                this.leftParticipantDataBlock.getToServerConnection().sendMessage(Serializer.toString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if( this.rightParticipantDataBlock.getToServerConnection() == null || !this.rightParticipantDataBlock.getToServerConnection().isOpened())
        {
            try {
                ClientSocketModule clientSocketModule = new ClientSocketModule(this.rightParticipantDataBlock.getSocketServerAddress(), this.rightParticipantDataBlock.getSocketServerPort(), this.messageHandleControlClass);
                clientSocketModule.initialize();
                this.rightParticipantDataBlock.setToServerConnection(clientSocketModule);
                System.out.println(this.myDataBlock.getId() + " Created connection to server " + this.rightParticipantDataBlock.getId() + " ");
            }catch (Exception e)
            {
                System.out.println("Exception during connection with "+ this.rightParticipantDataBlock.getSocketServerPort());
            }
        }
        else
        {
            Message message = new Message();
            message.setParticipantId(this.myDataBlock.getId());
            message.setMessageType(Message.MessageType.HEARTBEAT);
            message.setMessage("right");
            try {
                this.rightParticipantDataBlock.getToServerConnection().sendMessage(Serializer.toString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendToClients()
    {
        if(this.leftParticipantDataBlock.getFromClientConnection()!=null && this.leftParticipantDataBlock.getFromClientConnection().isOpened())
        {
            Message message = new Message();
            message.setParticipantId(this.myDataBlock.getId());
            message.setMessageType(Message.MessageType.INFO);
            message.setMessage("left client");
            try {
                this.leftParticipantDataBlock.getFromClientConnection().sendMessage(Serializer.toString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(this.rightParticipantDataBlock.getFromClientConnection()!=null && this.rightParticipantDataBlock.getFromClientConnection().isOpened())
        {
            Message message = new Message();
            message.setParticipantId(this.myDataBlock.getId());
            message.setMessageType(Message.MessageType.INFO);
            message.setMessage("right client");
            try {
                this.rightParticipantDataBlock.getFromClientConnection().sendMessage(Serializer.toString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void passToken()
    {
        if(this.haveToken) {
            this.clientQueueMessageHandler.passedToken();
            this.haveToken = false;
            this.counter = 0;
            if (this.rightParticipantDataBlock.getToServerConnection() != null && this.rightParticipantDataBlock.getToServerConnection().isOpened()) {
                Message message = new Message();
                message.setParticipantId(this.myDataBlock.getId());
                message.setMessageType(Message.MessageType.TOKEN_PASS);
                message.setToken(this.token);
                message.setContainsToken(true);
                message.setMessage("you got it now (" + this.myDataBlock.getId() + ", " + this.rightParticipantDataBlock.getId() + ")");
                try {
                    this.rightParticipantDataBlock.getToServerConnection().sendMessage(Serializer.toString(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.clientQueueMessageHandler.gotToken();
                this.haveToken = true;
                this.counter = 0;
            }
        }
    }
    public void run()
    {
        try {
            //initialize();
            while(true) {
                refreshConnections();
                Thread.sleep(1000);
                if(this.haveToken) {
                    counter++;
                }
                if( counter >=10 && haveToken)
                {
                    this.passToken();
                }
                sendToClients();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ParticipantDataBlock getLeftParticipantDataBlock() {
        return leftParticipantDataBlock;
    }

    public void setLeftParticipantDataBlock(ParticipantDataBlock leftParticipantDataBlock) {
        this.leftParticipantDataBlock = leftParticipantDataBlock;
    }

    public ParticipantDataBlock getRightParticipantDataBlock() {
        return rightParticipantDataBlock;
    }

    public void setRightParticipantDataBlock(ParticipantDataBlock rightParticipantDataBlock) {
        this.rightParticipantDataBlock = rightParticipantDataBlock;
    }

    public ParticipantDataBlock getMyDataBlock() {
        return myDataBlock;
    }

    public void setMyDataBlock(ParticipantDataBlock myDataBlock) {
        this.myDataBlock = myDataBlock;
    }

    public boolean isHaveToken() {
        return haveToken;
    }

    public void setHaveToken(boolean haveToken) {
        this.haveToken = true;
        this.counter = 0;
        this.clientQueueMessageHandler.gotToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getToPublishTopics() {
        return toPublishTopics;
    }

    public void setToPublishTopics(List<String> toPublishTopics) {
        this.toPublishTopics = toPublishTopics;
    }

    public List<String> getToSubscribeTopics() {
        return toSubscribeTopics;
    }

    public void setToSubscribeTopics(List<String> toSubscribeTopics) {
        this.toSubscribeTopics = toSubscribeTopics;
    }

    public QueueClientModule getQueueClientModule() {
        return queueClientModule;
    }

    public void setQueueClientModule(QueueClientModule queueClientModule) {
        this.queueClientModule = queueClientModule;
    }

    public ServerSocketModuleWrapper getServerSocketModule() {
        return serverSocketModule;
    }

    public void setServerSocketModule(ServerSocketModuleWrapper serverSocketModule) {
        this.serverSocketModule = serverSocketModule;
    }

    public ClientSocketModule getLeftClientSocketModule() {
        return leftClientSocketModule;
    }

    public void setLeftClientSocketModule(ClientSocketModule leftClientSocketModule) {
        this.leftClientSocketModule = leftClientSocketModule;
    }

    public ClientSocketModule getRightClientSocketModule() {
        return rightClientSocketModule;
    }

    public void setRightClientSocketModule(ClientSocketModule rightClientSocketModule) {
        this.rightClientSocketModule = rightClientSocketModule;
    }

    public MessageHandleControlClass getMessageHandleControlClass() {
        return messageHandleControlClass;
    }

    public void setMessageHandleControlClass(MessageHandleControlClass messageHandleControlClass) {
        this.messageHandleControlClass = messageHandleControlClass;
    }
    public void subscribe(String queueName)
    {
        this.queueClientModule.subscribe(queueName);
    }
    public void declareQueue(String queueName)
    {
        this.queueClientModule.declareQueue(queueName);
    }
    public void publish(String queueName, String message)
    {
        if(this.haveToken) {
            this.queueClientModule.publish(queueName, message);
        }
        else
        {
            System.out.println("[Proxy] do not have token, try later");
        }
    }

    public ClientQueueMessageHandler getClientQueueMessageHandler() {
        return clientQueueMessageHandler;
    }

    public void setClientQueueMessageHandler(ClientQueueMessageHandler clientQueueMessageHandler) {
        this.clientQueueMessageHandler = clientQueueMessageHandler;
    }
}

