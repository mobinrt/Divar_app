package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.Admin;
import ir.ac.kntu.util.MainAdmin;

import java.util.Scanner;

public class MainAdminManage extends AdminManage {
    public MainAdminManage() {
        Main.getRunManage().getUsers().add(new MainAdmin("a", "a", "a", "a"));
    }

}
