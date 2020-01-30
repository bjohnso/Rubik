package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.awt.*;
import java.util.ArrayList;

import static com.rubix.solver.Util.computeNodeRelations;
import static com.rubix.solver.Util.computePrimaryPermutation;

public class OLL {

    private static String[] edgesTop = {
            "312",
            "321",
            "332",
            "323"
    };

    private static String[] cornersTop = {
            "311",
            "331",
            "333",
            "313"
    };

    public static ArrayList<String> solveOLLEdges(State state) {

        State cubeStateClone = state.cloneState();
        ArrayList<String> finalPermutations = new ArrayList<>();
        Boolean isOLL = false;

        while (!isOLL) {

            Boolean scenario1 = true;
            Boolean scenario2 = false;
            Boolean scenario3 = false;
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
                        if (cubicleList.get(0).getNode3D().getFace("U") == Color.YELLOW)
                            scenario2 = true;
                        else if (cubicleList.get(i - 2).getNode3D().getFace("U") == Color.YELLOW)
                            scenario3 = true;
                    }
                    else if (i > 1) {
                        if (cubicleList.get(i - 1).getNode3D().getFace("U") == Color.YELLOW)
                            scenario2 = true;
                        else if (cubicleList.get(i - 2).getNode3D().getFace("U") == Color.YELLOW)
                            scenario3 = true;
                    }
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
                    if (!lastTarget.equalsIgnoreCase("323")) {
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

    public static ArrayList<String> solveOLLCorners(State state){

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
                    System.out.println("SOLVE COUNT 2");
                    ArrayList<String> relationship = computeNodeRelations(unsolves.get(0), unsolves.get(1));
                    if (!relationship.isEmpty()){
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
                else if (solveCount == 0 || solveCount == 1) {
                    System.out.println("SOLVE COUNT 0 OR 1");
                    tempPermutations.add("U");
                }

                i = -1;
                solveCount = 0;
                unsolves.clear();
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

}
