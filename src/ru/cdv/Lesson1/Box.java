package ru.cdv.Lesson1;

import java.util.ArrayList;


class Box<E extends Fruit> extends ArrayList<E> {
    private E fruit;

    Box(E theFruit) {
        fruit = theFruit;
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public void add(int count, E e) {
        for (int i = 1; i <= count; i++) this.add(e);
        this.trimToSize();
    }


    float getWeight() {
        if (this.isEmpty()) return 0f;
        return this.size() * (this.fruit.getWeight());
    }


    boolean compare(Box thatBox) {
        return Math.abs(this.getWeight() - thatBox.getWeight()) < 0.01f;
    }

    /**
     * Пересыпаем из коробки в коробку.
     */
    void reloadTo(Box<E> thatBox) {
        thatBox.addAll(this);
        this.clear();
    }

}







