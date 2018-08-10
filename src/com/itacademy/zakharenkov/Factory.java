package com.itacademy.zakharenkov;

import java.util.Queue;
import java.util.Random;

/**
 * Created by Рома on 10.08.2018.
 */
public class Factory implements Runnable {

    private final Queue<RobotParts> dump;

    public Factory(Queue<RobotParts> dump) {
        this.dump = dump;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.NIGHT_COUNT; i++) {
            addRandomParts();
            try {
                Thread.sleep(Main.DAY_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addRandomParts() {
        Random random = new Random();
        int count = random.nextInt(Main.MAX_RANDOM_DETAILS_COUNT);

        synchronized (dump) {
            for (int i = 0; i <= count; i++) {
                int index = random.nextInt(Main.ROBOT_PARTS_COUNT);
                RobotParts part = RobotParts.values()[index];
                dump.add(part);
                System.out.println(Thread.currentThread().getName() + " - " + part);
            }
        }
    }
}
