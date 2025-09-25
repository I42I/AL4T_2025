package be.ecam.basics.exercises;

public class Counter {
    public static int count(int start, int steps) {
        int c = (int) start;
        for (int i = 0; i < steps; i++) {
            c++;
        }
        return c;
    }
}
