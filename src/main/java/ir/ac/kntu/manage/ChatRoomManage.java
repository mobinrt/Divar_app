package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatRoomManage {
    private final Map<User, ChatRoom> returnChatRoomByReceiver;
    private final Map<ChatRoom, ArrayList<String>> returnChat;

    public ChatRoomManage() {
        returnChatRoomByReceiver = new HashMap<>();
        returnChat = new HashMap<>();
    }

    public void chatBox(Scanner sc, User sender) {
        if (sender.getUsers().isEmpty()) {
            System.out.println("There is no chat!");
            return;
        }
        showAllChats(sender);
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        if (choice == 0)
            return;
        User user = sender.getUsers().get(--choice);
        ChatRoom chatRoom = returnChatRoomByReceiver.get(user);
        chatPage(sender, returnChat.get(chatRoom));
        sendMsg(sc, chatRoom);
    }

//    private void swapRoleSeller(, User currentUser) {
//        if (!chatRoom.getSender().equals(currentUser)) {
//            User userReceiver = chatRoom.getSender();
//            User userSender = chatRoom.getReceiver();
//            chatRoom.setSender(userReceiver);
//            chatRoom.setReceiver(userSender);
//        }
//    }

    public void checkExistenceChat(Scanner sc, Customer sender, Seller receiver) {
        ChatRoom chatRoom;
        if (sender.getUsers().isEmpty()) {
            createChatRoom(sc, sender, receiver);
            return;
        }
        for (User user : sender.getUsers()) {
            Seller seller = (Seller) user;
            if (receiver.equals(seller)) {
                chatRoom = returnChatRoomByReceiver.get(receiver);
                chatPage(sender, returnChat.get(chatRoom));
                sendMsg(sc, chatRoom);
                return;
            }
        }
        createChatRoom(sc, sender, receiver);
    }

    private void createChatRoom(Scanner sc, Customer sender, Seller receiver) {
        ChatRoom chatRoom;
        chatRoom = new ChatRoom(sender, receiver);
//        chatRoom.getAllChats().add(chatRoom);
        sender.getUsers().add(receiver);
        receiver.getUsers().add(sender);
        returnChatRoomByReceiver.put(chatRoom.getReceiver(), chatRoom);
        returnChat.put(chatRoom, chatRoom.getMsg());
        sendMsg(sc, chatRoom);
    }

    private void showAllChats(User sender) {
        System.out.println("===========================================   Chat room:  ===================================================");
        for (User user : sender.getUsers()) {
            if (sender.getRole().equals(UsersRole.CUSTOMER)) {
                Seller seller = (Seller) user;
                int index = sender.getUsers().indexOf(seller);
                System.out.println((index + 1) + ") " + seller.getUserName());
            }
            if (sender.getRole().equals(UsersRole.SELLER)) {
                Customer customer = (Customer) user;
                int index = sender.getUsers().indexOf(customer);
                System.out.println((index + 1) + ") " + customer.getUserName());
            }
        }
        System.out.println("0) Back");
    }

    private void sendMsg(Scanner sc, ChatRoom chatRoom) {
        sendAndSaveMsg(sc, chatRoom);
        System.out.println("=============================================================================================================");
    }

    private void chatPage(User sender, ArrayList<String> text) {
        System.out.println("===========================================   Chat page:  ====================================================");
        for (String msg : text) {
            System.out.println(sender.getUserName() + ": " + msg);
        }
    }

    private void sendAndSaveMsg(Scanner sc, ChatRoom chatRoom) {
        sc.nextLine();
        System.out.print("Enter massage(Enter 0 for back): ");
        String msg = sc.next();
        if (msg.matches("0"))
            return;
        chatRoom.getMsg().add(msg);
    }

    public Map<User, ChatRoom> getReturnChatRoomByReceiver() {
        return returnChatRoomByReceiver;
    }

    public Map<ChatRoom, ArrayList<String>> getReturnChat() {
        return returnChat;
    }
}
