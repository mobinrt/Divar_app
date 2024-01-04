package ir.ac.kntu.util;

import javax.swing.plaf.PanelUI;
import java.util.*;

public class ChatRoom {
    private User sender;
    private User receiver;
    private String msg;

    public ChatRoom(){

    }
    public ChatRoom(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public ChatRoom(User sender, User receiver, String msg) {
        this(sender, receiver);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return getSender().getUserName() + " (" + getSender().getRole() + ") " + ": " + msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

