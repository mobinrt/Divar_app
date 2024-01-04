package ir.ac.kntu.util;

import java.util.ArrayList;

@FunctionalInterface
public interface ChatPage {
    void choiceChat(ArrayList<User> chatPages);
}
