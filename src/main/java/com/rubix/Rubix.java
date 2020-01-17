package com.rubix;

import com.rubix.artifacts.State;

public class Rubix {

    public static void main(String args[]) {
        State state = new State();
        state.permutate("R");
        state.permutate("F");
        state.permutate("L");
        state.permutate("R");
        state.permutate("D");
        state.permutate("F");
        state.permutate("U");
        state.permutate("R");
        state.permutate("R");
        state.permutate("F");
        state.permutate("L");
        state.permutate("L");
        state.permutate("F");
        state.permutate("U");
        state.permutate("R");
        state.permutate("D");
        state.permutate("B");
        state.permutate("B");
        state.permutate("L");
        state.permutate("D");
        state.permutate("F");
        state.permutate("B");
        state.permutate("R");

        state.printState();
    }
}