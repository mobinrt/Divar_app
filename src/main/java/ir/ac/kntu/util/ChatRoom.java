package ir.ac.kntu.util;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatRoom {
    private ArrayList<ChatRoom> chat;
    private User sender;
    private User receiver;
    private String msg;

    public ChatRoom() {
        chat = new ArrayList<>();
    }

    public ChatRoom(User sender, User receiver, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }

    public void startChat(Scanner sc, Customer sender, Seller receiver) {
        System.out.println("===========================================   Chat room:  ============================================");
        System.out.print("Enter massage: ");
        String msg = sc.nextLine();
        msg = sender.getUserName() + " (" + UsersRole.CUSTOMER + ") " + ": " + msg;
        chat.add(new ChatRoom(sender, receiver, msg));
        System.out.println("======================================================================================================");
    }

    public void showChat() {
        System.out.println("===========================================   Chat room:  ============================================");
        for (ChatRoom ignored : chat) {
            System.out.println(sender.getUserName() + " (" + sender.getRole() + ") " + ": " + msg);
        }
    }

    public void choiceChat() {

    }

    public void sendChat(Scanner sc, User sender, User receiver) {
        System.out.print("Enter massage: ");
        String msg = sc.nextLine();
        msg = sender.getUserName() + " (" + sender.getRole() + ") " + ": " + msg;
        chat.add(new ChatRoom(sender, receiver, msg));
        System.out.println("======================================================================================================");
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

    public ArrayList<ChatRoom> getChat() {
        return chat;
    }

    public String getMsg() {
        return msg;
    }
}

