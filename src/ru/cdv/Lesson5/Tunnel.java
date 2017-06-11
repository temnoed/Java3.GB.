package ru.cdv.Lesson5;

import static ru.cdv.Lesson5.MainRace.semaphore;

public class Tunnel extends Stage {
    Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {

                System.out.println("\n" + c.getName() + " готовится к этапу(ждет): " +
                        description);
                //cb.await();
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " +
                        description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}