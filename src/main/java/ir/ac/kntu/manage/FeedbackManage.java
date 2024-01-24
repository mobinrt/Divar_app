package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.users.Customer;
import ir.ac.kntu.util.Feedback;
import ir.ac.kntu.util.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class FeedbackManage implements Input {
    public void handleFeedback(Scanner sc, Customer customer, Product product) {
        ShowMenu.showMenu("Show feedbacks, Add your feedback");
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> Main.getRunManage().getSellerManage().showFeedbacks(product.getSeller());
            case 2 -> feedbackToSeller(sc, customer, product);
            default -> {
            }
        }
    }

    private void feedbackToSeller(Scanner sc, Customer customer, Product product) {
        sc.nextLine();
        ArrayList<Feedback> temp = product.getSeller().getFeedback();
        Feedback feedback = new Feedback(customer);
        System.out.print("Enter your feedback: ");
        String text = sc.nextLine();
        feedback.setText(text);
        temp.add(feedback);
        product.getSeller().setFeedback(temp);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }
}