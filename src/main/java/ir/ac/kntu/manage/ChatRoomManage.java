package ir.ac.kntu.manage;

import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatRoomManage {
    private final Map<User, ChatRoom> returnChatRoomBySender;
    private final Map<User, ChatRoom> returnChatRoomByReceiver;
    private final Map<ChatRoom, ArrayList<ChatRoom>> returnChat;
    private final ArrayList<ChatRoom> chat;

    public ChatRoomManage() {
        chat = new ArrayList<>();
        returnChatRoomByReceiver = new HashMap<>();
        returnChat = new HashMap<>();
        returnChatRoomBySender = new HashMap<>();
    }

    public void chatBox(Scanner sc, ChatRoom chatRoom, Customer customer) {
        showAllChats(chatRoom);
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        if (choice == 0)
            return;
        User user = customer.getUsers().get(--choice);
        chatRoom = returnChatRoomByReceiver.get(user);
        ArrayList<ChatRoom> chat = returnChat.get(chatRoom);
        chatPage(chat);
        sendMsg(sc, chatRoom);
    }

    public void chatBox(Scanner sc, ChatRoom chatRoom, Seller seller) {
        showAllChats(chatRoom);
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        if (choice == 0)
            return;
        User user = seller.getUsers().get(--choice);
        chatRoom = returnChatRoomBySender.get(user);
        ArrayList<ChatRoom> chat = returnChat.get(chatRoom);
        chatPage(chat);
        sendMsg(sc, chatRoom);
    }

    public void checkExistenceChat(Scanner sc, User sender, User receiver) {
        ChatRoom chatRoom;
        if (sender.getUsers().isEmpty()) {
            chatRoom = new ChatRoom(sender, receiver);
            startChat(sc, chatRoom);
            return;
        }
        for (User user : sender.getUsers()) {
            Seller seller = (Seller) user;
            if (receiver.getUserName().equals(seller.getUserName())) {
                chatRoom = returnChatRoomByReceiver.get(receiver);
                ArrayList<ChatRoom> text = returnChat.get(chatRoom);
                chatPage(text);
                sendMsg(sc, chatRoom);
                return;
            }
        }
        chatRoom = new ChatRoom(sender, receiver);
        startChat(sc, chatRoom);
    }

    private void startChat(Scanner sc, ChatRoom chatRoom) {
        System.out.println("===========================================   Chat page:  ===================================================");
        sendAndSaveMsg(sc, chatRoom);
        returnChatRoomByReceiver.put(chatRoom.getReceiver(), chatRoom);
        returnChatRoomBySender.put(chatRoom.getSender(), chatRoom);
        chatRoom.getSender().getUsers().add(chatRoom.getReceiver());
        chatRoom.getReceiver().getUsers().add(chatRoom.getSender());
        System.out.println("=============================================================================================================");
    }

    private void showAllChats(ChatRoom chatRoom) {
        System.out.println("===========================================   Chat room:  ===================================================");
        for (User user : chatRoom.getSender().getUsers()) {
            if (chatRoom.getSender().getRole().equals(UsersRole.CUSTOMER)) {
                int index = chatRoom.getSender().getUsers().indexOf(user);
                System.out.println((index + 1) + ") " + user.getUserName());
            }
            if (chatRoom.getSender().getRole().equals(UsersRole.SELLER)) {
                int index = chatRoom.getSender().getUsers().indexOf(user);
                System.out.println((index + 1) + ") " + user.getUserName());
            }
        }
        System.out.println("0) Back");
    }

    private void sendMsg(Scanner sc, ChatRoom chatRoom) {
        sendAndSaveMsg(sc, chatRoom);
        System.out.println("=============================================================================================================");
    }

    private void chatPage(ArrayList<ChatRoom> text) {
        System.out.println("===========================================   Chat page:  ====================================================");
        for (ChatRoom chat : text) {
            System.out.println(chat);
        }
    }

    private void sendAndSaveMsg(Scanner sc, ChatRoom chatRoom) {
        sc.nextLine();
        System.out.print("Enter massage(Enter 0 for back): ");
        String msg = sc.next();
        if (msg.matches("0"))
            return;
        chatRoom.getMsg().add(msg);
        chat.add(chatRoom);
        returnChat.put(chatRoom, chat);
    }

    public Map<User, ChatRoom> getReturnChatRoomByReceiver() {
        return returnChatRoomByReceiver;
    }

    public Map<User, ChatRoom> getReturnChatRoomBySender() {
        return returnChatRoomBySender;
    }
}
