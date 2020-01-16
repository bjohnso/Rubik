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
        planeMap.put("B", new Plane("B", "orange", "F"));
        planeMap.put("U", new Plane("U", "yellow", "D"));
        planeMap.put("D", new Plane("D", "white", "U"));
        planeMap.put("L", new Plane("L", "blue", "R"));
        planeMap.put("R", new Plane("R", "green", "L"));
    }

    public void permutate(String permutation) {
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Plane> pair = it.next();
            if (!pair.getValue().getPolar().equalsIgnoreCase(permutation.charAt(0) + "")) {

            }
        }
    }
}
