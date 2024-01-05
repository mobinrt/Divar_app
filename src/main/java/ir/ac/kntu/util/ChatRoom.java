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
    public String toString() {
        int length = msg.size();
        return getSender().getUserName() + " (" + getSender().getRole() + ") " + ": " + msg.get(--length);
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

