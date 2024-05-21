package dev.jonahm.cellularautomata.utils;

public class Threading {

    public static void runAsync(Runnable runnable) {
        new Thread(runnable).start();
    }

}
