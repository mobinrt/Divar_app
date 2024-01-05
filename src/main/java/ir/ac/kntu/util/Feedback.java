package ir.ac.kntu.util;

import java.util.ArrayList;

public class Feedback {
    private final Customer sender;
    private final ArrayList<String> text;

    public Feedback(Customer sender) {
        this.sender = sender;
        text = new ArrayList<>();
    }

    @Override
    public String toString() {
        return sender.getUserName() + " -> " + text;
    }

    public ArrayList<String> getText() {
        return text;
    }
}
