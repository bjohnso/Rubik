package com.rubix.artifacts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class State {

    private static Plane front = new Plane("F", "red");
    private static Plane back = new Plane("B", "orange");
    private static Plane up = new Plane("U", "yellow");
    private static Plane down = new Plane("D", "white");
    private static Plane left = new Plane("L", "blue");
    private static Plane right = new Plane("R", "green");

    private static HashMap<String, Plane> planeMap = new HashMap<String, Plane>();
    private HashMap<Integer, Node[]> nodeLayerMap = new HashMap<Integer, Node[]>();

    public State() {
        populateNodes();
    }

    private void populateNodes() {
        //Grow Node Map Trees
        for (int i = 0; i < 3; i++){
            if (i == 0) {
                Node nodeArray[] = {
                        new Node(front, down, null),
                        new Node(front, down, right),
                        new Node(null, down, right),
                        new Node(back, down, right),
                        new Node(back, down, null),
                        new Node(back, down, left),
                        new Node(null, down, left),
                        new Node(front, down, left)
                };
                nodeLayerMap.put(i, nodeArray);
            } else if (i == 1){
                Node nodeArray[] = {
                        new Node(front, null, null),
                        new Node(front, null, right),
                        new Node(null, null, right),
                        new Node(back, null, right),
                        new Node(back, null, null),
                        new Node(back, null, left),
                        new Node(null, null, left),
                        new Node(front, null, left)
                };
                nodeLayerMap.put(i, nodeArray);
            } else if (i == 2){
                Node nodeArray[] = {
                        new Node(front, up, null),
                        new Node(front, up, right),
                        new Node(null, up, right),
                        new Node(back, up, right),
                        new Node(back, up, null),
                        new Node(back, up, left),
                        new Node(null, up, left),
                        new Node(front, up, left)
                };
                nodeLayerMap.put(i, nodeArray);
            }
        }
    }



}
