package com.rubix.input;

import java.util.concurrent.Callable;

public class InputTimer implements Callable {

    public InputTimer(){

    }

    @Override
    public Object call() throws Exception {
        Thread.sleep(50);
        return null;
    }

}
