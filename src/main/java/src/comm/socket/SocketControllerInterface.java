package src.comm.socket;

public interface SocketControllerInterface {
    public void sendMessage(String message);
    public void stopConnection();
    public boolean isOpened();
    public boolean isClient();
}
