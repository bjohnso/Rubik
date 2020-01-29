package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.util.ArrayList;

import static com.rubix.runes.Runes.getMirror;
import static com.rubix.solver.Util.*;

public class F2L {

    private static String[] cornersBottom = {
            "111",
            "113",
            "133",
            "131"
    };

    private static String[] edgesTop = {
            "312",
            "321",
            "332",
            "323"
    };

    private static String[] edgesMiddle = {
            "213",
            "211",
            "231",
            "233"
    };

    private static String[] middles = {
            "212",
            "223",
            "232",
            "221"
    };

    public static ArrayList<String> solveFL(State state) {
        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();

        int solveCount = 0;

        for (int i = 0; i < cornersBottom.length; i++){
            Cubicle target = cubeStateClone.getCube().get(cornersBottom[i]);

            System.out.println("FL - NEW TARGET : " + target.getPosition());
            ArrayList<String> ejectPermutations = new ArrayList();
            ArrayList<String> auxPermutations = new ArrayList();
            ArrayList<String> vertPermutations = new ArrayList<>();

            Cubicle source;
            if (!target.getNode3D().isSolved()){
                Cubicle auxTarget = cubeStateClone.getCube().get("3" + target.getPosition().charAt(1) + "" + target.getPosition().charAt(2));
                if ((source = findNode(cubeStateClone, planeMap.get("U"), target.getPosition())) != null){
                    System.out.println("FL - SOLUTION FOUND IN PLANE : U at node : " + source.getPosition() + " FOR THIS TARGET");
                    //SEARCH UP PLANE
                    auxPermutations = computePrimaryPermutation(source, auxTarget, "U");
                    if (auxPermutations.isEmpty())
                        System.out.println("FL - NOTHING TO BE DONE FOR PRIME IN PLANE : U at node : " + source.getPosition() + " FOR THIS TARGET");
                    //DETERMINE WHETHER TO US LEFT HAND RULE OR RIGHT HAND RULE
                    vertPermutations = vertInsert(auxTarget);
                } else {
                    //SEARCH DOWN PLANE
                    source = findNode(cubeStateClone, planeMap.get("D"), target.getPosition());

                    System.out.println("FL - SOLUTION FOUND IN PLANE : U at node : " + source.getPosition() + " FOR THIS TARGET");

                    ejectPermutations = vertInsert(source);
                    Cubicle auxUpper = cubeStateClone.getCube().get(computeTarget(source, ejectPermutations));
                    auxPermutations = computePrimaryPermutation(auxUpper, auxTarget, "U");
                    vertPermutations = vertInsert(auxTarget);
                }
            } else {
                if (target.getNode3D().isTwisted()) {
                    System.out.println("FL - SOLVED, at node : " + target.getPosition() + " BUT TWISTED... EJECTING FOR RETRY");
                    ejectPermutations = vertInsert(target);
                } else {
                    System.out.println("FL - SOLVED, NOTHING TO BE DONE FOR TARGET : " + target.getPosition());
                    solveCount++;
                }
            }

            ArrayList<String> tempPermutations = new ArrayList<>();

            for (String s : ejectPermutations)
                tempPermutations.add(s);
            for (String s : auxPermutations)
                tempPermutations.add(s);
            for (String s : vertPermutations)
                tempPermutations.add(s);

            for (String s : tempPermutations) {
                finalPermutations.add(s);
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }

            if (i >= cornersBottom.length - 1 && solveCount < 4){
                i = -1;
                solveCount = 0;
            }
        }
        return finalPermutations;
    }

    public static ArrayList<String> solveSL(State state) {
        State cubeStateClone = state.cloneState();

        ArrayList<String> finalPermutations = new ArrayList<>();

        int solveCount = 0;

        for (int i = 0; i < edgesMiddle.length; i++) {
            Cubicle source;
            Cubicle target = cubeStateClone.getCube().get(edgesMiddle[i]);

            System.out.println("SL - NEW TARGET : " + target.getPosition());

            ArrayList<String> ejectPermutations = new ArrayList<>();
            ArrayList<String> slPermutations = new ArrayList<>();

            if (!target.getNode3D().isSolved()) {
                //FIND CORRECT NODE IN EITHER MIDDLE LAYER OR TOP LAYER
                if ((source = findNode(cubeStateClone, layerMap.get("2"), target.getPosition())) != null){
                    System.out.println("SL - SOLUTION FOUND IN LAYER MIDDLE at node : " + source.getPosition() + " FOR THIS TARGET");
                    //EJECT SOURCE NODE FROM CURRENT POSITION
                    System.out.println("SL - EJECTING...");
                    ejectPermutations = diagInsert(source);
                } else {
                    source = findNode(cubeStateClone, layerMap.get("3"), target.getPosition());
                    System.out.println("SL - SOLUTION FOUND IN LAYER TOP at node : " + source.getPosition() + " FOR THIS TARGET");
                    slPermutations = computeSLPermutations(state, source, target);
                }
            } else {
                if (target.getNode3D().isTwisted()) {
                    System.out.println("SL - TARGET IS TWISTED: " + target.getPosition());
                    System.out.println("SL - EJECTING...");
                    ejectPermutations = diagInsert(target);
                }
                else {
                    System.out.println("SL - SOLVED, NOTHING TO BE DONE FOR TARGET : " + target.getPosition());
                    solveCount++;
                }
            }

            ArrayList<String> tempPermutations = new ArrayList<>();

            for (String s : ejectPermutations)
                tempPermutations.add(s);
            for (String s : slPermutations)
                tempPermutations.add(s);

            for (String s : tempPermutations) {
                finalPermutations.add(s);
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }

            if (i >= edgesMiddle.length - 1 && solveCount < 4){
                i = -1;
                solveCount = 0;
            }
        }
        return finalPermutations;
    }

    private static ArrayList<String> computeSLPermutations(State state, Cubicle source, Cubicle target) {

        ArrayList<String> targetRelations = formatNodeFaces(target);
        ArrayList<String> orientatePermutations = new ArrayList<>();
        ArrayList<String> slPermutations = new ArrayList<>();
        ArrayList<String> finalPermutations = new ArrayList<>();

        String mirror = "";

        for (String s : targetRelations) {
            for (int i = 0; i < middles.length; i++){
                if (source.getNode3D().getFace("U") == state.getCube().get(middles[i]).getNode3D().getFace(s)) {
                    mirror = getMirror(s);
                    break ;
                }
            }
        }

        for (int i = 0; i < edgesTop.length; i++){
            Cubicle temp = state.getCube().get(edgesTop[i]);
            if (state.getCube().get("2" + edgesTop[i].charAt(1) + "" + edgesTop[i].charAt(2)).getNode3D().getFace(mirror) != null) {
                orientatePermutations = computePrimaryPermutation(source, temp, "U");

                //FU
                if (temp.getPosition() == "312"){
                    //BL
                    if (target.getPosition() == "231"){
                        slPermutations.add("B'");
                        slPermutations.add("U'");
                        slPermutations.add("B");
                        slPermutations.add("U");

                        slPermutations.add("L");
                        slPermutations.add("U");
                        slPermutations.add("L'");
                        slPermutations.add("U'");
                    }
                    //BR
                    else if (target.getPosition() == "233"){
                        slPermutations.add("B");
                        slPermutations.add("U");
                        slPermutations.add("B'");
                        slPermutations.add("U'");

                        slPermutations.add("R'");
                        slPermutations.add("U'");
                        slPermutations.add("R");
                        slPermutations.add("U");
                    }
                }
                //BU
                else if (temp.getPosition() == "332"){
                    //FL
                    if (target.getPosition() == "211"){
                        slPermutations.add("F");
                        slPermutations.add("U");
                        slPermutations.add("F'");
                        slPermutations.add("U'");

                        slPermutations.add("L'");
                        slPermutations.add("U'");
                        slPermutations.add("L");
                        slPermutations.add("U");
                    }
                    //FR
                    else if (target.getPosition() == "213"){
                        slPermutations.add("F'");
                        slPermutations.add("U'");
                        slPermutations.add("F");
                        slPermutations.add("U");

                        slPermutations.add("R");
                        slPermutations.add("U");
                        slPermutations.add("R'");
                        slPermutations.add("U'");
                    }
                }
                //LU
                else if (temp.getPosition() == "321"){
                    //RF
                    if (target.getPosition() == "213"){
                        slPermutations.add("R");
                        slPermutations.add("U");
                        slPermutations.add("R'");
                        slPermutations.add("U'");

                        slPermutations.add("F'");
                        slPermutations.add("U'");
                        slPermutations.add("F");
                        slPermutations.add("U");
                    }
                    //RB
                    else if (target.getPosition() == "233"){
                        slPermutations.add("R'");
                        slPermutations.add("U'");
                        slPermutations.add("R");
                        slPermutations.add("U");

                        slPermutations.add("B");
                        slPermutations.add("U");
                        slPermutations.add("B'");
                        slPermutations.add("U'");
                    }
                }
                //RU
                else if (temp.getPosition() == "323"){
                    //LF
                    if (target.getPosition() == "211"){
                        slPermutations.add("R");
                        slPermutations.add("U");
                        slPermutations.add("R'");
                        slPermutations.add("U'");

                        slPermutations.add("F'");
                        slPermutations.add("U'");
                        slPermutations.add("F");
                        slPermutations.add("U");
                    }
                    //LB
                    else if (target.getPosition() == "231"){
                        slPermutations.add("L");
                        slPermutations.add("U");
                        slPermutations.add("L'");
                        slPermutations.add("U'");

                        slPermutations.add("B'");
                        slPermutations.add("U'");
                        slPermutations.add("B");
                        slPermutations.add("U");
                    }
                }
                break ;
            }
        }

        for (String s : orientatePermutations)
            finalPermutations.add(s);
        for (String s : slPermutations)
            finalPermutations.add(s);
        return finalPermutations;
    }

    //WILL ONLY ACCURATELY INSERT VERTICAL ALIGNED NODES
    public static ArrayList<String> vertInsert(Cubicle source){
        ArrayList<String> permutations = new ArrayList<>();

        if (source.getNode3D().getFace("F") != null && source.getNode3D().getFace("R") != null) {
            permutations.add("R");
            permutations.add("U");
            permutations.add("R'");
            permutations.add("U'");
        } else if (source.getNode3D().getFace("B") != null && source.getNode3D().getFace("L") != null) {
            permutations.add("L");
            permutations.add("U");
            permutations.add("L'");
            permutations.add("U'");
        } else if (source.getNode3D().getFace("F") != null && source.getNode3D().getFace("L") != null) {
            permutations.add("L'");
            permutations.add("U'");
            permutations.add("L");
            permutations.add("U");
        } else if (source.getNode3D().getFace("B") != null && source.getNode3D().getFace("R") != null) {
            permutations.add("R'");
            permutations.add("U'");
            permutations.add("R");
            permutations.add("U");
        }
        return permutations;
    }

    public static ArrayList<String> diagInsert(Cubicle source){
        ArrayList<String> permutations = new ArrayList<>();

        permutations = vertInsert(source);

        if (source.getNode3D().getFace("F") != null && source.getNode3D().getFace("R") != null) {
            permutations.add("F'");
            permutations.add("U'");
            permutations.add("F");
            permutations.add("U");
        } else if (source.getNode3D().getFace("B") != null && source.getNode3D().getFace("L") != null) {
            permutations.add("B'");
            permutations.add("U'");
            permutations.add("B");
            permutations.add("U");
        } else if (source.getNode3D().getFace("F") != null && source.getNode3D().getFace("L") != null) {
            permutations.add("F");
            permutations.add("U");
            permutations.add("F'");
            permutations.add("U'");
        } else if (source.getNode3D().getFace("B") != null && source.getNode3D().getFace("R") != null) {
            permutations.add("B");
            permutations.add("U");
            permutations.add("B'");
            permutations.add("U'");

        }
        return permutations;
    }

}
