package ru.cdv.Lesson1;

import java.util.ArrayList;
import java.util.Arrays;


public class Lesson1Main {

    public static void main(String[] args) {

        System.out.println("\n Задача1.");
        String[] theArray = {"one", "two", "three"};
        System.out.println(swapElement(theArray, 1, 2));


        System.out.println("\n Задача2.");
        System.out.println(makeArrayList(theArray));


        System.out.println("\n Задача3.");
        Apple anApple = new Apple();
        Orange anOrange = new Orange();

        Box<Apple> box1 = new Box<>(anApple);
        Box<Orange> box2 = new Box<>(anOrange);
        box1.add(16, anApple);
        box2.add(16,anOrange);
        System.out.println("\nBox1 вес = " + box1.getWeight());
        System.out.println("Box2 вес = " + box2.getWeight());

        System.out.println("\nBox1 сравнить с box2 = " + box1.compare(box2));

        Box<Orange> box3 = new Box<>(anOrange);
        box3.add(5,anOrange);
        System.out.println("\nBox2 вес = " + box2.getWeight());
        System.out.println("Box3 вес = " + box3.getWeight());
        System.out.println("Пересыпаем box2 -> box3");
        box2.reloadTo(box3);
        System.out.println("Box2 вес = " + box2.getWeight());
        System.out.println("Box3 вес = " + box3.getWeight());
    }


    /**
     * Задача 1.
     * Поменять местами два элемента массива типа, используя <T>
     */
    private static <T> boolean swapElement(T[] anArray, int index1, int index2) {
        T buffer;

        if (anArray == null || index1 > anArray.length - 1 || index2 > anArray.length - 1 ||
                index1 < 0 || index2 < 0) return false;

        buffer = anArray[index1];
        anArray[index1] = anArray[index2];
        anArray[index2] = buffer;
        return true;
    }


    /**
     * Задача 2.
     * Переделать массив в ArrayList<T>
     */
    private static <T> ArrayList<T> makeArrayList(T[] anArray) {

        if (anArray == null) return null;

        return new ArrayList<>(Arrays.asList(anArray));
    }

}