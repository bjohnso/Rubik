package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;

public class Solver {

    public Solver(){
    }

    public State solveDaisy(State state) {
        state.solveAdd("DAISY");
        //Check if node face is white
        int i = 0;
        Node stems[][] = null;
        String observeRule = state.getRule('U');
        int backtrack = 0;
        int solveCount = 0;
        boolean solved = false;
        while (true){
            if (++i > 4) {
                i = 1;
            }
            if (solved) {
                if (++solveCount == 4)
                    break ;
                solved = false;
                while (backtrack > 0){
                    if (i < 2)
                        i = 5;
                    backtrack--;
                    i--;
                    state.permutate("U'");
                }
                backtrack = 0;
            }
            System.out.println("FACE : " + observeRule.charAt(i));
            stems = state.cloneRotation(observeRule.charAt(i) + "");
            String rotationRule = state.getRule(observeRule.charAt(i));
            for (int j = 0; j < stems.length; j++) {
                for (int k = 0; k < stems[j].length; k++) {
                    if (nodeCompare(stems[j][k], Color.WHITE, "EDGE") == 0) {
                        String ro = calculatePermutation(rotationRule, rotationRule.charAt(j + 1) + "", "U");
                        if (ro != null)
                            state.permutate(ro);
                        j = stems.length;
                        solved = true;
                        break ;
                    }
                }
            }

            if (!solved) {
                Plane plane = state.getPlaneMap().get(rotationRule.charAt(0) + "");
                stems = plane.getNodes();

                for (int j = 0; j < stems.length; j++){
                    for (int k = 0; k < stems[j].length; k++) {
                        if (nodeCompare(stems[j][k], Color.WHITE, "EDGE") == 0) {
                            String ro;
                            if (plane.getAlias().equalsIgnoreCase("B"))
                                ro = calculateRotation(plane.getAlias(), k, j, 2, 1);
                            else
                                ro = calculateRotation(plane.getAlias(), k, j, 0, 1);
                            if (ro != null)
                                state.permutate(ro);
                            j = stems.length;
                            break ;
                        }
                    }
                }
            }

            if (!solved) {
                //CANNOT SOLVE STATE IN THIS PERMUTATION SO ROTATE UP TO FIND SOLVE ON DIFFERENT PERMUTATION
                state.permutate("U");
                backtrack++;
            }
        }
        return state;
    }

    public State solveCross(State state){
        state.solveAdd("CROSS");
        Node stems[][] = null;
        String observeRule = state.getRule('U');

        int i = 0;
        int solveCount = 0;
        int backtrack = 0;
        boolean solved = false;
        while (solveCount < 4) {
            if (++i > 4) {
                i = 1;
            }
            if (solved) {
                if (++solveCount == 4)
                    break ;
                solved = false;
                while (backtrack > 0){
                    if (i < 2)
                        i = 5;
                    backtrack--;
                    i--;
                    state.permutate("U'");
                }
                backtrack = 0;
            }

            Plane plane  = state.getPlaneMap().get(observeRule.charAt(i) + "");
            stems = plane.getNodes();
            Node compare;
            if (observeRule.charAt(i) == 'B')
                compare = stems[0][1];
            else
                compare = stems[2][1];
            if (nodeCompareColor(compare, stems[1][1].getColor()) == 0) {
                state.permutate(observeRule.charAt(i) + "2");
                solved = true;
            }

            if (!solved) {
                state.permutate("U");
                backtrack++;
            }
        }
        return state;
    }

    public String calculatePermutation(String rule, String source, String target) {
        int source_pos = 0;
        int target_pos = 0;

        for (int i = 0; i < rule.length(); i++){
            if (rule.charAt(i) == source.charAt(0))
                source_pos = i;
            if (rule.charAt(i) == target.charAt(0))
                target_pos = i;
        }

        //ROTATION IS ANTI-CLOCKWISE
        if (target_pos - source_pos == -1 || target_pos - source_pos > 2) {
            return rule.charAt(0) + "'";
        }
        //ROTATION IS DOUBLE
        else if (target_pos - source_pos == 2 || target_pos - source_pos == -2){
            return rule.charAt(0) + "2";
        }
        //ROTATION IS CLOCKWISE
        else if (target_pos - source_pos != 0){
            return rule.charAt(0) + "";
        }
        return null;
    }

    public String calculateRotation(String face, int sourceX, int sourceY, int targetX, int targetY) {
        int diffX = targetX - sourceX;
        int diffY = targetY - sourceY;

        if (diffX == 0 && diffY == 0)
            return null;
        else if (diffX == 0 || diffY == 0)
            return face + "2";

        if ((diffX > 0 && diffY > 0 && sourceX == 0) || (diffX > 0 && diffY < 0 && targetX == 2) || (diffX < 0 && diffY > 0 && targetX == 0))
            return face;
        else
            return face + "'";
    }


    public int nodeCompareColor(Node a, Color color) {
        if (a.getColor() == color)
            return 0;
        return -1;
    }

    public int nodeCompare(Node a, Color color, String type) {
        if (a.getColor() == color && a.getType().equalsIgnoreCase(type))
            return 0;
        return -1;
    }
}
