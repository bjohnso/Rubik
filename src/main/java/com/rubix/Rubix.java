package com.rubix;

import com.rubix.artifacts.State;

public class Rubix {

    public static void main(String args[]) {
        State state = new State();
        state.permutate("R");
        state.permutate("L");
        state.permutate("F");
        state.permutate("F");
        state.permutate("L");
        state.permutate("R");
        
        //state.permutate("R");
        //state.permutate("U");
        //state.permutate("B");
        //state.permutate("F");
        state.printState();
    }
}