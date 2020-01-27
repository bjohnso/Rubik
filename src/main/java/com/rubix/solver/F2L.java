package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.util.ArrayList;

import static com.rubix.solver.Util.*;

public class F2L {

    private static String[] cornersBottom = {
            "111",
            "113",
            "133",
            "131"
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

}
