package ir.ac.kntu.util;

import ir.ac.kntu.util.users.User;

import java.util.*;

public class ChatRoom {
    private User sender;
    private User receiver;
    private final ArrayList<String> msg;

    public ChatRoom(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        msg = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return (sender.equals(chatRoom.sender) && receiver.equals(chatRoom.receiver));
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 17;
        result = result * prime + sender.hashCode();
        result = result * prime + receiver.hashCode();
        return result;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }
}

