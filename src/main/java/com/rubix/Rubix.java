package com.rubix;

import com.rubix.rendering.ui.Window;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Rubix implements Runnable{

    private Window window;
    private boolean running;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Rubix());
    }

    public Rubix(){
        //Initialise Window
        this.window = new Window(this);
        start();
    }

    private void tick() {
        //Input Listeners
    }

    private void render() {
        window.render();
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