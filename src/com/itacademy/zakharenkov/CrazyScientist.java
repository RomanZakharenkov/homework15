package com.itacademy.zakharenkov;

import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Рома on 11.08.2018.
 */
public class CrazyScientist implements Runnable {

    private final Queue<RobotParts> dump;
    private final Map<RobotParts, Integer> storage;

    public CrazyScientist(Queue<RobotParts> dump, Map<RobotParts, Integer> storage) {
        this.dump = dump;
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.NIGHT_COUNT; i++) {
            pullRandomParts();
            try {
                Thread.sleep(Main.DAY_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pullRandomParts() {
        Random random = new Random();

        synchronized (dump) {
            int count = Math.min(random.nextInt(Main.MAX_RANDOM_DETAILS_COUNT), dump.size());
            for (int i = 0; i < count; i++) {
                RobotParts part = dump.poll();
                storage.put(part, storage.get(part) + 1);
                System.out.println(Thread.currentThread().getName() + " - " + part);
            }
        }
    }
}
