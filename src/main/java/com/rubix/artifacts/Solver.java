package com.rubix.artifacts;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;
import static com.rubix.runes.Runes.ROTATION_FACES_R;

public class Solver {

    HashMap<String, String[]> planeMap = new HashMap<>();
    HashMap<String, String[]> rotationNodeMap = new HashMap<>();
    HashMap<String, String[]> rotationFaceMap = new HashMap<>();

    public Solver(){
        //CONTAINS RULES DEFINING PLANES BY THEIR RESPECTIVE NODES
        planeMap.put("F", PLANE_F);
        planeMap.put("B", PLANE_B);
        planeMap.put("U", PLANE_U);
        planeMap.put("D", PLANE_D);
        planeMap.put("L", PLANE_L);
        planeMap.put("R", PLANE_R);

        //CONTAINS RULES DEFINING ROTATIONS BY ORDER OF NODES
        rotationNodeMap.put("F", ROTATION_NODES_F);
        rotationNodeMap.put("B", ROTATION_NODES_B);
        rotationNodeMap.put("U", ROTATION_NODES_U);
        rotationNodeMap.put("D", ROTATION_NODES_D);
        rotationNodeMap.put("L", ROTATION_NODES_L);
        rotationNodeMap.put("R", ROTATION_NODES_R);

        //CONTAINS RULES DEFINING ROTATIONS BY ORDER OF PLANES
        rotationFaceMap.put("F", ROTATION_FACES_F);
        rotationFaceMap.put("B", ROTATION_FACES_B);
        rotationFaceMap.put("U", ROTATION_FACES_U);
        rotationFaceMap.put("D", ROTATION_FACES_D);
        rotationFaceMap.put("L", ROTATION_FACES_L);
        rotationFaceMap.put("R", ROTATION_FACES_R);
    }

    public ArrayList<String> solveDaisy(State state) {

        String edges[] = {
                "312",
                "321",
                "332",
                "323"
        };

        State cubeStateClone = new State();
        //cubeStateClone.setCube(state.cloneCube());
        ArrayList<String> finalPermutations = new ArrayList<>();
        int backtrack = 0;

            //INSPECT TOP PLANE
            for (int i = 0; i < edges.length; i++) {
                ArrayList<String> tempPermutations = new ArrayList<>();
                Cubicle target = cubeStateClone.getCube().get(edges[i]);
                Cubicle source = null;

                System.out.println("TARGET : " + target.getPosition());

                //DETERMINE IF NODES OF TYPE EDGE HAVE A WHITE FACE DISPLAYED ON THE TOP PLANE
                if (target.getType().equalsIgnoreCase("E")) {
                    if (target.getNode3D().getFace("U") != Color.WHITE) {
                        //IF NOT SEARCH FOR A NODE OF TYPE EDGE WITH A WHITE FACE IN EACH SUCCESSIVE PLANE THAT INTERSECTS TOP
                        if ((source = findNode(cubeStateClone, planeMap.get(ROTATION_FACES_U[i]), "E", Color.WHITE)) != null) {
                            System.out.println("SOLUTION FOUND IN PLANE : " + ROTATION_FACES_U[i] + " at node : " + source.getPosition());
                            //DETERMINE SET OF PERMUTATIONS TO SOLVE FOR THE DESIRED STATE
                            if (source.getNode3D().getFace(ROTATION_FACES_U[i]) != Color.WHITE) {
                                tempPermutations = computePermutations(cubeStateClone, source, target, "NONE");
                            }
                            else
                                tempPermutations = computePermutations(cubeStateClone, source, target, ROTATION_FACES_U[i]);
                            i -= backtrack;
                            while (backtrack > 0) {
                                tempPermutations.add("U'");
                                backtrack--;
                            }
                        }
                        else {
                            tempPermutations.add("U");
                            backtrack++;
                        }
                    }
                }
                //ADD CALCULATED PERMUTATIONS TO FINAL ARRAY LIST AND PERFORM CALCULATED PERMUTATIONS ON CUBE CLONE
                for (String s : tempPermutations){
                    finalPermutations.add(s);
                    if (s.length() > 1 && s.charAt(1) == '\'')
                        cubeStateClone.rotate(s.charAt(0) + "", -1);
                    else
                        cubeStateClone.rotate(s, 1);
                }
            }
        return finalPermutations;
    }

    public Cubicle findNode(State state, String plane[], String type, Color color){
        for (int i = 0; i < plane.length - 1; i++){
            Cubicle cubicle = state.getCube().get(plane[i]);
            if (cubicle.getType().equalsIgnoreCase(type)){
                if (cubicle.getNode3D().getColor(color) != null){
                    return cubicle;
                }
            }
        }
        return null;
    }

    public ArrayList<String> computePermutations(State state, Cubicle source, Cubicle target, String forbid) {

        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> auxiliaryPermutations = new ArrayList<>();
        ArrayList<String> primaryPermutation = computePrimaryPermutation(source, target, forbid);

        if (primaryPermutation.isEmpty()){
            ArrayList<String> targetFaces = formatNodeFaces(target);
            for (String s : targetFaces){
                if (!s.equalsIgnoreCase(forbid)) {
                    auxiliaryPermutations = computeAuxiliaryPermutation(source, s, forbid);
                    Cubicle clone = state.getCube().get(computeTarget(source, auxiliaryPermutations));
                    primaryPermutation = computePrimaryPermutation(clone, target, "NONE");
                }
            }
        }
        for (String s : auxiliaryPermutations)
            permutations.add(s);
        for (String s : primaryPermutation)
            permutations.add(s);
        return permutations;
    }

    public ArrayList<String> computePrimaryPermutation(Cubicle source, Cubicle target, String forbid) {
        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> relationships = computeNodeRelations(source, target);
        String rule[] = null;
        int sourcePos = -1;
        int targetPos = -1;

        for (String s: relationships){
            if (!s.equalsIgnoreCase(forbid)){
                rule = rotationNodeMap.get(s);
                for (int i = 0; i < rule.length - 1; i++){
                    if (source.getPosition().equalsIgnoreCase(rule[i]))
                        sourcePos = i;
                    if (target.getPosition().equalsIgnoreCase(rule[i]))
                        targetPos = i;
                }
                int calc = targetPos - sourcePos;
                if (calc == -2 || calc == 6)
                    permutations.add(s + '\'');
                else if (calc == 4 || calc == -4) {
                    permutations.add(s);
                    permutations.add(s);
                }
                else
                    permutations.add(s);
                break ;
            }
        }
        return permutations;
    }

    public ArrayList<String> computeAuxiliaryPermutation(Cubicle cubicle, String target, String forbid) {
        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> sourceFaces = formatNodeFaces(cubicle);

        for (String s : sourceFaces){
            Iterator<Map.Entry<String, String[]>> it = rotationFaceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String[]> pair = it.next();
                if (!pair.getKey().equalsIgnoreCase(forbid)) {
                    for (int i = 0; i < pair.getValue().length - 2; i++) {
                        if (pair.getValue()[i].equalsIgnoreCase(s) && pair.getValue()[i + 1].equalsIgnoreCase(target)) {
                            if (cubicle.getNode3D().getFace(pair.getKey()) != null)
                                permutations.add(pair.getKey());
                            else
                                permutations.add(getMirror(pair.getKey()) + "\'");
                        }
                    }
                }
            }
        }
        return permutations;
    }

    public String computeTarget(Cubicle source, ArrayList<String> permutations){
        String id = source.getPosition();

        for (String s : permutations){
            String rule[] = rotationNodeMap.get(s.charAt(0) + "");
            for (int i = 0; i < rule.length - 1; i++){
                if (id.equalsIgnoreCase(rule[i])){
                    if (s.length() > 1 && s.charAt(1) == '\''){
                        if (i < 2)
                            id = rule[rule.length - 2 + i];
                        else
                            id = rule[i - 2];
                    } else {
                        if (i > rule.length - 3)
                            id = rule[i - rule.length - 2];
                        else
                            id = rule[i + 2];
                    }
                }
            }
        }
        return id;
    }

    public ArrayList<String> computeNodeRelations(Cubicle source, Cubicle target) {
        ArrayList<String> relationships = new ArrayList<>();
        Iterator<Map.Entry<String, Color>> sourceIT = source.getNode3D().getFaceMap().entrySet().iterator();

        while (sourceIT.hasNext()){
            Map.Entry<String, Color> sourcePair = sourceIT.next();
            Iterator<Map.Entry<String, Color>> targetIT = target.getNode3D().getFaceMap().entrySet().iterator();
            while (targetIT.hasNext()){
                Map.Entry<String, Color> targetPair = targetIT.next();
                if (targetPair.getKey().equalsIgnoreCase(sourcePair.getKey())){
                    relationships.add(targetPair.getKey());
                }
            }
        }
        return relationships;
    }

    public ArrayList<String> formatNodeFaces(Cubicle cubicle) {
        ArrayList<String> faces = new ArrayList<>();
        Iterator<Map.Entry<String, Color>> it = cubicle.getNode3D().getFaceMap().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Color> pair = it.next();
            faces.add(pair.getKey());
        }
        return faces;
    }
}
