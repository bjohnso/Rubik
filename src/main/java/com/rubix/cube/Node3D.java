package com.rubix.cube;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;

public class Node3D {

    private String currentCubicle;
    private String homeCubicle;
    private String type;
    private HashMap<String, Color> faceMap;
    private HashMap<String, Color> defaultFaceMap = new HashMap<>();

    public Node3D(String homeCubicle, String type) {
        this.homeCubicle = homeCubicle;
        this.type = type;
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
                    for (int i = 0; i < ROTATION_FACES_F.length - 1; i++) {
                        if (ROTATION_FACES_F[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_F[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "B":
                    for (int i = 0; i < ROTATION_FACES_B.length - 1; i++) {
                        if (ROTATION_FACES_B[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_B[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "U":
                    for (int i = 0; i < ROTATION_FACES_U.length - 1; i++) {
                        if (ROTATION_FACES_U[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_U[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "D":
                    for (int i = 0; i < ROTATION_FACES_D.length - 1; i++) {
                        if (ROTATION_FACES_D[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_D[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "L":
                    for (int i = 0; i < ROTATION_FACES_L.length - 1; i++) {
                        if (ROTATION_FACES_L[i].equalsIgnoreCase(face)) {
                            newMap.put(ROTATION_FACES_L[i + 1], pair.getValue());
                            replaced = true;
                            break;
                        }
                    }
                    break ;
                case "R":
                    for (int i = 0; i < ROTATION_FACES_R.length - 1; i++) {
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
    }

    public void addFace(String face, Color color) {
        if (faceMap.get(face) == null){
            faceMap.put(face, color);
        }
    }

    public void addDefaultFace(String face, Color color) {
        if (defaultFaceMap.get(face) == null){
            defaultFaceMap.put(face, color);
        }
    }

    public String getColor(Color color) {
        Iterator<Map.Entry<String, Color>> it = faceMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Color> pair = it.next();
            if (pair.getValue() == color)
                return pair.getKey();
        }
        return null;
    }

    public Node3D cloneNode3D() {
        Node3D node3D = new Node3D(this.homeCubicle, this.type);
        node3D.setCurrentCubicle(this.currentCubicle);
        node3D.setDefaultFaceMap(this.cloneDefaultFaceMap());
        node3D.setFaceMap(this.cloneFaceMap());
        return node3D;
    }

    public HashMap<String, Color> cloneFaceMap() {
        HashMap<String, Color> clone  = new HashMap<>();

        Iterator<Map.Entry<String, Color>> it  = faceMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Color> pair = it.next();
            clone.put(pair.getKey(), pair.getValue());
        }
        return clone;
    }

    public HashMap<String, Color> cloneDefaultFaceMap() {
        HashMap<String, Color> clone = new HashMap<>();
        Iterator<Map.Entry<String, Color>> it = defaultFaceMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Color> pair = it.next();
            clone.put(pair.getKey(), pair.getValue());
        }
        return clone;
    }

    public boolean isTwisted() {
        Iterator<Map.Entry<String, Color>> it = faceMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Color> pair = it.next();
            if (defaultFaceMap.get(pair.getKey()) != pair.getValue())
                return true;
        }
        return false;
    }

    public void setFaceMap(HashMap<String, Color> faceMap) {
        this.faceMap = faceMap;
    }

    public void setDefaultFaceMap(HashMap<String, Color> defaultFaceMap) {
        this.defaultFaceMap = defaultFaceMap;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Color> getFaceMap() {
        return faceMap;
    }

    public void setHomeCubicle(String homeCubicle) {
        this.homeCubicle = homeCubicle;
    }

    public boolean isSolved() {
        return currentCubicle == homeCubicle ? true : false;
    }

}
