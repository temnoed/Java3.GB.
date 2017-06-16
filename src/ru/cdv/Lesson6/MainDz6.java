package ru.cdv.Lesson6;


import org.junit.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainDz6 {

    private static MainDz6 mainObj = new MainDz6();
    private int[] theArr;

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mainObj.task1(1, 2, 3, 4, 5, 6, 7)));

    }

    @Test
    public void testTask1() {
        int assert1[] = {5, 6};
        Assert.assertEquals(assert1, mainObj.task1(1, 2, 3, 4, 5, 6, 7));
    }


    private Integer[] task1(Integer... arr) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));
        if (!list.contains(4)) throw new RuntimeException("В массиве нет четвёрки!");

        int index = list.indexOf(4);
        Integer[] result = new Integer[arr.length - index - 1];

        for (int i = 0; i < arr.length - index - 1; i++) {
            result[i] = arr[index + i + 1];
        }

        return result;
    }


}
