package ir.ac.kntu.manage;

import java.util.stream.IntStream;

public class ShowMenu {
    public static void showMenu(String text) {
        System.out.println("==============================================================================================================");
        String[] icon = text.split(", ");
        IntStream.range(0, icon.length)
                .mapToObj(i -> (i + 1) + ". " + icon[i])
                .forEach(System.out::println);
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    public static <T> void showMenuEnum(T[] icons) {
        System.out.println("==============================================================================================================");
        IntStream.range(0, icons.length)
                .mapToObj(i -> (i + 1) + ". " + capitalizeFirstLetter(icons[i]))
                .forEach(System.out::println);
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private static <T> String capitalizeFirstLetter(T icon) {
        String temp = icon.toString();
        temp = temp.replace("_", " ");
        temp = temp.toLowerCase();
        temp = Character.toUpperCase(temp.charAt(0)) + temp.substring(1);
        return temp;
    }
}