package ir.ac.kntu.util;

import java.util.Scanner;

public interface Location {
    int[][] location = new int[10][10];

    default void setLocation(int[][] location, Scanner sc) {
        location[0][0] = 1;
    }
}
