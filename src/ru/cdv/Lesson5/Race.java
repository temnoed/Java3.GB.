package ru.cdv.Lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

class Race {
    private ArrayList<Stage> stages;

     ArrayList<Stage> getStages() {
        return stages;
    }

     Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}