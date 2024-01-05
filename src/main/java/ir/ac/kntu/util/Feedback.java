package ir.ac.kntu.util;

import ir.ac.kntu.util.users.Customer;

import java.util.ArrayList;

public class Feedback {
    private final Customer sender;
    private String text;

    public Feedback(Customer sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return sender.getUserName() + " -> " + "[" + text + "]";
    }

    public void setText(String text) {
        this.text = text;
    }
}
