package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.users.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatRoomManage implements Choice {
    private final Map<User, ChatRoom> returnChatRoomByReceiver;

    public ChatRoomManage() {
        returnChatRoomByReceiver = new HashMap<>();
    }

    public void chatBox(Scanner sc, User currentUser) {
        if (currentUser.getUsers().isEmpty()) {
            System.out.println("There is no chat!");
            return;
        }
        showAllChats(currentUser);
        int choice = getChoice(sc, currentUser.getUsers().size() + 1);
        if (choice == 0)
            return;

        User user = currentUser.getUsers().get(--choice);
        for (ChatRoom chatRoom : currentUser.getAllChats()) {
            if (chatRoom.getSender().equals(user)) {
                swapRole(chatRoom, user, currentUser);
                chatPage(chatRoom);
                sendMsg(sc, chatRoom);
            }
            if (chatRoom.getReceiver().equals(user)) {
                chatPage(chatRoom);
                sendMsg(sc, chatRoom);
            }
        }
    }

    private void swapRole(ChatRoom chatRoom, User receiver, User crrentUser) {
        chatRoom.setSender(crrentUser);
        chatRoom.setReceiver(receiver);
    }

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
                chatPage(chatRoom);
                sendMsg(sc, chatRoom);
                return;
            }
        }
        createChatRoom(sc, sender, receiver);
    }

    private void createChatRoom(Scanner sc, Customer sender, Seller receiver) {
        ChatRoom chatRoom;
        chatRoom = new ChatRoom(sender, receiver);
        sender.getAllChats().add(chatRoom);
        receiver.getAllChats().add(chatRoom);
        sender.getUsers().add(receiver);
        receiver.getUsers().add(sender);
        returnChatRoomByReceiver.put(chatRoom.getReceiver(), chatRoom);
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
        sc.nextLine();
        System.out.print("Enter massage(Enter 0 for back): ");
        String msg = sc.nextLine();
        if (msg.matches("0"))
            return;
        chatRoom.getMsg().add(chatRoom.getSender().getUserName() + ": " + msg);
        System.out.println("=============================================================================================================");
    }

    private void chatPage(ChatRoom chatRoom) {
        System.out.println("===========================================   Chat page:  ====================================================");
        for (String msg : chatRoom.getMsg()) {
            System.out.println(msg);
        }
    }
}
