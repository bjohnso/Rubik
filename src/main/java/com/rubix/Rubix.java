package com.rubix;

import com.rubix.cube.State;
import com.rubix.input.KeyInput;

import com.rubix.rendering.window.Renderer;
import com.rubix.solver.Solver;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_R;

public class Rubix implements Runnable{

    private Renderer renderer;
    private boolean running;
    private State state;
    private State computeState;
    private ArrayList<ArrayList<String>> scrambleQueue = new ArrayList<>();
    private ArrayList<String> rotateQueue = new ArrayList<>();
    private String lifeCycle[] = {"SCRAMBLE", "SOLVE", "PRINT"};
    private int currentLifeCycleState = 0;
    private boolean isWaiting = false;
    private String[] all = {
        "DAISY", "CROSS", "FL", "SL",
        "OLLEDGES", "PLLCORNERS", "OLLCORNERS", "PLLEDGES"
    };
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private boolean autoMode = false;

    public static void main(String[] args) {
        executorService.execute(new Rubix(args));
    }

    private void algoTimer() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentLifeCycleState == 0) {
                    isWaiting = false;
                    if (!scrambleQueue.isEmpty()) {
                        state.resetSolveRecipes();
                        computeState.resetSolveRecipes();

                        performRotations(scrambleQueue.get(0));
                        for (String s : scrambleQueue.get(0)) {
                            rotateQueue.add(s);
                        }
                    }
                    if (autoMode)
                        currentLifeCycleState++;
                    else
                        isWaiting = true;
                }

                if (currentLifeCycleState == 1) {
                    isWaiting = false;
                    boolean isSolved = false;
                    int solveOperation = 0;
                    Future<ArrayList<String>> future = executorService.submit(new Solver(computeState, all[solveOperation]));
                    while (!isSolved) {
                        if (future.isDone()) {
                            try {
                                performRotations(future.get());
                                computeState.addSolveRecipe(future.get());

                                if (solveOperation >= all.length - 1) {
                                    isSolved = true;
                                } else {
                                    future = executorService.submit(new Solver(computeState, all[++solveOperation]));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for (String s : computeState.getSolveRecipes()) {
                        rotateQueue.add(s);
                    }
                    state.addSolveRecipe(computeState.getSolveRecipes());
                    if (autoMode)
                        currentLifeCycleState++;
                    else
                        isWaiting = true;
                }

                if (currentLifeCycleState == 2) {
                    isWaiting = false;
                    System.out.println("<----------SOLUTION---------->");
                    System.out.println(state.simplifySolveRecipe());
                    currentLifeCycleState = 0;
                    if (!autoMode)
                        isWaiting = true;
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
                    else if (rotation.length() > 1 && rotation.charAt(1) == '2') {
                        state.rotate(rotation.charAt(0) + "", 1);
                        state.rotate(rotation.charAt(0) + "", 1);
                    }
                    else
                        state.rotate(rotation, 1);
                    rotateQueue.remove(0);
                }
            }
        }, 0,50);
    }

    private void performRotations(ArrayList<String> rot) {
        for (String r : rot) {
            if (r.length() > 1 && r.charAt(1) == '\'')
                computeState.rotate(r.charAt(0) + "", -1);
            else if (r.length() > 1 && r.charAt(1) == '2') {
                computeState.rotate(r.charAt(0) + "", 1);
                computeState.rotate(r.charAt(0) + "", 1);
            }
            else
                computeState.rotate(r, 1);
        }
    }

    public Rubix(String... args) {
        //Initialise Window
        state = new State();
        computeState = new State();

        if (args.length > 0) {
            if (args.length >= 2 && args[0].equalsIgnoreCase("--Q")) {

            }
            commandParser(args);
        } else if (args.length > 2) {
            System.out.println("INCORRECT NUMBER OF ARGUMENTS");
            System.exit(-1);
        }

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
        if (isWaiting) {
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
                if (KeyInput.wasPressed((VK_G))) {
                    renderer.toggleGridVisible();
                } else if (KeyInput.wasPressed(VK_S)) {
                    if (currentLifeCycleState == 1) {
                        currentLifeCycleState = 2;
                    }
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
                else if (KeyInput.wasPressed(VK_0)) {
                    if (currentLifeCycleState == 0) {
                        currentLifeCycleState = 1;
                    }
                }
            }
        }
        if (!arg.equals("")){
            ArrayList<String> userScramble = new ArrayList<>();
            userScramble.add(arg);
            scrambleQueue.add(userScramble);
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
        boolean canRender = false;

        while (running){
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nanoSecondsPT;
            lastTime = now;

            if (unprocessed >= 1){
                tick();
                unprocessed--;
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
            }

            if (System.currentTimeMillis() - 1000 > timer){
                timer += 1000;
            }
        }

        System.exit(0);
    }

    private void commandParser(String... scrambles) {
        int it = 0;
        for (String s : scrambles) {
            if (!s.equalsIgnoreCase("--Q")) {
                String commands[] = s.split(" ");
                boolean isValid = false;
                String valid[] = {"F", "U", "R", "L", "B", "D", "F2", "U2", "D2", "B2", "R2", "L2", "F'", "U'", "R'", "L'", "B'", "D'"};

                for (int i = 0; i < commands.length; i++) {
                    isValid = false;
                    for (int j = 0; j < valid.length; j++) {
                        if (commands[i].equals(valid[j])) {
                            isValid = true;
                            break;
                        }
                    }
                    if (!isValid) {
                        break;
                    }
                }

                if (isValid) {
                    ArrayList<String> recipe = new ArrayList<>();
                    for (int i = 0; i < commands.length; i++) {
                        if (commands[i].contains("2")) {
                            recipe.add(commands[i].charAt(0) + "");
                            recipe.add(commands[i].charAt(0) + "");
                        } else {
                            recipe.add(commands[i]);
                        }
                    }
                    scrambleQueue.add(recipe);
                } else {
                    System.out.println("ERROR - INVALID INPUT!");
                    System.exit(-1);
                }
            }
        }
    }

    private void start() {
        if (running)
            return;
        running = true;
        new Thread(this, "RubixMain-Thread").start();
    }

    public void stop() {
        if (!running)
            return;
        running = false;
    }
}