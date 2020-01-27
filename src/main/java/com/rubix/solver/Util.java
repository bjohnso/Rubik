package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;
import static com.rubix.runes.Runes.ROTATION_FACES_R;

public class Util {

    protected static HashMap<String, String[]> planeMap = new HashMap<>();
    protected static HashMap<String, String[]> rotationNodeMap = new HashMap<>();
    protected static HashMap<String, String[]> rotationFaceMap = new HashMap<>();

    protected static void initSolve() {
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

    protected static Cubicle findNode(State state, String plane[], String type, Color color) {
        for (int i = 0; i < plane.length; i++) {
            Cubicle cubicle = state.getCube().get(plane[i]);
            if (cubicle.getType().equalsIgnoreCase(type)) {
                if (cubicle.getNode3D().getColor(color) != null)
                    return cubicle;
            }
        }
        return null;
    }

    protected static Cubicle findNode(State state, String plane[], String id) {
        for (int i = 0; i < plane.length; i++) {
            Cubicle cubicle = state.getCube().get(plane[i]);
            if (cubicle.getNode3D().getHomeCubicle() == id)
                return cubicle;
        }
        return null;
    }

    protected static ArrayList<String> computePermutations(State state, Cubicle source, Cubicle target, String forbid) {
        System.out.println("COMPUTING TOTAL PERMUTATONS FOR " + source.getPosition() + " TO " + target.getPosition() + " WITH FORBID PLANE : " + forbid);

        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> primaryPermutation = new ArrayList<>();
        ArrayList<String> auxiliaryPermutations = new ArrayList<>();
        ArrayList<String> testBias;
        ArrayList<String> enforceBias = new ArrayList<>();
        String sourceClone = source.getPosition();

        if (forbid.equalsIgnoreCase("NONE"))
            primaryPermutation = computePrimaryPermutation(source, target, "NONE");

        if (primaryPermutation.isEmpty()){
            ArrayList<String> targetFaces = formatNodeFaces(target);
            for (String s : targetFaces){
                if (!s.equalsIgnoreCase(forbid)) {
                    //ENFORCING CLOCKWISE BIAS
                    System.out.println("TESTING BIASES...");
                    testBias = computePrimaryPermutation(source, target, "NONE");
                    if (testBias.isEmpty()){
                        System.out.println("ENFORCING BIAS WITH ROTATION : " + forbid + '\'');
                        enforceBias.add(forbid + '\'');
                        sourceClone = computeTarget(source, enforceBias);
                    } else {
                        for (String t : testBias){
                            if (t.equalsIgnoreCase(forbid + '\'')){
                                System.out.println("ENFORCING BIAS WITH ROTATION : " + forbid + '2');
                                enforceBias.add(forbid);
                                enforceBias.add(forbid);
                                sourceClone = computeTarget(source, enforceBias);
                                break ;
                            }
                        }
                    }
                    auxiliaryPermutations = computeAuxiliaryPermutation(state.getCube().get(sourceClone), s, forbid);
                    for (String t : auxiliaryPermutations){
                        System.out.println("TEST : "  + t);
                    }
                    String newSource = computeTarget(state.getCube().get(sourceClone), auxiliaryPermutations);
                    Cubicle clone = state.getCube().get(newSource);
                    ArrayList<String> finalPermutations = new ArrayList<>();
                    if (!newSource.equalsIgnoreCase(target.getPosition()))
                        finalPermutations = computePrimaryPermutation(clone, target, "NONE");
                    for (String f : finalPermutations)
                        auxiliaryPermutations.add(f);
                    break ;
                }
            }
        }
        for (String s : enforceBias)
            permutations.add(s);
        for (String s : auxiliaryPermutations)
            permutations.add(s);
        for (String s : primaryPermutation)
            permutations.add(s);
        return permutations;
    }

    protected static ArrayList<String> computePrimaryPermutation(Cubicle source, Cubicle target, String plane) {
        System.out.println("COMPUTING PRIME FOR " + source.getPosition() + " TO " + target.getPosition());
        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> relationships = computeNodeRelations(source, target);
        String rule[] = null;
        int sourcePos = -1;
        int targetPos = -1;

        if (plane.equalsIgnoreCase("NONE")){
            for (String s: relationships) {
                rule = rotationNodeMap.get(s);
                plane = s;
                break ;
            }
        } else {
            rule = rotationNodeMap.get(plane);
        }

        for (int i = 0; i < rule.length - 1; i++){
            if (source.getPosition().equalsIgnoreCase(rule[i]))
                sourcePos = i;
            if (target.getPosition().equalsIgnoreCase(rule[i]))
                targetPos = i;
        }
        int calc = targetPos - sourcePos;
        if (calc == -2 || calc == 6)
            permutations.add(plane + '\'');
        else if (calc == 4 || calc == -4) {
            permutations.add(plane);
            permutations.add(plane);
        }
        else if (calc == 0)
            return permutations;
        else
            permutations.add(plane);

        return permutations;
    }

    protected static ArrayList<String> computeAuxiliaryPermutation(Cubicle source, String targetPlane, String sourcePlane) {
        System.out.println("COMPUTING AUX FOR " + source.getPosition() + " TO " + targetPlane + " PLANE");
        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> sourceFaces = formatNodeFaces(source);

        Iterator<Map.Entry<String, String[]>> it = rotationFaceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> pair = it.next();
            String rule[] = pair.getValue();
            if (!pair.getKey().equalsIgnoreCase(sourcePlane) &&
                    (source.getNode3D().getFace(pair.getKey()) != null || source.getNode3D().getFace(getMirror(pair.getKey())) != null)) {
                for (int i = 0; i < rule.length - 1; i++){
                    if (rule[i].equalsIgnoreCase(sourcePlane) && rule[i + 1].equalsIgnoreCase(targetPlane)){
                        System.out.println("FOUND SIMPLE AUX");
                        //Check if Rotation is mirror
                        if (source.getNode3D().getFace(pair.getKey()) != null)
                            permutations.add(pair.getKey());
                        else
                            permutations.add(getMirror(pair.getKey()) + '\'');
                        break ;
                    }
                }
            }
            if (!permutations.isEmpty())
                break ;
        }

        if (permutations.isEmpty()){
            System.out.println("FAILED TO GENERATE AUX PATTERN... ATTEMPTING AUX ALGO");
            for (String s : sourceFaces){
                if (!s.equalsIgnoreCase(sourcePlane)){
                    permutations.add(s + '\'');
                    for (int i = 0; i < rotationFaceMap.get(s).length - 1; i++){
                        if (rotationFaceMap.get(s)[i].equalsIgnoreCase(sourcePlane)){
                            permutations.add(getMirror(rotationFaceMap.get(s)[i + 1]) + '\'');
                            break;
                        }
                    }
                }
            }
            permutations.add(sourcePlane);
        }
        return permutations;
    }

    protected static String computeTarget(Cubicle source, ArrayList<String> permutations){
        String id = source.getPosition();
        for (String s : permutations){
            String rule[] = rotationNodeMap.get(s.charAt(0) + "");
            for (int i = 0; i < rule.length; i++){
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
                    break ;
                }
            }
            System.out.println("TESTING TARGET COMPUTATION FOR PERMUTATION : " + s.charAt(0) + " WITH ID : " + id);
        }
        return id;
    }

    protected static ArrayList<String> computeNodeRelations(Cubicle source, Cubicle target) {
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

    protected static ArrayList<String> formatNodeFaces(Cubicle cubicle) {
        ArrayList<String> faces = new ArrayList<>();
        Iterator<Map.Entry<String, Color>> it = cubicle.getNode3D().getFaceMap().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Color> pair = it.next();
            faces.add(pair.getKey());
        }
        return faces;
    }
}
