package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.users.Customer;
import ir.ac.kntu.util.Feedback;
import ir.ac.kntu.util.Product;

import java.util.Scanner;

public class FeedbackManage implements Choice {
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
        Feedback feedback = new Feedback(customer);
        System.out.print("Enter your feedback: ");
        String text = sc.nextLine();
        feedback.setText(text);
        product.getSeller().getFeedback().add(feedback);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }
}
