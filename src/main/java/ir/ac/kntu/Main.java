package ir.ac.kntu;

import ir.ac.kntu.manage.RunManage;

/**
 * AP second project
 * 1402 3rd semester
 * @author mobin
 */

public class Main {
    private static final RunManage RUN_MANAGE = new RunManage();

    public static void main(String[] args) {
        RUN_MANAGE.run();
    }

    public static RunManage getRunManage() {
        return RUN_MANAGE;
    }
}
