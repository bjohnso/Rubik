package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;

public class Solver {

    public Solver(){
    }

    public State solveWhiteCross(State state) {
        HashMap<String, Plane> planeMap = state.getPlaneMap();
        String rules[] = state.getRules();

        //Check if node face is white
        int i = 0;
        Node stems[][] = null;
        String observeRule = state.getRule('U');
        while (++i < 5){
            boolean solved = false;
            stems = state.cloneRotation(observeRule.charAt(i) + "");
            String rotationRule = state.getRule(observeRule.charAt(i));
            for (int j = 0; j < stems.length; j++){
                for (int k = 0; k < stems[j].length; k++){
                    if (nodeCompare(stems[j][k], Color.WHITE, "EDGE") == 0) {
                        String ro = calculatePermutation(rotationRule, rotationRule.charAt(j + 1) + "", "U");
                        state.permutate(ro);
                        j = stems.length;
                        solved = true;
                        break ;
                    }
                }
            }

            if (!solved) {

            }
        }

        return state;
    }

    public String calculatePermutation(String rule, String source, String target) {
        int source_pos = 0;
        int target_pos = 0;

        for (int i = 1; i < rule.length(); i++){
            if (rule.charAt(i) == source.charAt(0)){
                source_pos = i;
            } else if (rule.charAt(i) == target.charAt(0)){
                target_pos = i;
            }
        }

        //ROTATION IS ANTI-CLOCKWISE
        if (target_pos - source_pos == -1) {
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

    public int nodeCompare(Node a, Color color, String type) {
        if (a.getColor() == color && a.getType().equalsIgnoreCase(type))
            return 0;
        return -1;
    }
}
