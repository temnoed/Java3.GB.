package ru.cdv.Lesson5;

import java.util.concurrent.CountDownLatch;

public class Main {
    private static final int CARS_COUNT = 4;
    static final CountDownLatch readyStart = new CountDownLatch(CARS_COUNT + 1);


    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (Car aCar : cars) {
            new Thread(aCar).start();
        }

        while (readyStart.getCount() > 1) //Проверяем, собрались ли все автомобили
            Thread.sleep(100);

        readyStart.countDown();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}











