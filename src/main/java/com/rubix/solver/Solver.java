package com.rubix.solver;

import com.rubix.cube.State;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Solver implements Callable<ArrayList<String>> {

    private State state;
    private String command;

    public Solver(State state, String command) {
        this.state = state;
        this.command = command;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        switch (command) {
            case "DAISY":
                return Cross.solveDaisy(state);
            case "CROSS":
                return Cross.solveCross(state);
            case "FL":
                return F2L.solveFL(state);
            case "SL":
                return F2L.solveSL(state);
            case "OLLEDGES":
                return OLL.solveOLLEdges(state);
            case "PLLCORNERS":
                return PLL.solvePLLCorners(state);
            case "OLLCORNERS":
                return OLL.solveOLLCorners(state);
            case "PLLEDGES":
                return PLL.solvePLLEdges(state);
        }
        return null;
    }
}
