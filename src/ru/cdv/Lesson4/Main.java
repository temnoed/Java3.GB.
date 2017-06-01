package ru.cdv.Lesson4;


import java.io.*;

public class Main {
    private final Object monit = new Object();
    private final Object monit2 = new Object();
    byte firstSymbol = 1;
    byte flag = 1;
    final static byte ABC = 3;


    public static void main(String[] args) throws IOException, InterruptedException {
        Main w = new Main();
        w.printABCABC(ABC);
        Thread.sleep(300);
        w.pishemVfile();

    }


    //---------------------Задача 1-------------------------------------
    void printABCABC(int quant) {
        for (int i = 0; i < quant; i++) {
            new Thread(() -> {
                printNext(firstSymbol++);
            }).start();
        }
    }

    void printNext(int theSym) {
        synchronized (monit) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (flag != theSym) monit.wait();
                    System.out.print((char) (theSym + 64) + " ");
                    if (flag == ABC) flag = 1;
                    else flag++;
                    monit.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //------------------------- задача 2----------------------------
    void pishemVfile() {
        Thread[] t = new Thread[3];
        try (Writer writer = new FileWriter("file10strok.txt")) {

            for (int i = 0; i < 3; i++) { // три потока
                t[i] = new Thread(() -> {
                    try {
                        for (int j = 0; j < 10; j++) // 10 строчек
                            writer.write("String " + j + " " + Thread.currentThread().getName() + "\n");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t[i].start();
            }

            t[0].join();
            t[1].join();
            t[2].join();
            writer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

} // Main
