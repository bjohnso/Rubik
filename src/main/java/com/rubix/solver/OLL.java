package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.awt.*;
import java.util.ArrayList;
import static com.rubix.solver.Util.computePrimaryPermutation;

public class OLL {

    public static ArrayList<String> solveOLLEdges(State state) {

        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();
        Boolean isOLL = false;

        while (!isOLL) {

            Boolean scenario1 = true;
            Boolean scenario2 = false;
            Boolean scenario3 = false;
            String firstTarget = "";
            String lastTarget = "";

            ArrayList<Cubicle> cubicleList = new ArrayList<>();
            cubicleList.add(cubeStateClone.getCube().get("312"));
            cubicleList.add(cubeStateClone.getCube().get("321"));
            cubicleList.add(cubeStateClone.getCube().get("332"));
            cubicleList.add(cubeStateClone.getCube().get("323"));

            isOLL = true;
            for (int i = 0 ; i < cubicleList.size(); i++){
                //System.out.println("OLL - NEW TARGET : " + cubicleList.get(i).getPosition());
                if (cubicleList.get(i).getNode3D().getFace("U") != Color.YELLOW){
                    //System.out.println("TARGET DID NOT MATCH OLL SOLVE CRITERIA");
                    isOLL = false;
                } else {
                    scenario1 = false;
                    if (i == 1){
                        if (cubicleList.get(i - 1).getNode3D().getFace("U") == Color.YELLOW)
                            scenario2 = true;
                    }
                    else if (i == cubicleList.size() - 1){
                        if (cubicleList.get(0).getNode3D().getFace("U") == Color.YELLOW
                                || cubicleList.get(i - 1).getNode3D().getFace("U") == Color.YELLOW){
                            scenario2 = true;
                        }
                        else if (cubicleList.get(i - 2).getNode3D().getFace("U") == Color.YELLOW)
                            scenario3 = true;
                    }
                    else if (i > 1) {
                        if (cubicleList.get(i - 1).getNode3D().getFace("U") == Color.YELLOW)
                            scenario2 = true;
                        else if (cubicleList.get(i - 2).getNode3D().getFace("U") == Color.YELLOW)
                            scenario3 = true;
                    }

                    if (firstTarget.equalsIgnoreCase(""))
                        firstTarget = cubicleList.get(i).getPosition();
                    lastTarget = cubicleList.get(i).getPosition();
                }
            }

            ArrayList<String> tempPermutations = new ArrayList<>();
            ArrayList<String> auxUpper = new ArrayList<>();
            if (!isOLL) {
                if (scenario1 || scenario3) {
                    if (!lastTarget.equalsIgnoreCase("")) {
                        System.out.println("OLL - SCENARIO 3");
                        auxUpper = computePrimaryPermutation(cubeStateClone.getCube().get(lastTarget), cubeStateClone.getCube().get("312"), "U");
                        for (String s : auxUpper)
                            tempPermutations.add(s);
                    } else
                        System.out.println("OLL - SCENARIO 1");
                    tempPermutations.add("F");
                    tempPermutations.add("R");
                    tempPermutations.add("U");
                    tempPermutations.add("R'");
                    tempPermutations.add("U'");
                    tempPermutations.add("F'");
                } else if (scenario2) {
                    System.out.println("OLL - SCENARIO 2");
                    if (!lastTarget.equalsIgnoreCase("323") || !firstTarget.equalsIgnoreCase("312")) {
                        auxUpper = computePrimaryPermutation(cubeStateClone.getCube().get(lastTarget), cubeStateClone.getCube().get("312"), "U");
                        for (String s : auxUpper)
                            tempPermutations.add(s);
                    }
                    tempPermutations.add("B");
                    tempPermutations.add("U");
                    tempPermutations.add("L");
                    tempPermutations.add("U'");
                    tempPermutations.add("L'");
                    tempPermutations.add("B'");
                }
            } else {
                System.out.println("OLL - NOTHING TO BE DONE, STATE IS SOLVED");
            }
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

    public static ArrayList<String> solveOLLCorners(State state) {
        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();

        int untwisted = 0;

        while (untwisted < 4) {
            ArrayList<String> tempPermutations = new ArrayList<>();
            Cubicle target = cubeStateClone.getCube().get("311");
            if (target.getNode3D().getFace("U") != Color.YELLOW){
                tempPermutations.add("L");
                tempPermutations.add("D");
                tempPermutations.add("L'");
                tempPermutations.add("D'");
                untwisted = 0;
            } else {
                tempPermutations.add("U");
                untwisted++;
            }

            for (String s : tempPermutations){
                finalPermutations.add(s);
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }
        }

        for (String s : orientateLL(cubeStateClone))
            finalPermutations.add(s);
        return finalPermutations;
    }

    public static ArrayList<String> orientateLL(State state){
        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();

        while (!cubeStateClone.getCube().get("311").getNode3D().isSolved()){
            finalPermutations.add("U");
            cubeStateClone.rotate("U", 1);
        }
        return finalPermutations;
    }
}
