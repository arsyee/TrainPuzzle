package com.example.listtest;

import java.util.ArrayList;

public class Main {

    static class CounterObject {
        private static int objectCounter = 0;
        
        private final int number;
        
        public int data;
        
        public CounterObject() {
            number = objectCounter;
            objectCounter++;
            data = number;
            System.out.println("Object #"+number+" has been created.");
        }

        @Override
        public String toString() {
            return "CounterObject(number: "+number+", data: "+data+")";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Creating list:");
        ArrayList<CounterObject> objectList = new ArrayList<CounterObject>();
        System.out.println("  adding object:");
        objectList.add(new CounterObject());
        System.out.println("  adding object:");
        objectList.add(new CounterObject());
        
        System.out.println();
        System.out.println("Iterating through list:");
        for (int i = 0; i < objectList.size(); ++i) {
            CounterObject object = objectList.get(i);
            System.out.println("  found object: " + object);
            object.data = 3;
        }

        System.out.println();
        System.out.println("Iterating through again:");
        for (int i = 0; i < objectList.size(); ++i) {
            CounterObject object = objectList.get(i);
            System.out.println("  found object: " + object);
        }
        
        System.out.println();
        System.out.println("TEST: Passing parameters");

        System.out.println();
        CounterObject testObject = new CounterObject();
        testObject.data = 10;
        change(testObject);
        System.out.println("testObject data after reference change: " + testObject.data);
        changeData(testObject);
        System.out.println("testObject data after data change: " + testObject.data);

        System.out.println();
        int testPrimitive = 10;
        change(testPrimitive);
        System.out.println("testPrimitive after change: " + testPrimitive);
    }

    private static void change(CounterObject testObject) {
        testObject = new CounterObject();
        testObject.data = 20;
    }

    private static void changeData(CounterObject testObject) {
        testObject.data = 30;
    }

    private static void change(int testPrimitive) {
        testPrimitive = 20;
    }

}
