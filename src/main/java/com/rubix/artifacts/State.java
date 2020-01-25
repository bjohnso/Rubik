package com.rubix.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;

public class State {

    private String rules[];
    private ArrayList<String> scramble = new ArrayList<>();
    private ArrayList<String> solve = new ArrayList<>();
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
        cube.put("111", new Cubicle("111", "C"));
        cube.put("112", new Cubicle("112", "E"));
        cube.put("113", new Cubicle("113", "C"));
        cube.put("121", new Cubicle("121", "E"));
        cube.put("122", new Cubicle("122", "M"));
        cube.put("123", new Cubicle("123", "E"));
        cube.put("131", new Cubicle("131", "C"));
        cube.put("132", new Cubicle("132", "E"));
        cube.put("133", new Cubicle("133", "C"));

        //LAYER 2
        cube.put("211", new Cubicle("211", "E"));
        cube.put("212", new Cubicle("212", "M"));
        cube.put("213", new Cubicle("213", "E"));
        cube.put("221", new Cubicle("221", "M"));
        cube.put("222", new Cubicle("222", "M"));
        cube.put("223", new Cubicle("223", "M"));
        cube.put("231", new Cubicle("231", "E"));
        cube.put("232", new Cubicle("232", "M"));
        cube.put("233", new Cubicle("233", "E"));

        //LAYER 3
        cube.put("311", new Cubicle("311", "C"));
        cube.put("312", new Cubicle("312", "E"));
        cube.put("313", new Cubicle("313", "C"));
        cube.put("321", new Cubicle("321", "E"));
        cube.put("322", new Cubicle("322", "M"));
        cube.put("323", new Cubicle("323", "E"));
        cube.put("331", new Cubicle("331", "C"));
        cube.put("332", new Cubicle("332", "E"));
        cube.put("333", new Cubicle("333", "C"));
    }

    public void rotate(String rotation, int direction) {
        if (direction > 0)
            System.out.println("PERM : " + rotation);
        else if (direction < 0)
            System.out.println("PERM : " + rotation + '\'');
        String rule[] = cubicleRotations.get(rotation);
        HashMap<String, Cubicle> clone = cloneCube();
        if (direction > 0){
            for (int i = 0; i < rule.length; i++){
                if (i == 0)
                    cube.get(rule[i]).setNode3D(clone.get(rule[rule.length - 2]).getNode3D());
                else if (i == 1)
                    cube.get(rule[i]).setNode3D(clone.get(rule[rule.length - 1]).getNode3D());
                else
                    cube.get(rule[i]).setNode3D(clone.get(rule[i - 2]).getNode3D());
            }
        } else {
            for (int i = rule.length - 1; i >= 0; i--) {
                if (i == rule.length - 1)
                    cube.get(rule[i]).setNode3D(clone.get(rule[1]).getNode3D());
                else if (i == rule.length - 2)
                    cube.get(rule[i]).setNode3D(clone.get(rule[0]).getNode3D());
                else
                    cube.get(rule[i]).setNode3D(clone.get(rule[i + 2]).getNode3D());
            }
        }
        updateNodes(rotation, direction);
        if (direction > 0)
            addScramble(rotation);
        else
            addScramble(rotation + '\'');
    }

    public void updateNodes(String rotation, int direction) {
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
            clone.put(pair.getKey(), pair.getValue().clone());
        }
        return clone;
    }

    public HashMap<String, Cubicle> getCube() {
        return cube;
    }

    public void printNodes() {
        System.out.println(cube.get("111").getNode3D().getHomeCubicle());
        System.out.println(cube.get("211").getNode3D().getHomeCubicle());
        System.out.println(cube.get("311").getNode3D().getHomeCubicle());
        System.out.println(cube.get("312").getNode3D().getHomeCubicle());
        System.out.println(cube.get("313").getNode3D().getHomeCubicle());
        System.out.println(cube.get("213").getNode3D().getHomeCubicle());
        System.out.println(cube.get("113").getNode3D().getHomeCubicle());
        System.out.println(cube.get("112").getNode3D().getHomeCubicle());
    }

    public ArrayList<String> getScramble() {
        return scramble;
    }

    public ArrayList<String> getSolve() {
        return solve;
    }

    public void setScramble(ArrayList<String> scramble) {
        this.scramble = scramble;
    }

    public void setCube(HashMap<String, Cubicle> cube) {
        this.cube = cube;
    }

    public void setCubicleRotations(HashMap<String, String[]> cubicleRotations) {
        this.cubicleRotations = cubicleRotations;
    }

    public void setFaceRotations(HashMap<String, String[]> faceRotations) {
        this.faceRotations = faceRotations;
    }

    public void addSolve(String solve) {
        this.solve.add(solve);
    }

    public void setRules(String[] rules) {
        this.rules = rules;
    }

    public void addScramble(String scramble) {
        this.scramble.add(scramble);
    }

    public void setSolve(ArrayList<String> solve) {
        this.solve = solve;
    }

    public State cloneState() {
        State state = new State();
        state.setCube(this.cloneCube());
        return state;
    }
}
