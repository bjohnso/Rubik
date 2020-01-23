package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.rubix.runes.Runes.*;

public class Node3D {

    String currentCubicle;
    String homeCubicle;
    HashMap<String, Color> faceMap = new HashMap<>();

    public Node3D(String homeCubicle) {
        this.homeCubicle = homeCubicle;
    }

    public void rotateFaces(String rotate) {
        HashMap<String, Color> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Color>> it = faceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Color> pair = it.next();
            String face = pair.getKey();
            boolean replaced = false;
            switch (rotate){
                case "F":
                    for (int i = 0; i < ROTATION_FACES_F.length; i++) {
                        if (ROTATION_FACES_F[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_F[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "B":
                    for (int i = 0; i < ROTATION_FACES_B.length; i++) {
                        if (ROTATION_FACES_B[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_B[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "U":
                    for (int i = 0; i < ROTATION_FACES_U.length; i++) {
                        if (ROTATION_FACES_U[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_U[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "D":
                    for (int i = 0; i < ROTATION_FACES_D.length; i++) {
                        if (ROTATION_FACES_D[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_D[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "L":
                    for (int i = 0; i < ROTATION_FACES_L.length; i++) {
                        if (ROTATION_FACES_L[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_L[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "R":
                    for (int i = 0; i < ROTATION_FACES_R.length; i++) {
                        if (ROTATION_FACES_R[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_R[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
            }
            if (!replaced)
                newMap.put(pair.getKey(), pair.getValue());
        }
        faceMap.clear();
        faceMap = newMap;
        newMap.clear();
    }

    public void addFace(String face, Color color) {
        if (faceMap.get(face) == null){
            faceMap.put(face, color);
        }
    }

    public Color getFace(String face){
        return faceMap.get(face);
    }

    public String getCurrentCubicle() {
        return currentCubicle;
    }

    public String getHomeCubicle() {
        return homeCubicle;
    }

    public void setCurrentCubicle(String currentCubicle) {
        this.currentCubicle = currentCubicle;
    }

    public void setHomeCubicle(String homeCubicle) {
        this.homeCubicle = homeCubicle;
    }

    public boolean isSolved() {
        return currentCubicle == homeCubicle ? true : false;
    }
}
