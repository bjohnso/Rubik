package com.rubix.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KeyInput extends KeyAdapter {

    private static int NUM_KEYS = 256;

    private static final boolean keys[] = new boolean[NUM_KEYS];
    private static final boolean lastKeys[] = new boolean[NUM_KEYS];
    private boolean timer = false;

    private InputTimer inputTimer = new InputTimer();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<Object> future;

    @Override
    public void keyPressed(KeyEvent e) {
        if (timer){
            if (future.isDone()){
                keys[e.getKeyCode()] = true;
            }
        }
        else {
            timer = true;
            future = executorService.submit(inputTimer);
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void update(){
        for (int i = 0; i < NUM_KEYS; i++){
            lastKeys[i] = keys[i];
        }
    }

    public static boolean isDown(int keyCode){
        return keys[keyCode];
    }

    public static boolean wasPressed(int keyCode){
        return isDown(keyCode) && !lastKeys[keyCode];
    }

    public static boolean wasReleased(int keyCode){
        return !isDown(keyCode) && lastKeys[keyCode];
    }
}
