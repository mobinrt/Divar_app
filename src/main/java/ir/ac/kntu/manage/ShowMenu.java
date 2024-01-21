package ir.ac.kntu.manage;

public class ShowMenu {
    public static void showMenu(String text) {
        System.out.println("==============================================================================================================");
        String[] icon = text.split(", ");
        for (int i = 1; i <= icon.length; i++) {
            System.out.println(i + ". " + icon[i - 1]);
        }
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    public static <T> void showMenuEnum(T[] icons) {
        System.out.println("==============================================================================================================");
        int i = 1;
        for (T icon : icons) {
            String temp = capitalizeFirstLetter(icon);
            System.out.println(i + ". " + temp);
            i++;
        }
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