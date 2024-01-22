package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.users.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        currentUser.getAllChats().stream()
                .filter(chatRoom -> chatRoom.getSender().equals(user))
                .findFirst()
                .ifPresent(chatRoom -> {
                    swapRole(chatRoom, user, currentUser);
                    chatPage(chatRoom);
                    sendMsg(sc, chatRoom);
                });

        currentUser.getAllChats().stream()
                .filter(chatRoom -> chatRoom.getReceiver().equals(user))
                .findFirst()
                .ifPresent(chatRoom -> {
                    chatPage(chatRoom);
                    sendMsg(sc, chatRoom);
                });
    }

    public void checkExistenceChat(Scanner sc, Customer sender, Seller receiver) {
        ChatRoom chatRoom;
        if (sender.getUsers().isEmpty()) {
            createChatRoom(sc, sender, receiver);
            return;
        }
        Optional<ChatRoom> existingChatRoom = sender.getUsers().stream()
                .filter(user -> user instanceof Seller)
                .map(user -> (Seller) user)
                .filter(receiver::equals)
                .findFirst()
                .map(returnChatRoomByReceiver::get);

        if (existingChatRoom.isPresent()) {
            chatPage(existingChatRoom.get());
            sendMsg(sc, existingChatRoom.get());
        } else {
            createChatRoom(sc, sender, receiver);
        }
    }

    private void showAllChats(User sender) {
        System.out.println("===========================================   Chat room:  ===================================================");
        sender.getUsers().stream()
                .map(user -> {
                    if (sender.getRole().equals(UsersRole.CUSTOMER)) {
                        Seller seller = (Seller) user;
                        int index = sender.getUsers().indexOf(seller);
                        return (index + 1) + ") " + seller.getUserName();
                    } else {
                        Customer customer = (Customer) user;
                        int index = sender.getUsers().indexOf(customer);
                        return (index + 1) + ") " + customer.getUserName();
                    }
                })
                .forEach(System.out::println);
        System.out.println("0) Back");
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

    private void swapRole(ChatRoom chatRoom, User receiver, User crrentUser) {
        chatRoom.setSender(crrentUser);
        chatRoom.setReceiver(receiver);
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
        chatRoom.getMsg().forEach(System.out::println);
    }
}
