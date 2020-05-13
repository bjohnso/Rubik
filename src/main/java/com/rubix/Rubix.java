package com.rubix;

import com.rubix.cube.State;
import com.rubix.input.KeyInput;
import com.rubix.rendering.window.Renderer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.rubix.solver.Cross.*;
import static com.rubix.solver.F2L.solveFL;
import static com.rubix.solver.F2L.solveSL;
import static com.rubix.solver.OLL.*;
import static com.rubix.solver.PLL.solvePLLCorners;
import static com.rubix.solver.PLL.solvePLLEdges;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_R;

public class Rubix implements Runnable{

    private Renderer renderer;
    private boolean running;
    private State state;
    private ArrayList<String> rotateQueue = new ArrayList<>();
    private String[] all = {
        "DAISY", "CROSS", "FL", "SL",
        "OLLEDGES", "PLLCORNERS", "OLLCORNERS", "PLLEDGES"
    };
    private int allCount = all.length;

    private String solve = "";

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Rubix(args[0]));
    }

    private void algoTimer() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!solve.equalsIgnoreCase("")) {
                    if (solve.equalsIgnoreCase("DAISY")){
                        ArrayList<String> solve = solveDaisy(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("DAISY");
                    } else if (solve.equalsIgnoreCase("CROSS")){
                        ArrayList<String> solve = solveCross(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("CROSS");
                    } else if (solve.equalsIgnoreCase("FL")){
                        ArrayList<String> solve = solveFL(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("FL");
                    } else if (solve.equalsIgnoreCase("SL")){
                        ArrayList<String> solve = solveSL(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("SL");
                    } else if (solve.equalsIgnoreCase("OLLEDGES")){
                        ArrayList<String> solve = solveOLLEdges(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("OLL EDGES");
                    } else if (solve.equalsIgnoreCase("PLLCORNERS")){
                        ArrayList<String> solve = solvePLLCorners(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("PLL CORNERS");
                    } else if (solve.equalsIgnoreCase("OLLCORNERS")){
                        ArrayList<String> solve = solveOLLCorners(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("OLL CORNERS");
                    } else if (solve.equalsIgnoreCase("PLLEDGES")){
                        ArrayList<String> solve = solvePLLEdges(state);
                        for (String s : solve)
                            rotateQueue.add(s);
                        state.addSolveRecipe(solve);
                        state.addSolve("PLL EDGES");
                    }
                    else if (solve.equalsIgnoreCase("ALL")){
                        allCount = 0;
                    } else if (solve.equalsIgnoreCase("RECIPE")){
                        for (String s : state.simplifySolveRecipe())
                            System.out.println("SOLVE RECIPE : " + s);
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
        }, 0,20);
    }

    private void allTimer() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (allCount < all.length)
                    solve = all[allCount];
                allCount++;
            }
        }, 0,3000);
    }

    public Rubix(String scramble){
        //Initialise Window
        state = new State();
        commandParser(scramble);

        //SOLVE

        /*ArrayList<String> solve = new ArrayList<>();

        solve = solveDaisy(state);
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
        allTimer();
        algoTimer();
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
            } else if (KeyInput.wasPressed(VK_S)){
                solve = "RECIPE";
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
            else if (KeyInput.wasPressed(VK_5))
                solve = "OLLEDGES";
            else if (KeyInput.wasPressed(VK_6))
                solve = "PLLCORNERS";
            else if (KeyInput.wasPressed(VK_7))
                solve = "OLLCORNERS";
            else if (KeyInput.wasPressed(VK_8))
                solve = "PLLEDGES";
            else if (KeyInput.wasPressed(VK_0))
                solve = "ALL";
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

    private void commandParser(String scramble) {
        String commands[] = scramble.split(" ");
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].contains("2")) {
                rotateQueue.add(commands[i].charAt(0) + "");
                rotateQueue.add(commands[i].charAt(0) + "");
            } else {
                rotateQueue.add(commands[i]);
            }
        }
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