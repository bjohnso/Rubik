package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.util.ArrayList;

import static com.rubix.solver.Util.findNode;
import static com.rubix.solver.Util.planeMap;

public class F2L {

    private static String[] cornersBottom = {
            "111",
            "113",
            "133",
            "131"
    };

    /*public static ArrayList<String> FL(State state) {
        State cubeStateClone = state.cloneState();
        ArrayList<String> tempPermutations = new ArrayList<>();

        int solveCount = 0;
        int backtrack = 0;

        for (int i = 0; i < cornersBottom.length; i++){
            Cubicle target = cubeStateClone.getCube().get(cornersBottom[i]);
            Cubicle source;
            if (!target.getNode3D().isSolved()){
                source = findNode(cubeStateClone, planeMap.get("U"), target.getPosition());
                String auxTarget = "3" + target.getPosition().charAt(1) + "" + target.getPosition().charAt(2);
                //target.
            } else {
                if (target.getNode3D().isTwisted()) {
                    //TODO : SOLVE
                } else {
                    //TODO : COMPLETELY SOLVED
                    solveCount++;
                }
            }

            if (i >= cornersBottom.length - 1 && solveCount < 4){
                i = -1;
                solveCount = 0;
                backtrack = 0;
            }
        }
        return tempPermutations;
    }*/
}
