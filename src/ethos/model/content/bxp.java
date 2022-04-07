package ethos.model.content;

import ethos.Config;
import ethos.model.content.wogw.Wogw;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class bxp {

    public static long EXPERIENCE_TIMER = 0;


    public static void init() {
        try {
            File f = new File("./data/bxp.txt");
            Scanner sc = new Scanner(f);
            int i = 0;
            while (sc.hasNextLine()) {
                i++;
                String line = sc.nextLine();
                String[] details = line.split("=");
                String amount = details[1];
                System.out.println("attempting to load bxp " + i);
                switch (i) {
                    case 1:
                        EXPERIENCE_TIMER = (int) Long.parseLong(amount);
                        System.out.println("Loading bonus xp and setting it " + amount +" timer: " + EXPERIENCE_TIMER);
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./data/bxp.txt"));
            out.write("experience-timer=" + EXPERIENCE_TIMER);
            out.newLine();
            out.close();
            System.out.println("saved bonus exp " + EXPERIENCE_TIMER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTime(int amt) {
        PlayerHandler.executeGlobalMessage("@blu@ " + (amt - 2) + " minutes of bonus XP has been given to everyone!");
        Config.bonusXP = true;
    }

    public static void checkTime() {
        System.out.println(TimeUnit.MILLISECONDS.toMinutes(EXPERIENCE_TIMER * 600) + " Minutes");
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(EXPERIENCE_TIMER * 600));
    }

    public static void appendBonus() {
        if (EXPERIENCE_TIMER > 0 && !Config.bonusXP) {
            Config.bonusXP = true;
            return;
        }
    }
}
