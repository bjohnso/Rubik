package com.rubix.artifacts;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.rubix.runes.Runes.*;

public class Solver {

    HashMap<String, String[]> planeMap = new HashMap<>();
    HashMap<String, String[]> rotationMap = new HashMap<>();

    public Solver(){
        planeMap.put("F", PLANE_F);
        planeMap.put("B", PLANE_B);
        planeMap.put("U", PLANE_U);
        planeMap.put("D", PLANE_D);
        planeMap.put("L", PLANE_L);
        planeMap.put("R", PLANE_R);

        rotationMap.put("F", ROTATION_NODES_F);
        rotationMap.put("B", ROTATION_NODES_B);
        rotationMap.put("U", ROTATION_NODES_U);
        rotationMap.put("D", ROTATION_NODES_D);
        rotationMap.put("L", ROTATION_NODES_L);
        rotationMap.put("R", ROTATION_NODES_R);
    }

    public State solveDaisy(State state) {

        for (int i = 0; i < PLANE_U.length - 1; i++){
            Cubicle target = state.getCube().get(PLANE_U[i]);
            Cubicle source = null;

            if (target.getType().equalsIgnoreCase("E")){
                if (target.getNode3D().getFace("U") != Color.WHITE){
                    for (int j = 0; j < ROTATION_FACES_U.length - 1; j++){
                        if ((source = findNode(state, planeMap.get(ROTATION_FACES_U[i]),"E", Color.WHITE)) != null)
                            break ;
                    }
                }
            }

            if (source != null) {

            }
        }

        return state;
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

    public ArrayList<String> computePermutations(Cubicle source, Cubicle target, String forbid) {

        ArrayList<String> permuations = new ArrayList<>();
        ArrayList<String> relationships = computeNodeRelationships(source, target);
        String rule[] = null;
        int sourcePos = -1;
        int targetPos = -1;

        for (String s: relationships){
            if (!s.equalsIgnoreCase(forbid)){
                rule = rotationMap.get(s);
                for (int i = 0; i < rule.length - 1; i++){
                    if (source.getPosition().equalsIgnoreCase(rule[i]))
                        sourcePos = i;
                    if (target.getPosition().equalsIgnoreCase(rule[i]))
                        targetPos = i;
                }
                int calc = targetPos - sourcePos;
                if (calc == -2 || calc == 6)
                    permuations.add(s + '\'');
                else if (calc == 4 || calc == - 4) {
                    permuations.add(s);
                    permuations.add(s);
                }
                else
                    permuations.add(s);
                break ;
            }
        }

        if (permuations.isEmpty()){
                
        }

        return permuations;
    }

    public ArrayList<String> computeNodeRelationships(Cubicle source, Cubicle target) {
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
}
