package src.proxy;

import src.comm.socket.SocketControllerInterface;

import java.io.Serializable;

public class ParticipantDataBlock implements Serializable {
    private int id;
    private boolean itsMe;
    private String socketServerAddress;
    private int socketServerPort;
    private String socketClientAddress;
    private int socketClientPort;
    private SocketControllerInterface toServerConnection;
    private SocketControllerInterface fromClientConnection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isItsMe() {
        return itsMe;
    }

    public void setItsMe(boolean itsMe) {
        this.itsMe = itsMe;
    }

    public String getSocketServerAddress() {
        return socketServerAddress;
    }

    public void setSocketServerAddress(String socketServerAddress) {
        this.socketServerAddress = socketServerAddress;
    }

    public int getSocketServerPort() {
        return socketServerPort;
    }

    public void setSocketServerPort(int socketServerPort) {
        this.socketServerPort = socketServerPort;
    }

    public String getSocketClientAddress() {
        return socketClientAddress;
    }

    public void setSocketClientAddress(String socketClientAddress) {
        this.socketClientAddress = socketClientAddress;
    }

    public int getSocketClientPort() {
        return socketClientPort;
    }

    public void setSocketClientPort(int socketClientPort) {
        this.socketClientPort = socketClientPort;
    }

    public SocketControllerInterface getToServerConnection() {
        return toServerConnection;
    }

    public void setToServerConnection(SocketControllerInterface toServerConnection) {
        this.toServerConnection = toServerConnection;
    }

    public SocketControllerInterface getFromClientConnection() {
        return fromClientConnection;
    }

    public void setFromClientConnection(SocketControllerInterface fromClientConnection) {
        this.fromClientConnection = fromClientConnection;
    }
}
