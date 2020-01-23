package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;

public class Solver {

    public Solver(){

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

    public int nodeComparePrecise(Node a, Color color, String type) {
        if (a.getColor() == color && a.getType().equalsIgnoreCase(type))
            return 0;
        return -1;
    }
}
