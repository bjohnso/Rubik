package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rubix.solver.Util.*;

public class PLL {

    private static HashMap<String, String> PLLMap;

    private static void PLLInit() {
        PLLMap = new HashMap<>();
        PLLMap.put("FBR", "RRUFB'RRF'BURR");
        PLLMap.put("FBL", "LLU'F'BLLFB'U'LL");
        PLLMap.put("BFR", "RRU'B'FRRBF'U'RR");
        PLLMap.put("BFL", "LLUBF'LLB'FULL");
        PLLMap.put("LRF", "FFULR'FFL'RUFF");
        PLLMap.put("LRB", "BBU'L'RBBLR'U'BB");
        PLLMap.put("RLF", "FFU'LR'FFL'RU'FF");
        PLLMap.put("RLB", "BBUL'RBBLR'UBB");
    }

    private static String[] cornersTop = {
            "311",
            "331",
            "333",
            "313"
    };

    private static String[] edgesTop = {
            "312",
            "321",
            "332",
            "323"
    };

    public static ArrayList<String> solvePLLCorners(State state){

        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();

        ArrayList<Cubicle> unsolves = new ArrayList<>();
        int solveCount = 0;

        for (int i = 0; i < cornersTop.length; i++){
            Cubicle target = cubeStateClone.getCube().get(cornersTop[i]);
            if (target.getNode3D().isSolved())
                solveCount++;
            else
                unsolves.add(target);
            ArrayList<String> tempPermutations = new ArrayList<>();

            if (i == cornersTop.length - 1 && solveCount < 4){
                if (solveCount == 2){
                    ArrayList<String> relationship = computeNodeRelations(unsolves.get(0), unsolves.get(1));
                    if (!relationship.isEmpty() && relationship.size() > 1){
                        for (String s : relationship){
                            if (s.equalsIgnoreCase("L")){
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("L'");
                                    tempPermutations.add("U'");
                                    tempPermutations.add("L");
                                    tempPermutations.add("U");
                                }
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("F");
                                    tempPermutations.add("U");
                                    tempPermutations.add("F'");
                                    tempPermutations.add("U'");
                                }
                            } else if (s.equalsIgnoreCase("B")){
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("B");
                                    tempPermutations.add("U");
                                    tempPermutations.add("B'");
                                    tempPermutations.add("U'");
                                }
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("R'");
                                    tempPermutations.add("U'");
                                    tempPermutations.add("R");
                                    tempPermutations.add("U");
                                }
                            } else if (s.equalsIgnoreCase("R")){
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("R");
                                    tempPermutations.add("U");
                                    tempPermutations.add("R'");
                                    tempPermutations.add("U'");
                                }
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("F'");
                                    tempPermutations.add("U'");
                                    tempPermutations.add("F");
                                    tempPermutations.add("U");
                                }
                            } else if (s.equalsIgnoreCase("F")){
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("F");
                                    tempPermutations.add("U");
                                    tempPermutations.add("F'");
                                    tempPermutations.add("U'");
                                }
                                for(int j = 0; j < 3; j++){
                                    tempPermutations.add("L'");
                                    tempPermutations.add("U'");
                                    tempPermutations.add("L");
                                    tempPermutations.add("U");
                                }
                            }
                        }
                    } else {
                        for(int j = 0; j < 3; j++){
                            tempPermutations.add("L'");
                            tempPermutations.add("U'");
                            tempPermutations.add("L");
                            tempPermutations.add("U");
                        }
                        for(int j = 0; j < 3; j++){
                            tempPermutations.add("F");
                            tempPermutations.add("U");
                            tempPermutations.add("F'");
                            tempPermutations.add("U'");
                        }
                        for(int j = 0; j < 3; j++){
                            tempPermutations.add("B");
                            tempPermutations.add("U");
                            tempPermutations.add("B'");
                            tempPermutations.add("U'");
                        }
                        for(int j = 0; j < 3; j++){
                            tempPermutations.add("R'");
                            tempPermutations.add("U'");
                            tempPermutations.add("R");
                            tempPermutations.add("U");
                        }
                    }
                }
                else if (solveCount != 2) {
                    tempPermutations.add("U");
                }

                i = -1;
                solveCount = 0;
                unsolves.clear();
            }

            for (String s : tempPermutations) {
                finalPermutations.add(s);
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }
        }
        return finalPermutations;
    }

    public static ArrayList<String> solvePLLEdges(State state) {

        PLLInit();

        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();
        ArrayList<Cubicle> unsolves = new ArrayList<>();
        int pllCount = 0;

        Cubicle source = null;
        String sourceFace = "";
        Cubicle target = null;
        String targetFace = "";
        Cubicle proxy = null;
        String proxyFace = "";

        for (int i = 0; i < edgesTop.length; i++){
            Cubicle tempSource = cubeStateClone.getCube().get(edgesTop[i]);
            Cubicle tempTarget;
            if (!tempSource.getNode3D().isSolved()) {
                ArrayList<String> computePermutations= new ArrayList<>();
                computePermutations.add("U");
                computePermutations.add("U");
                tempTarget = cubeStateClone.getCube().get(computeTarget(tempSource, computePermutations));


                if (tempTarget.getPosition() == tempSource.getNode3D().getHomeCubicle()) {
                    source = tempSource;
                    target = tempTarget;
                    pllCount++;
                }
                unsolves.add(tempSource);
            }
        }

        System.out.println("UNSOLVES SIZE : " + unsolves.size() + " PLL COUNT : " + pllCount);

        if (source != null && target != null) {
            for (Cubicle c : unsolves) {
                if (!c.getPosition().equalsIgnoreCase(source.getPosition()) && !c.getPosition().equalsIgnoreCase(target.getPosition())) {
                    proxy = c;
                    break;
                }
            }

            for (String s : formatNodeFaces(source)) {
                if (!s.equalsIgnoreCase("U")) {
                    sourceFace = s;
                    break;
                }
            }

            for (String s : formatNodeFaces(target)) {
                if (!s.equalsIgnoreCase("U")) {
                    targetFace = s;
                    break;
                }
            }

            for (String s : formatNodeFaces(proxy)) {
                if (!s.equalsIgnoreCase("U")) {
                    proxyFace = s;
                    break;
                }
            }

            ArrayList<String> tempPermutations = buildPermutations(PLLMap.get(sourceFace + targetFace + proxyFace));
            System.out.println("PERM RULE IS : " + sourceFace + targetFace + proxyFace);

            for(String s : tempPermutations)
                finalPermutations.add(s);

            if(pllCount > 1) {
                for (String s : finalPermutations) {
                    if (s.length() > 1 && s.charAt(1) == '\'')
                        cubeStateClone.rotate(s.charAt(0) + "", -1);
                    else
                        cubeStateClone.rotate(s, 1);
                }
                ArrayList<String> recursivePermutations = solvePLLEdges(cubeStateClone);
                for(String s : recursivePermutations)
                    finalPermutations.add(s);
            }

        } else {
            ArrayList<String> recursivePermutations = new ArrayList<>();
            finalPermutations = buildPermutations(PLLMap.get("FBR"));

            for (String s : finalPermutations){
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }
            System.out.println("RECURSION!");
            recursivePermutations = solvePLLEdges(cubeStateClone);

            for (String s : recursivePermutations)
                finalPermutations.add(s);
        }

        return finalPermutations;
    }

    public static ArrayList<String> buildPermutations(String rule) {

        ArrayList<String> permutations = new ArrayList<>();

        for (int i = 0; i < rule.length(); i++){
            if (i < rule.length() - 1) {
                if (rule.charAt(i + 1) == '\'') {
                    permutations.add(rule.charAt(i) + "" + rule.charAt(i + 1));
                    i++;
                }
                else
                    permutations.add(rule.charAt(i) + "");
            } else {
                permutations.add(rule.charAt(i) + "");
            }
        }

        return permutations;
    }

}
