package ir.ac.kntu.manage;

import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatRoomManage {
    private final Map<Seller, ChatRoom> returnChatRoomBySeller;
    private final Map<Customer, ChatRoom> returnChatRoomByCustomer;
    private final Map<ChatRoom, ArrayList<ChatRoom>> returnChat;
    private final ArrayList<ChatRoom> chat;

    public ChatRoomManage() {
        chat = new ArrayList<>();
        returnChatRoomBySeller = new HashMap<>();
        returnChat = new HashMap<>();
        returnChatRoomByCustomer = new HashMap<>();
    }

    public void chatBox(Scanner sc, ChatRoom chatRoom, Customer customer) {
        showAllChats(chatRoom);
        int choice = sc.nextInt();
        if (choice == 0)
            return;
        Seller seller = (Seller) customer.getUsers().get(--choice);
        chatRoom = returnChatRoomBySeller.get(seller);
        ArrayList<ChatRoom> chat = returnChat.get(chatRoom);
        chatPage(chat);
        sendMsg(sc, chatRoom);
    }

    public void chatBox(Scanner sc, ChatRoom chatRoom, Seller seller) {
        showAllChats(chatRoom);
        int choice = sc.nextInt();
        if (choice == 0)
            return;
        Customer customer = (Customer) seller.getUsers().get(--choice);
        chatRoom = returnChatRoomByCustomer.get(customer);
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
            if (receiver.equals(seller)) {
                chatRoom = returnChatRoomBySeller.get((Seller) receiver);
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
        System.out.println("===========================================   Chat page:  ============================================");
        System.out.print("Enter massage (enter 0 for back): ");
        String msg;
        do {
            msg = sc.nextLine();
        } while (!msg.matches("0"));
        chatRoom.setMsg(msg);
        chat.add(chatRoom);
        returnChat.put(chatRoom, chat);
        returnChatRoomBySeller.put((Seller) chatRoom.getReceiver(), chatRoom);
        returnChatRoomByCustomer.put((Customer) chatRoom.getSender(), chatRoom);
        chatRoom.getSender().getUsers().add(chatRoom.getReceiver());
        chatRoom.getReceiver().getUsers().add(chatRoom.getSender());
        System.out.println("======================================================================================================");
    }

    private void showAllChats(ChatRoom chatRoom) {
        System.out.println("===========================================   Chat room:  ============================================");
        for (User user : chatRoom.getSender().getUsers()) {
            if (chatRoom.getSender().getRole().equals(UsersRole.CUSTOMER)) {
                Seller seller = (Seller) user;
                System.out.println(chatRoom.getSender().getUsers().indexOf(seller) + 1 + ") " + seller.getUserName());
            }
            if (chatRoom.getSender().getRole().equals(UsersRole.SELLER)) {
                Customer customer = (Customer) user;
                System.out.println(chatRoom.getSender().getUsers().indexOf(customer) + 1 + ") " + customer.getUserName());
            }
        }
        System.out.println("0) Back");
    }

    private void sendMsg(Scanner sc, ChatRoom chatRoom) {
        System.out.print("Enter massage: ");
        String msg;
        do {
            msg = sc.nextLine();
        } while (!msg.matches("0"));
        chatRoom.setMsg(msg);
        chat.add(chatRoom);
        returnChat.put(chatRoom, chat);
        System.out.println("======================================================================================================");
    }

    private void chatPage(ArrayList<ChatRoom> text) {
        System.out.println("===========================================   Chat page:  ============================================");
        for (ChatRoom chat : text) {
            System.out.println(chat);
        }
    }

    public ArrayList<ChatRoom> getChat() {
        return chat;
    }

    public Map<Seller, ChatRoom> getReturnChatRoomBySeller() {
        return returnChatRoomBySeller;
    }

    public Map<ChatRoom, ArrayList<ChatRoom>> getReturnChat() {
        return returnChat;
    }

    public Map<Customer, ChatRoom> getReturnChatRoomByCustomer() {
        return returnChatRoomByCustomer;
    }
}
