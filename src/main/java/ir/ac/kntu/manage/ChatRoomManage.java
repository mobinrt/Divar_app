package ir.ac.kntu.manage;

import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatRoomManage {
    private final Map<User, ChatRoom> showChat;
    private final ArrayList<ChatRoom> chat;

    public ChatRoomManage() {
        chat = new ArrayList<>();
        showChat = new HashMap<>();
    }

    public void startChat(Scanner sc, ChatRoom chatRoom) {
        System.out.println("===========================================   Chat page:  ============================================");
        System.out.print("Enter massage (enter 0 for back): ");
        String msg = sc.nextLine();
        if (chatRoom.getMsg().matches("0"))
            return;
        chatRoom.setMsg(msg);
        chatRoom.getSender().getUsers().add(chatRoom.getReceiver());
        chat.add(chatRoom);
        System.out.println("======================================================================================================");
    }

    public void showAllChats(ChatRoom chatRoom) {
        System.out.println("===========================================   Chat room:  ============================================");
        for (User user : chatRoom.getSender().getUsers()) {
            if (chatRoom.getSender().getRole().equals(UsersRole.CUSTOMER)) {
                Seller seller = (Seller) user;
                System.out.println(chatRoom.getSender().getUsers().indexOf(seller) + 1 + ") " + seller);
            }
            if (chatRoom.getSender().getRole().equals(UsersRole.SELLER)) {
                Customer customer = (Customer) user;
                System.out.println(chatRoom.getSender().getUsers().indexOf(customer) + 1 + ") " + customer);
            }
        }
        System.out.println("0) Back");
    }

    public void sendMsg(Scanner sc, ChatRoom chatRoom) {
        System.out.print("Enter massage: ");
        String msg = sc.nextLine();
        if (chatRoom.getMsg().matches("0"))
            return;
        chatRoom.setMsg(msg);
        chat.add(chatRoom);
        System.out.println("======================================================================================================");
    }

    public void chatPage() {
        System.out.println("===========================================   Chat page:  ============================================");
        for (ChatRoom chat : chat) {
            System.out.println(chat);
        }
    }

    public Map<User, ChatRoom> getShowChat() {
        return showChat;
    }

    public ArrayList<ChatRoom> getChat() {
        return chat;
    }
}
