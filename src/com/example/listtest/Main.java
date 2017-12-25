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
}

}
