package com.itacademy.zakharenkov;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Рома on 11.08.2018.
 */
public class Main {

    public static final int ROBOT_PARTS_COUNT = RobotParts.values().length;
    public static final int MAX_RANDOM_DETAILS_COUNT = 4;
    public static final int NIGHT_COUNT = 100;
    public static final int DAY_DURATION = 100;
    public static final int INITIAL_DETAILS_COUNT = 20;

    public static void main(String[] args) {
        Queue<RobotParts> dump = buildDump(INITIAL_DETAILS_COUNT);
        Map<RobotParts, Integer> firstStorage = buildStorage();
        Map<RobotParts, Integer> secondStorage = buildStorage();
        CrazyScientist firstCrazyScientist = new CrazyScientist(dump, firstStorage);
        CrazyScientist secondCrazyScientist = new CrazyScientist(dump, secondStorage);
        Factory factory = new Factory(dump);

        Thread firstScientistThread = new Thread(firstCrazyScientist, "Первый ученный");
        Thread secondScientistThread = new Thread(secondCrazyScientist, "Второй ученный");
        Thread factoryThread = new Thread(factory, "Фабрика");

        firstScientistThread.start();
        secondScientistThread.start();
        factoryThread.start();

        try {
            firstScientistThread.join();
            secondScientistThread.join();
            factoryThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        determineWinner(firstStorage, secondStorage);
    }

    private static void determineWinner(Map<RobotParts, Integer> firstStorage, Map<RobotParts, Integer> secondStorage) {
        System.out.println(firstStorage);
        System.out.println(secondStorage);
        int firstRobotsCount = getMinValue(firstStorage);
        int secondRobotsCount = getMinValue(secondStorage);
        System.out.println("Первый ученый собрал " + firstRobotsCount + " роботов.");
        System.out.println("Второй ученый собрал " + secondRobotsCount + " роботов.");
        if (firstRobotsCount > secondRobotsCount) {
            System.out.println("Победил первый ученый.");
        } else if (firstRobotsCount < secondRobotsCount) {
            System.out.println("Победил второй ученый.");
        } else {
            System.out.println("Ничья.");
        }
    }

    private static int getMinValue(Map<RobotParts, Integer> map) {
        List<Integer> values = new ArrayList<>(map.values());
        int min = values.get(0);
        for (Integer value : values){
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private static Queue<RobotParts> buildDump(int count) {
        Random random = new Random();
        Queue<RobotParts> dump = new ArrayDeque<>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(ROBOT_PARTS_COUNT);
            dump.add(RobotParts.values()[index]);
            System.out.println("Фабрика - " + RobotParts.values()[index]);
        }
        return dump;
    }

    private static Map<RobotParts, Integer> buildStorage() {
        Map<RobotParts, Integer> storage = new EnumMap<>(RobotParts.class);
        for (int i = 0; i < ROBOT_PARTS_COUNT; i++) {
            storage.put(RobotParts.values()[i], 0);
        }
        return storage;
    }
}
