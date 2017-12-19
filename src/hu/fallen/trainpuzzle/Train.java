package hu.fallen.trainpuzzle;

import java.util.Random;

public class Train {
    
    private final int size;
    private final boolean[] lights;
    
    private int currentPosition;
    private int steps;
    
    private boolean broken;
    
    public Train(int max) {
        Random random = new Random();
        size = random.nextInt(max-1)+1;
        lights = new boolean[size];
        for (int i = 0; i < size; ++i) {
            lights[i] = random.nextBoolean();
        }
        currentPosition = 0;
        steps = 0;
        broken = false;
    }
    
    public Train(Train t) {
        size = t.size;
        currentPosition = t.currentPosition;
        steps = t.steps;
        broken = t.broken;
        lights = new boolean[size];
        for (int i = 0; i < size; ++i) lights[i] = t.lights[i];
    }
    
    public void forward() {
        if (broken) throw new RuntimeException();
        steps++;
        currentPosition = (currentPosition+1)%size;
    }
    
    public void backward() {
        if (broken) throw new RuntimeException();
        steps++;
        currentPosition = (size+currentPosition-1)%size;
    }
    
    public void switchLight() {
        if (broken) throw new RuntimeException();
        lights[currentPosition] = !lights[currentPosition];
    }

    public boolean isLightOn() {
        if (broken) throw new RuntimeException();
        return lights[currentPosition];
    }
    
    public boolean guess(int guessedSize) {
        if (broken) throw new RuntimeException();
        if (size == guessedSize) return true;
        broken = true;
        throw new RuntimeException();
    }
    
    public int getSteps() {
        return steps;
    }
    
    public void debug() {
        StringBuilder message = new StringBuilder("train: ");
        message.append(currentPosition).append(" : ");
        for (int i = 0; i < lights.length; ++i) {
            message.append(lights[i]?1:0);
        }
        System.out.println(message.toString());
    }
}
