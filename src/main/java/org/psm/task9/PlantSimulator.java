package org.psm.task9;

public class PlantSimulator {

    public static String generate(int iterations) {
        String current = "X";
        for (int i = 0; i < iterations; i++) {
            StringBuilder next = new StringBuilder();
            for (char c : current.toCharArray()) {
                switch (c) {
                    case 'X':
                        next.append("F+[[X]-X]-F[-FX]+X");
                        break;
                    case 'F':
                        next.append("FF");
                        break;
                    default:
                        next.append(c);
                        break;
                }
            }
            current = next.toString();
        }
        return current;
    }
}