package com.rubix.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;

public class State {

    private String rules[];
    private String scramble;
    private String solve;
    private HashMap<String, Cubicle> cube = new HashMap<>();
    private HashMap<String, String[]> cubicleRotations = new HashMap<>();
    private HashMap<String, String[]> faceRotations = new HashMap<>();

    public State() {
        initCubicleRotations();
        initCube();
    }

    private void initCubicleRotations() {
        cubicleRotations.put("F", ROTATION_NODES_F);
        cubicleRotations.put("B", ROTATION_NODES_B);
        cubicleRotations.put("U", ROTATION_NODES_U);
        cubicleRotations.put("D", ROTATION_NODES_D);
        cubicleRotations.put("L", ROTATION_NODES_L);
        cubicleRotations.put("R", ROTATION_NODES_R);
    }

    private void initFaceRotations() {
        faceRotations.put("F", ROTATION_FACES_F);
        faceRotations.put("B", ROTATION_FACES_B);
        faceRotations.put("U", ROTATION_FACES_U);
        faceRotations.put("D", ROTATION_FACES_D);
        faceRotations.put("L", ROTATION_FACES_L);
        faceRotations.put("R", ROTATION_FACES_R);
    }

    private void initCube() {
        //LAYER 1
        cube.put("111", new Cubicle("111"));
        cube.put("112", new Cubicle("112"));
        cube.put("113", new Cubicle("113"));
        cube.put("121", new Cubicle("121"));
        cube.put("122", new Cubicle("122"));
        cube.put("123", new Cubicle("123"));
        cube.put("131", new Cubicle("131"));
        cube.put("132", new Cubicle("132"));
        cube.put("133", new Cubicle("133"));

        //LAYER 2
        cube.put("211", new Cubicle("211"));
        cube.put("212", new Cubicle("212"));
        cube.put("213", new Cubicle("213"));
        cube.put("221", new Cubicle("221"));
        cube.put("222", new Cubicle("222"));
        cube.put("223", new Cubicle("223"));
        cube.put("231", new Cubicle("231"));
        cube.put("232", new Cubicle("232"));
        cube.put("233", new Cubicle("233"));

        //LAYER 3
        cube.put("311", new Cubicle("311"));
        cube.put("312", new Cubicle("312"));
        cube.put("313", new Cubicle("313"));
        cube.put("321", new Cubicle("321"));
        cube.put("322", new Cubicle("322"));
        cube.put("323", new Cubicle("323"));
        cube.put("331", new Cubicle("331"));
        cube.put("332", new Cubicle("332"));
        cube.put("333", new Cubicle("333"));
    }

    public void rotate(String rotation, int direction) {
        System.out.println(rotation);
        String rule[] = cubicleRotations.get(rotation);
        Node3D clone1;
        Node3D clone2;
        if (direction > 0){
            clone1 = cube.get(rule[0]).getNode3D();
            clone2 = cube.get(rule[1]).getNode3D();
            for (int i = 0; i < rule.length; i++){
                if (i == 0)
                    cube.get(rule[i]).setNode3D(cube.get(rule[rule.length - 2]).getNode3D());
                else if (i == 1)
                    cube.get(rule[i]).setNode3D(cube.get(rule[rule.length - 1]).getNode3D());
                else if (i == 2)
                    cube.get(rule[i]).setNode3D(clone1);
                else if (i == 3)
                    cube.get(rule[i]).setNode3D(clone2);
                else
                    cube.get(rule[i]).setNode3D(cube.get(rule[i - 2]).getNode3D());
            }
        } /*else {
            for (int i = rule.length - 1; i >= 0; i--) {
                if (i == rule.length - 1)
                    cube.get(rule[i]).setNode3D(clone.get(rule[1]).getNode3D());
                else if (i == rule.length - 2)
                    cube.get(rule[i]).setNode3D(clone.get(rule[0]).getNode3D());
                else
                    cube.get(rule[i]).setNode3D(clone.get(rule[i + 2]).getNode3D());
            }
        }*/
        //updateNodes(rotation, direction);
    }

    public void updateNodes(String rotation, int direction) {
        HashMap<String, Cubicle> clone = cube;
        String rule[] = cubicleRotations.get(rotation);
        String rotate;
        if (direction > 0)
            rotate = rotation;
        else
            rotate = getMirror(rotation);

        for (String c : rule)
            cube.get(c).getNode3D().rotateFaces(rotate);
    }

    public HashMap<String, Cubicle> cloneCube() {
        HashMap<String, Cubicle> clone = new HashMap<>();
         Iterator<Map.Entry<String, Cubicle>> it = cube.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<String, Cubicle> pair = it.next();
            clone.put(pair.get)
        }
    }

    public void printNodes() {
        System.out.println(cube.get("111").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("211").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("311").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("312").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("313").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("213").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("113").getNode3D().getCurrentCubicle());
        System.out.println(cube.get("112").getNode3D().getCurrentCubicle());
    }
}
