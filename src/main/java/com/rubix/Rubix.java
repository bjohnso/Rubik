package com.rubix;

import com.rubix.artifacts.State;
import com.rubix.input.KeyInput;
import com.rubix.rendering.window.Renderer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_R;

public class Rubix implements Runnable{

    private Renderer renderer;
    private boolean running;
    private State state;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Rubix());
    }

    public Rubix(){
        //Initialise Window
        state = new State();
        this.renderer = new Renderer(this);
        start();
    }

    private void tick() {
        //Input Listeners
        KeyInput keyInput = new KeyInput();
        renderer.addKeyListener(keyInput);
        renderer.getFrame().addKeyListener(keyInput);

        if (KeyInput.wasPressed(VK_F)){
            state.permutate("F");
            state.scrambleAdd("F");
        }

        if (KeyInput.wasPressed(VK_B)){
            state.permutate("B");
            state.scrambleAdd("B");
        }

        if (KeyInput.wasPressed(VK_U)){
            state.permutate("U");
            state.scrambleAdd("U");
        }

        if (KeyInput.wasPressed(VK_D)){
            state.permutate("D");
            state.scrambleAdd("D");
        }

        if (KeyInput.wasPressed(VK_L)){
            state.permutate("L");
            state.scrambleAdd("L");
        }

        if (KeyInput.wasPressed(VK_R)){
            state.permutate("R");
            state.scrambleAdd("R");
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
                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
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