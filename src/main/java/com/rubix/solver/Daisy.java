package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.awt.*;
import java.util.ArrayList;

import static com.rubix.runes.Runes.ROTATION_FACES_U;
import static com.rubix.solver.Util.*;

public class Daisy {

    private static String edges[] = {
            "312",
            "321",
            "332",
            "323"
    };

    public static ArrayList<String> solveDaisy(State state) {

        //Init Solve Util
        Util.initSolve();

        State cubeStateClone = new State();
        cubeStateClone.setCube(state.cloneCube());
        ArrayList<String> finalPermutations = new ArrayList<>();
        int backtrack = 0;
        int solveCount = 0;

        //INSPECT TOP PLANE
        for (int i = 0; i < edges.length; i++) {
            ArrayList<String> tempPermutations = new ArrayList<>();
            Cubicle target = cubeStateClone.getCube().get(edges[i]);
            Cubicle source = null;

            System.out.println("DAISY - NEW TARGET : " + target.getPosition());

            //DETERMINE IF NODES OF TYPE EDGE HAVE A WHITE FACE DISPLAYED ON THE TOP PLANE
            if (target.getType().equalsIgnoreCase("E")) {
                if (target.getNode3D().getFace("U") != null && target.getNode3D().getFace("U") != Color.WHITE) {
                    //IF NOT SEARCH FOR A NODE OF TYPE EDGE WITH A WHITE FACE IN EACH SUCCESSIVE PLANE THAT INTERSECTS TOP
                    if ((source = findNode(cubeStateClone, planeMap.get(ROTATION_FACES_U[i]), "E", Color.WHITE)) != null) {
                        System.out.println("DAISY - SOLUTION FOUND IN PLANE : " + ROTATION_FACES_U[i] + " at node : " + source.getPosition() + " FOR THIS TARGET");
                        //DETERMINE SET OF PERMUTATIONS TO SOLVE FOR THE DESIRED STATE
                        if (source.getNode3D().getFace(ROTATION_FACES_U[i]) != Color.WHITE) {
                            tempPermutations = computePermutations(cubeStateClone, source, target, "NONE");
                        }
                        else
                            tempPermutations = computePermutations(cubeStateClone, source, target, ROTATION_FACES_U[i]);
                        i -= backtrack;
                        while (backtrack > 0) {
                            tempPermutations.add("U'");
                            backtrack--;
                        }
                    }
                    else {
                        tempPermutations.add("U");
                        backtrack++;
                    }
                }
                else {
                    System.out.println("DAISY - SOLVED, NOTHING TO BE DONE FOR TARGET : " + target.getPosition());
                    solveCount++;
                }
            }
            //ADD CALCULATED PERMUTATIONS TO FINAL ARRAY LIST AND PERFORM CALCULATED PERMUTATIONS ON CUBE CLONE
            for (String s : tempPermutations){
                finalPermutations.add(s);
                if (s.length() > 1 && s.charAt(1) == '\'')
                    cubeStateClone.rotate(s.charAt(0) + "", -1);
                else
                    cubeStateClone.rotate(s, 1);
            }
            if (i == edges.length - 1 && solveCount < 4) {
                i = -1;
                backtrack = 0;
                solveCount = 0;
            }
        }
        return finalPermutations;
    }
}
