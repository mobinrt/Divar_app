package ir.ac.kntu.util;

import java.util.ArrayList;

public interface ShowList {
    default void showList(ArrayList<Product> list) {
        list.stream()
                .map(product -> list.indexOf(product) + 1 + ") " + product)
                .forEach(System.out::println);
    }
}
