package com.rubix.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.*;

public class KeyInput extends KeyAdapter {

    private static int NUM_KEYS = 256;

    private static final boolean keys[] = new boolean[NUM_KEYS];
    private static final boolean lastKeys[] = new boolean[NUM_KEYS];
    private static boolean timer = false;

    private void createTimer() {
        timer = true;
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer = false;
            }
        }, 100);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!timer) {
            keys[e.getKeyCode()] = true;
            createTimer();
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
