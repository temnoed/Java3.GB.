package ru.cdv.Lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainRace {
    private static final int CARS_COUNT = 4;
    static final CountDownLatch finish = new CountDownLatch(CARS_COUNT);
    static final Semaphore semaphore = new Semaphore(CARS_COUNT / 2);
    static final CyclicBarrier cb = new CyclicBarrier(5);
    static volatile boolean isWinner = false;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (Car aCar : cars) {
            new Thread(aCar).start();
        }
        cb.await(); // ready ?
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        cb.await(); // start !

        finish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}











