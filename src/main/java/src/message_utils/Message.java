package src.message_utils;

import java.io.Serializable;

public class Message implements Serializable {
    public static enum MessageType{
        INFO,
        TOKEN_PASS,
        HEARTBEAT
    };
    private int participantId ;
    private MessageType messageType;
    private boolean containsToken;
    private String token;
    private String message;

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public boolean isContainsToken() {
        return containsToken;
    }

    public void setContainsToken(boolean containsToken) {
        this.containsToken = containsToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
