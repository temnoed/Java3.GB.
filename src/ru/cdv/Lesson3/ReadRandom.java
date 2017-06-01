package ru.cdv.Lesson3;

import java.io.*;

/**
 * апгрейженый" способ с буфером. выгода не заметна
 * http://www.oracle.com/technetwork/articles/javase/perftuning-137844.html#RandomAccess
 */

public class ReadRandom {
    private static final int DEFAULT_BUFSIZE = 4096;
    private RandomAccessFile raf;
    private byte inbuf[];
    private long startpos = -1;
    private long endpos = -1;
    private int bufsize;

    public ReadRandom(String name) throws FileNotFoundException {
        this(name, DEFAULT_BUFSIZE);
    }

    public ReadRandom(String name, int b) throws FileNotFoundException {
        raf = new RandomAccessFile(name, "r");
        bufsize = b;
        inbuf = new byte[bufsize];
    }

    public int read(long pos) {
        if (pos < startpos || pos > endpos) {
            long blockstart = (pos / bufsize) * bufsize;
            int n;
            try {
                raf.seek(blockstart);
                n = raf.read(inbuf);
            } catch (IOException e) {
                return -1;
            }
            startpos = blockstart;
            endpos = blockstart + n - 1;
            if (pos < startpos || pos > endpos) return -1;
        }
        return inbuf[(int) (pos - startpos)] & 0xffff;
    }

    public void close() throws IOException {
        raf.close();
    }

    public static void main(String args[]) {
        long t = System.currentTimeMillis();
        try {
            ReadRandom rr = new ReadRandom("pages.txt");
            long pos = 0;
            int c;
            byte buf[] = new byte[1];

            for ( pos = 10000; ((pos < 20000) && ((c = rr.read(pos)) != -1)) ; ) {
                pos++;
                buf[0] = (byte) c;
                System.out.write(buf, 0, 1);
            }
            /*while ((c = rr.read(pos)) != -1) {

            }*/



            rr.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("\n msec = " + (System.currentTimeMillis() - t));
    }
}
