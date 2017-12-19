package hu.fallen.trainpuzzle;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int MAX = 1000*1000;
    private static final int SAMPLES = 100;
    
    public static void main(String[] args) {
        for (int max = 10; max < MAX; max *= 10) {
            System.out.println("Statistics for max train size "+max+" (samples: "+SAMPLES+")");
            int tibiSum = 0;
            int tibiMax = 0;
            int pontusSum = 0;
            int pontusMax = 0;
            for (int i = 0; i < SAMPLES; ++i) {
                Train train = new Train(max);
                int tibiSteps = TibiSolver.solve(new Train(train));
                tibiSum += tibiSteps;
                if (tibiSteps > tibiMax) tibiMax = tibiSteps;
                int pontusSteps = PontusSolver.solve(new Train(train));
                pontusSum += pontusSteps;
                if (pontusSteps > pontusMax) pontusMax = pontusSteps;
            }
            System.out.println("Tibi AVG: "+(tibiSum/SAMPLES)+"; MAX: "+tibiMax);
            System.out.println("Pont AVG: "+(pontusSum/SAMPLES)+"; MAX: "+pontusMax);
        }
    }

    private static class PontusSolver {
        public static int solve(Train train) {
            int count = 0;
            while (true) {
                // assume 1st is on, if not, just turn it on
                if (!train.isLightOn()) train.switchLight();
                
                // while light is off, step over and count
                count = 0;
                do {
                    train.forward();
                    count++;
                } while (!train.isLightOn());
                
                // found one 'on', is this the first?
                train.switchLight();
                for (int position = count; position > 0; position--) {
                    train.backward();
                }
                if (!train.isLightOn()) {
                    break;
                }
            }
            
            if (train.guess(count)) {
                // System.out.println("Solution is "+count+", steps taken: "+train.getSteps());
                return train.getSteps();
            }
            return 0;
        }
    }
    
    private static class TibiSolver {
        private enum SolverState { PROGRESSING, BACKCHECKING, RETURNING } 
        
        public static int solve(Train train) {
            int position = 0;
            
            List<Boolean> lights = new ArrayList<Boolean>();
            lights.add(train.isLightOn());
            int size = 0;
            int assumedSize = 0;
            
            boolean alteredLightState = true;
            SolverState state = SolverState.PROGRESSING;
            
            while (size == 0) {
                // train.debug();
                // debug(position, lights, state);
                
                switch (state) {
                    case PROGRESSING:
                        train.forward();
                        position++;
                        lights.add(train.isLightOn());
                        if (position % 2 == 1 && isRepeating(lights)) {
                            train.switchLight();
                            alteredLightState = train.isLightOn();
                            assumedSize = position / 2 + 1;
                            state = SolverState.BACKCHECKING;
                        }
                        continue;
                    case BACKCHECKING:
                        train.backward();
                        position--;
                        if (position == assumedSize - 1) {
                            if (alteredLightState == train.isLightOn()) {
                                size = assumedSize;
                            } else {
                                state = SolverState.RETURNING;
                            }
                        }
                        continue;
                    case RETURNING:
                        train.forward();
                        position++;
                        if (position == assumedSize * 2 - 1) {
                            train.switchLight();
                            state = SolverState.PROGRESSING;
                        }
                        continue;
                    default:
                        break;
                }
            }
            if (train.guess(size)) {
                //System.out.println("Solution is "+size+", steps taken: "+train.getSteps());
                return train.getSteps();
            }
            return 0;
        }

        private static void debug(int position, List<Boolean> lights, SolverState state) {
            StringBuilder message = new StringBuilder("solve: ");
            message.append(position).append(" : ");
            for (int i = 0; i < lights.size(); ++i) {
                message.append(lights.get(i)?1:0);
            }
            System.out.println(message.toString());
            System.out.println(state);
        }

        private static boolean isRepeating(List<Boolean> lights) {
            for (int i = 0; i < lights.size() / 2; ++i) {
                Boolean a = lights.get(i);
                Boolean b = lights.get(lights.size() / 2 + i); 
                if (!a.equals(b)) return false;
            }
            return true;
        }
    }

}
