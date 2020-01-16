package com.rubix.artifacts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class State {

    HashMap<String, Plane> planeMap = new HashMap<String, Plane>();

    public State() {
        initPlaneMap();
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
        /*String rule = "FLOR";
        int rule_index = 0;
        for (int i = 0; i < rule.length(); i++){
            if (rule.charAt(i) == permutation.charAt(0)){
                rule_index = i;
            }
        }

        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Plane> pair = it.next();
            if (!pair.getValue().getPolar().equalsIgnoreCase(permutation.charAt(0) + "")) {

            }
        }*/
        planeMap.get(permutation).permutate(planeMap.get("R"), planeMap.get("U"), planeMap.get("L"), planeMap.get("D"), 1);
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Plane> pair = it.next();
            pair.getValue().printPlane();
            System.out.println("\n");
        }
    }
}
