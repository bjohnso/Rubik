package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class State {

    private String rules[];
    private HashMap<String, Plane> planeMap = new HashMap<String, Plane>();

    public State() {
        initPlaneMap();
        initRules();
    }

    private void initRules() {
        String temp[] = {
            "FURDL",
            "BDRUL",
            "UFLBR",
            "DFRBL",
            "LFDBU",
            "RFUBD"
        };
        rules = temp;
    }

    private void initPlaneMap() {
        planeMap.put("F", new Plane("F", Color.RED, "B"));
        planeMap.put("R", new Plane("R", Color.GREEN, "L"));
        planeMap.put("B", new Plane("B", Color.MAGENTA, "F"));
        planeMap.put("L", new Plane("L", Color.BLUE, "R"));
        planeMap.put("U", new Plane("U", Color.YELLOW, "D"));
        planeMap.put("D", new Plane("D", Color.WHITE, "U"));
    }

    public void lateral(String permutation, String rule) {
        Node stems[][] = new Node[4][3];
        //Handles F, F', B, B'
        if (permutation.charAt(0) == 'F') {
            stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
            stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 0, 1);
            stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 0, 0);
            stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 2, 1);

            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 1);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 2, 1);
        } else if (permutation.charAt(0) == 'B') {
            stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 2, 0);
            stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 1);
            stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
            stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 1);

            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 2, 1);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 1);
        }
    }

    public void vertical(String permutation, String rule) {
        Node stems[][] = new Node[4][3];
        //Handles U, U', D, D'
        if (permutation.charAt(0) == 'U') {
            stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 0, 0);
            stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 0);
            stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(0, 2, 0);
            stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 0);

            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 0);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 0);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 0, 0, 0);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 0);
        } else if (permutation.charAt(0) == 'D') {
            stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
            stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 2, 0);
            stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
            stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 0, 0);

            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 0, 0, 0);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 0, 0, 0);
        }
    }

    public void horizontal(String permutation, String rule) {
        Node stems[][] = new Node[4][3];
        //Handles L, L', R, R'
        if (permutation.charAt(0) == 'L') {
            for (int i = 0; i < 4; i++) {
                stems[i] = planeMap.get(rule.charAt(i + 1) + "").cloneStem(2, 0, 1);
            }
            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 1);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 1);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 1);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 1);
        } else if (permutation.charAt(0) == 'R') {
            for (int i = 0; i < 4; i++) {
                stems[i] = planeMap.get(rule.charAt(i + 1) + "").cloneStem(2, 2, 1);
            }
            planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 2, 1);
            planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 2, 1);
            planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 2, 1);
            planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 2, 1);
        }

    }

    public void permutate(String permutation) {
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Plane> pair = it.next();
            for (int i = 0; i < rules.length; i++) {
                if (permutation.charAt(0) == rules[i].charAt(0)) {
                    planeMap.get(permutation.charAt(0) + "").rotatePlane(1);
                    if (permutation.charAt(0) == 'F'
                        || permutation.charAt(0) == 'B'){
                        lateral(permutation, rules[i]);
                    } else if (permutation.charAt(0) == 'U'
                            || permutation.charAt(0) == 'D') {
                        vertical(permutation, rules[i]);
                    } else if (permutation.charAt(0) == 'L'
                            || permutation.charAt(0) == 'R') {
                        horizontal(permutation, rules[i]);
                    }
                    break ;
                }
            }
            break ;
        }
    }

    public void printState() {
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Plane> pair = it.next();
            pair.getValue().printPlane();
            System.out.println("\n");
        }
    }

    public HashMap<String, Plane> getPlaneMap() {
        return planeMap;
    }
}
