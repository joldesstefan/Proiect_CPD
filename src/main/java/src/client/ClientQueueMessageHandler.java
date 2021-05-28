package src.client;

public class ClientQueueMessageHandler {
    private ClientView clientView;
    public ClientQueueMessageHandler(ClientView clientView)
    {
        this.clientView = clientView;
    }
    public void handleMessage(String queueName, String message)
    {
        System.out.println("[Queue message] queue: " + queueName+ " message: " + message);
        this.clientView.addText(queueName,"\n"+message);
    }
    public void gotToken()
    {
        this.clientView.setEnableButtons(true);
    }
    public void passedToken()
    {
        this.clientView.setEnableButtons(false);
    }


}
