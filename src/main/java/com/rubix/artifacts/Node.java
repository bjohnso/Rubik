package com.rubix.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node {

    //FTR
    private String alias;
    private HashMap<String, Plane> planeMap;

    public Node(Plane p1, Plane p2, Plane p3) {
        if (p1 != null)
            planeMap.put(p1.getAlias(), p1);
        if (p2 != null)
            planeMap.put(p2.getAlias(), p2);
        if (p3 != null)
            planeMap.put(p3.getAlias(), p3);
    }

    public Node generateChild(String direction){
        if (direction.equalsIgnoreCase("B")){
            return new Node();
        } else if (direction.equalsIgnoreCase(""){

        } else (direction.equalsIgnoreCase("U")) {

        }
    }

    public void cast() {
        Iterator<Map.Entry<String, Plane>> it = planeMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Plane> pair = it.next();
            alias += pair.getKey();
        }
    }

    public void addPlane(String planeAlias, Plane plane) {
        planeMap.put(planeAlias, plane);
    }

    public HashMap<String, Plane> getPlaneMap() {
        return planeMap;
    }

    public String getAlias() {
        return alias;
    }

}
