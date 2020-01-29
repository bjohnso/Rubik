package com.rubix;

import com.rubix.solver.Daisy;
import com.rubix.solver.Util;
import com.rubix.cube.State;
import com.rubix.input.KeyInput;
import com.rubix.rendering.window.Renderer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.rubix.solver.Cross.solveCross;
import static com.rubix.solver.Daisy.solveDaisy;
import static com.rubix.solver.F2L.solveFL;
import static com.rubix.solver.F2L.solveSL;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_R;

public class Rubix implements Runnable{

    private Renderer renderer;
    private boolean running;
    private State state;
    private ArrayList<String> rotateQueue = new ArrayList<>();
    private String solve = "";

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Rubix());
    }

    private void solveTimer() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!solve.equalsIgnoreCase("")) {
                    if (solve.equalsIgnoreCase("DAISY")){
                        ArrayList<String> solve = solveDaisy(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolve("DAISY");
                    } else if (solve.equalsIgnoreCase("CROSS")){
                        ArrayList<String> solve = solveCross(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolve("CROSS");
                    } else if (solve.equalsIgnoreCase("FL")){
                        ArrayList<String> solve = solveFL(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolve("FL");
                    } else if (solve.equalsIgnoreCase("SL")){
                        ArrayList<String> solve = solveSL(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolve("SL");
                    }
                    solve = "";
                }
            }
        }, 0,500);
    }

    private void rotateTimer() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!rotateQueue.isEmpty()) {
                    String rotation = rotateQueue.get(0);
                    if (rotation.length() > 1 && rotation.charAt(1) == '\'')
                        state.rotate(rotation.charAt(0) + "", -1);
                    else
                        state.rotate(rotation, 1);
                    rotateQueue.remove(0);
                }
            }
        }, 0,100);
    }

    public Rubix(){
        //Initialise Window
        state = new State();

        rotateQueue.add("B");
        rotateQueue.add("B");
        rotateQueue.add("D");
        rotateQueue.add("L");
        rotateQueue.add("L");
        rotateQueue.add("F");
        rotateQueue.add("L");
        rotateQueue.add("L");
        rotateQueue.add("B");
        rotateQueue.add("B");
        rotateQueue.add("U");

        rotateQueue.add("U");
        rotateQueue.add("F'");
        rotateQueue.add("L");
        rotateQueue.add("F");
        rotateQueue.add("F");
        rotateQueue.add("L'");
        rotateQueue.add("D'");
        rotateQueue.add("F");
        rotateQueue.add("D");
        rotateQueue.add("F");
        rotateQueue.add("F");

        rotateQueue.add("B'");
        rotateQueue.add("R");
        rotateQueue.add("L");
        rotateQueue.add("F");
        rotateQueue.add("F");
        rotateQueue.add("B'");
        rotateQueue.add("D'");
        rotateQueue.add("L'");
        rotateQueue.add("F'");
        rotateQueue.add("R'");
        rotateQueue.add("L");
        rotateQueue.add("L");

        rotateQueue.add("D");
        rotateQueue.add("F'");
        rotateQueue.add("L");
        rotateQueue.add("L");
        rotateQueue.add("B");
        rotateQueue.add("R");
        rotateQueue.add("R");
        rotateQueue.add("B");
        rotateQueue.add("R");
        rotateQueue.add("D");
        rotateQueue.add("D");

        rotateQueue.add("B'");
        rotateQueue.add("L");
        rotateQueue.add("L");
        rotateQueue.add("D");
        rotateQueue.add("R");
        rotateQueue.add("F");
        rotateQueue.add("U");
        rotateQueue.add("U");
        rotateQueue.add("D'");
        rotateQueue.add("R");
        rotateQueue.add("R");

        rotateQueue.add("F'");
        rotateQueue.add("R'");
        rotateQueue.add("F");
        rotateQueue.add("F");
        rotateQueue.add("U");
        rotateQueue.add("U");
        rotateQueue.add("F");
        rotateQueue.add("F");
        rotateQueue.add("R'");
        rotateQueue.add("U'");
        rotateQueue.add("R'");
        rotateQueue.add("F'");

        //SOLVE

        //ArrayList<String> solve = new ArrayList<>();

        /*solve = solveDaisy(state);
        for (String s : solve) {
            if (s.length() > 1 && s.charAt(1) == '\'')
                state.rotate(s.charAt(0) + "", -1);
            else
                state.rotate(s, 1);
        }*/
        /*solve = solveCross(state);
        for (String s : solve) {
            if (s.length() > 1 && s.charAt(1) == '\'')
                state.rotate(s.charAt(0) + "", -1);
            else
                state.rotate(s, 1);
        }
        solve = solveFL(state);
        for (String s : solve) {
            if (s.length() > 1 && s.charAt(1) == '\'')
                state.rotate(s.charAt(0) + "", -1);
            else
                state.rotate(s, 1);
        }*/
        solveTimer();
        rotateTimer();
        this.renderer = new Renderer(this);
        KeyInput keyInput = new KeyInput();
        renderer.addKeyListener(keyInput);
        renderer.getFrame().addKeyListener(keyInput);
        start();
    }

    private void tick() {
        //Input Listeners
        String arg = "";
        if (KeyInput.isDown(VK_SHIFT)) {
            if (KeyInput.wasPressed(VK_F))
                arg = "F'";
            else if (KeyInput.wasPressed(VK_B))
                arg = "B'";
            else if (KeyInput.wasPressed(VK_U))
                arg = "U'";
            else if (KeyInput.wasPressed(VK_D))
                arg = "D'";
            else if (KeyInput.wasPressed(VK_L))
                arg = "L'";
            else if (KeyInput.wasPressed(VK_R))
                arg = "R'";
        } else if (KeyInput.isDown(VK_CONTROL)) {
            if (KeyInput.wasPressed((VK_G))){
                renderer.toggleGridVisible();
            }
        } else {
            if (KeyInput.wasPressed(VK_F))
                arg = "F";
            else if (KeyInput.wasPressed(VK_B))
                arg = "B";
            else if (KeyInput.wasPressed(VK_U))
                arg = "U";
            else if (KeyInput.wasPressed(VK_D))
                arg = "D";
            else if (KeyInput.wasPressed(VK_L))
                arg = "L";
            else if (KeyInput.wasPressed(VK_R))
                arg = "R";
            else if (KeyInput.wasPressed(VK_1))
                solve = "DAISY";
            else if (KeyInput.wasPressed(VK_2))
                solve = "CROSS";
            else if (KeyInput.wasPressed(VK_3))
                solve = "FL";
            else if (KeyInput.wasPressed(VK_4))
                solve = "SL";
        }
        if (!arg.equals("")){
            if (arg.length() > 1) {
                if (arg.charAt(1) == '\'')
                    state.rotate(arg.charAt(0) + "", -1);
            } else
                state.rotate(arg.charAt(0) + "", 1);
        }
    }

    private void render() {
        renderer.render(state);
    }

    public void run() {
        //Game Loop
        double targetTicks = 60.0;
        double nanoSecondsPT = 100000000.0 / targetTicks;
        double unprocessed = 0.0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int fps = 0;
        int tps = 0;
        boolean canRender = false;

        while (running){
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nanoSecondsPT;
            lastTime = now;

            if (unprocessed >= 1){
                tick();
                unprocessed--;
                tps++;
                canRender = true;
            } else {
                canRender = false;
            }

            KeyInput.update();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (canRender){
                render();
                fps++;
            }

            if (System.currentTimeMillis() - 1000 > timer){
                timer += 1000;
                //System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
                fps = 0;
                tps = 0;
            }
        }

        System.exit(0);
    }

    private void start(){
        if (running)
            return;
        running = true;
        new Thread(this, "RubixMain-Thread").start();
    }

    public void stop(){
        if (!running)
            return;
        running = false;
    }
}