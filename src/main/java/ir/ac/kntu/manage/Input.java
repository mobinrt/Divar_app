package ir.ac.kntu.manage;

import java.util.InputMismatchException;
import java.util.Scanner;

public interface Input {
    default int getChoice(Scanner sc, int bound) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                if (choice >= 0 && choice < bound) {
                    return choice;
                } else {
                    System.out.println("Invalid Input!");
                    return getChoice(sc, bound);
                }
            } catch (InputMismatchException e) {
                System.out.println("You should enter number. Please try again.");
                sc.next();
            }
        }
    }

    default double inputDouble(Scanner sc, String text) {
        while (true) {
            try {
                System.out.print(text);
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("You should enter number. Please try again.");
                sc.next();
            }
        }
    }
}
