package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class State {

    private String rules[];
    private String scramble;
    private String solve;
    private HashMap<String, Plane> planeMap = new HashMap<String, Plane>();

    public State() {
        scramble = "";
        solve = "";
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

    public Node[][] cloneRotation(String rotation) {
        String rule = getRule(rotation.charAt(0));
        Node stems[][] = new Node[4][3];
        if (rotation.length() == 1 || (rotation.length() > 1 && rotation.charAt(1) == '2')) {
            System.out.println(rotation);
            if (rotation.charAt(0) == 'F') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 0, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 0, 0);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 2, 1);
            } else if (rotation.charAt(0) == 'B') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 2, 0);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 1);
            } else if (rotation.charAt(0) == 'U') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 0, 0);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 0);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(0, 2, 0);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 0);
            } else if (rotation.charAt(0) == 'D') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 2, 0);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 0, 0);
            } else if (rotation.charAt(0) == 'L') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 0, 1);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 0, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 0, 1);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 1);
            } else if (rotation.charAt(0) == 'R') {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 2, 1);
                stems[1] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 1);
                stems[3] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 2, 1);
            }
        } else if (rotation.length() > 1 && rotation.charAt(1) == '\''){
            if (rotation.equalsIgnoreCase("F'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 2, 0);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 2, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 0, 1);
            } else if (rotation.equalsIgnoreCase("B'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 0, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 0, 0);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 2, 1);
            } else if (rotation.equalsIgnoreCase("U'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 0, 0);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 2, 0);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(0, 2, 0);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 0, 0);
            } else if (rotation.equalsIgnoreCase("D'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(0, 0, 0);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(0, 2, 0);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 0);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(0, 0, 0);
            } else if (rotation.equalsIgnoreCase("L'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 0, 1);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 0, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 0, 1);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 0, 1);
            } else if (rotation.equalsIgnoreCase("R'")) {
                stems[0] = planeMap.get(rule.charAt(1) + "").cloneStem(2, 2, 1);
                stems[1] = planeMap.get(rule.charAt(4) + "").cloneStem(2, 2, 1);
                stems[2] = planeMap.get(rule.charAt(3) + "").cloneStem(2, 2, 1);
                stems[3] = planeMap.get(rule.charAt(2) + "").cloneStem(2, 2, 1);
            }
        }
        return stems;
    }

    public void insertRotation(String rotation, Node[][] stems) {
        String rule = getRule(rotation.charAt(0));
        if (rotation.length() == 1 || (rotation.length() > 1 && rotation.charAt(1) == '2')) {
            if (rotation.charAt(0) == 'F') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 2, 1);
            } else if (rotation.charAt(0) == 'B') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 2, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 1);
            } else if (rotation.charAt(0) == 'U') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 0);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 0, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 0);
            } else if (rotation.charAt(0) == 'D') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 0, 0, 0);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 0, 0, 0);
            } else if (rotation.charAt(0) == 'L') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 1);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 0, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 1);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 0, 1);
            } else if (rotation.charAt(0) == 'R') {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 2, 1);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[0], 2, 2, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 2, 1);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[2], 2, 2, 1);
            }
        } else if (rotation.length() > 1 && rotation.charAt(1) == '\''){
            if (rotation.equalsIgnoreCase("F'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 2, 2, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 2, 0, 1);
            } else if (rotation.equalsIgnoreCase("B'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 2, 0, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 2, 2, 1);
            } else if (rotation.equalsIgnoreCase("U'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 2, 0, 0);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 0, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 2, 0, 0);
            } else if (rotation.equalsIgnoreCase("D'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 0, 0, 0);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 0, 0, 0);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 0);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 0, 0, 0);
            } else if (rotation.equalsIgnoreCase("L'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 0, 1);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 2, 0, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 0, 1);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 2, 0, 1);
            } else if (rotation.equalsIgnoreCase("R'")) {
                planeMap.get(rule.charAt(1) + "").insertStem(stems[3], 2, 2, 1);
                planeMap.get(rule.charAt(4) + "").insertStem(stems[0], 2, 2, 1);
                planeMap.get(rule.charAt(3) + "").insertStem(stems[1], 2, 2, 1);
                planeMap.get(rule.charAt(2) + "").insertStem(stems[2], 2, 2, 1);
            }
        }
    }

    public void permutate(String permutation) {
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Plane> pair = it.next();
            for (int i = 0; i < rules.length; i++) {
                if (permutation.charAt(0) == rules[i].charAt(0)) {
                    if (permutation.length() > 1 && permutation.charAt(1) == '\'')
                        planeMap.get(permutation.charAt(0) + "").rotatePlane(-1);
                    else
                        planeMap.get(permutation.charAt(0) + "").rotatePlane(1);

                    insertRotation(permutation, cloneRotation(permutation));

                    if (permutation.length() > 1 && permutation.charAt(1) == '2') {
                        planeMap.get(permutation.charAt(0) + "").rotatePlane(1);
                        insertRotation(permutation, cloneRotation(permutation));
                    }
                    break ;
                }
            }
            break ;
        }
    }

    public String getRule(char rotation){
        String rule = "";

        for (int i = 0; i < rules.length; i++){
            if (rotation == rules[i].charAt(0)) {
                rule = rules[i];
                break ;
            }
        }
        return rule;
    }

    public String[] getRules() {
        return rules;
    }

    public void scrambleAdd(String rotation) {
        scramble += rotation;
    }

    public void solveAdd(String solve) {
        this.solve += solve;
    }

    public String getScramble() {
        return scramble;
    }

    public String getSolve() {
        return solve;
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
