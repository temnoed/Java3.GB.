package ru.cdv.Lesson3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


public class Main {

    public static void main(String[] args) {

        // Задача 1.
        for (int v : read50bytes("50bytes.txt")) {
            System.out.print(v + " ");
        }

        // Задача 2.
        sewFiles(new String[]{"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"},
                "bigfile.txt");


        // Задача 3.
        long t = System.currentTimeMillis();
        printPage(readPage(2, "pages.txt"));
        System.out.println("\n msec = " + (System.currentTimeMillis() - t));

    }


    static byte[] read50bytes(String fileName) {
        long size = (new File(fileName)).length();
        byte[] bytes = new byte[(int) size];

        try (FileInputStream ins = new FileInputStream(fileName)) {
            ins.read(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    static void sewFiles(String[] fileNames, String bigFile) {
        ArrayList<FileInputStream> inStreams = new ArrayList<>();

        try {
            for (String v : fileNames)
                inStreams.add(new FileInputStream(v));
            Enumeration<FileInputStream> enumInStreams = Collections.enumeration(inStreams);

            try (SequenceInputStream seq = new SequenceInputStream(enumInStreams);
                 FileOutputStream out = new FileOutputStream(bigFile)) {

                int readByte = seq.read();
                while (readByte != -1) {
                    out.write(readByte);
                    readByte = seq.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            // вдруг Sequence не откроется, тогда входящие закроем тут:
        } finally {
            inStreams.forEach(s -> {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        }

    }


    static byte[] readPage(int pageNum, String fileName) {
        final int PAGE_LENGHT = 1800;
        long size = (new File(fileName)).length();

        if ((pageNum - 1) * PAGE_LENGHT >= size) {
            System.out.println("\nТакой страницы нет.\n");
            return null;
        }

        byte[] buffer = new byte[PAGE_LENGHT];

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            raf.seek(pageNum * PAGE_LENGHT - PAGE_LENGHT);
            raf.readFully(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    static void printPage(byte[] bytes) {
        if (bytes == null) return;

        System.out.println();
        for (byte oneByte : bytes) {
            System.out.print((char) oneByte);
        }
    }


}