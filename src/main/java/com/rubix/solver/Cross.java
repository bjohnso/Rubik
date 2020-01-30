package com.rubix.solver;

import com.rubix.cube.Cubicle;
import com.rubix.cube.State;

import java.awt.*;
import java.util.ArrayList;

import static com.rubix.runes.Runes.ROTATION_FACES_U;
import static com.rubix.solver.Util.*;
import static com.rubix.solver.Util.computePermutations;

public class Cross {

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

    public static ArrayList<String> solveCross(State state) {
        State cubeStateClone = new State();
        cubeStateClone.setCube(state.cloneCube());
        ArrayList<String> finalPermutations = new ArrayList<>();

        int backtrack = 0;
        int solveCount = 0;

        //INSPECT TOP PLANE
        for (int i = 0; i < edges.length; i++) {
            ArrayList<String> tempPermutations = new ArrayList<>();
            Cubicle source = cubeStateClone.getCube().get(edges[i]);
            Cubicle compare = cubeStateClone.getCube().get("2" + edges[i].charAt(1) + "" + edges[i].charAt(2));

            System.out.println("CROSS - NEW SOURCE : " + source.getPosition());

            //CHECK IF NOT ALREADY SOLVED
            if (source.getNode3D().getFace("U") == Color.WHITE) {
                //COMPARE NODE ON TOP PLANE WITH NODE IN MIDDLE PLANE BENEATH IT
                ArrayList<String> relationship = computeNodeRelations(source, compare);
                for (String s : relationship) {
                    //CHECK IF COLORS MATCH AND THEN SOLVE
                    if (source.getNode3D().getFace(s) == compare.getNode3D().getFace(s)) {
                        System.out.println("CROSS - SOURCE FOUND IN PLANE : " + s + " at node : 1" + source.getPosition().charAt(1) + "" + source.getPosition().charAt(2) + " FOR THIS TARGET");
                        tempPermutations.add(s);
                        tempPermutations.add(s);
                        i -= backtrack;
                        while (backtrack > 0) {
                            tempPermutations.add("U'");
                            backtrack--;
                        }
                        break;
                    } else {
                        tempPermutations.add("U");
                        backtrack++;
                        break;
                    }
                }
            } else {
                System.out.println("CROSS - SOLVED, NOTHING TO BE DONE FOR SOURCE : " + source.getPosition());
                solveCount++;
            }

            //ADD CALCULATED PERMUTATIONS TO FINAL ARRAY LIST AND PERFORM CALCULATED PERMUTATIONS ON CUBE CLONE
            for (String s : tempPermutations) {
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
