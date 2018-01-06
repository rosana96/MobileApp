package com.example.rosana.booksapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosana on 06.01.2018.
 */

public class MySubject {
    private static List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer obj) {
        observers.add(obj);
    }

    public static void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

}
