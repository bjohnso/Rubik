package com.rubix.artifacts;

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
            "FRULD",
            "RBUFD",
            "BLURD",
            "LFUBD",
            "ULBRF",
            "DLFRB"
        };
        rules = temp;
    }

    private void initPlaneMap() {
        planeMap.put("F", new Plane("F", "red", "B"));
        planeMap.put("R", new Plane("R", "green", "L"));
        planeMap.put("B", new Plane("B", "orange", "F"));
        planeMap.put("L", new Plane("L", "blue", "R"));
        planeMap.put("U", new Plane("U", "yellow", "D"));
        planeMap.put("D", new Plane("D", "white", "U"));
    }

    public void permutate(String permutation) {
        for (int i = 0; i < rules.length; i++){
            if (permutation.charAt(0) == rules[i].charAt(0)){
                planeMap.get(permutation).permutate(planeMap.get(rules[i].charAt(1) + ""),
                        planeMap.get(rules[i].charAt(2) + ""),
                        planeMap.get(rules[i].charAt(3) + ""),
                        planeMap.get(rules[i].charAt(4) + ""),
                        1);
                break ;
            }
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
}
