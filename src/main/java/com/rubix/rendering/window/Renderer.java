package com.rubix.rendering.window;

import com.rubix.Rubix;
import com.rubix.cube.State;
import com.rubix.rendering.artificats.Point;
import com.rubix.rendering.ui.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedHashMap;

public class Renderer extends Canvas {

    //ABSOLUTES
    private static final String TITLE = "RUBIX";
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //DIMENSION SPECIFICS
    private LinkedHashMap<String, Point> framePoints;
    private LinkedHashMap<String, Point> cubePoints;

    public static final int windowHeight = screenSize.height / 2;
    public static final int windowWidth = screenSize.width / 2;
    private int height_padding = (windowHeight / 10) / 2;
    private int width_padding = (windowWidth / 10) / 2;
    private int frameHeight = windowHeight / 100 * 90;
    private int frameWidth = windowWidth / 100 * 90;
    private int gridSize = 16;

    private JFrame frame;
    private boolean gridVisible = false;

    public void render(State state){
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(2);
            return ;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, windowWidth, windowHeight);
        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 10), Color.GREEN, TITLE,
                windowHeight / 100 * 10, false);

        if (gridVisible)
            drawFrame(graphics);
        drawCube(graphics);
        if (state.getCube() != null)
            renderPlanes(graphics, state);
        renderTelemetry(graphics, state);
        graphics.dispose();
        bufferStrategy.show();
    }

    //Scales a render frame within the Window, to dimensions of perfect square
    private void castFramePoints() {
        height_padding = (windowHeight / 10) / 2;
        width_padding = (windowWidth / 10) / 2;
        frameHeight = windowHeight / 100 * 90;
        frameWidth = windowWidth / 100 * 90;
        if (frameHeight > frameWidth){
            height_padding += ((frameHeight - frameWidth) / 2);
        } else if (frameHeight < frameWidth ){
            width_padding += ((frameWidth - windowHeight) / 2);
        }

        framePoints = new LinkedHashMap<>();

        framePoints.put("left_height", new Point(width_padding, height_padding, 0));
        framePoints.put("right_height", new Point(windowWidth - (width_padding), height_padding, 0));
        framePoints.put("right_depth", new Point(windowWidth - (width_padding), windowHeight - (height_padding), 0));
        framePoints.put("left_depth", new Point(width_padding, windowHeight - (height_padding), 0));
    }

    public void drawCube(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        //DrawCubeFrame
        graphics.drawLine(cubePoints.get("left_height").getX(), cubePoints.get("left_height").getY(), cubePoints.get("center_mid").getX(), cubePoints.get("center_mid").getY());
        graphics.drawLine(cubePoints.get("center_mid").getX(), cubePoints.get("center_mid").getY(), cubePoints.get("right_height").getX(), cubePoints.get("right_height").getY());
        graphics.drawLine(cubePoints.get("right_height").getX(), cubePoints.get("right_height").getY(), cubePoints.get("center_height").getX(), cubePoints.get("center_height").getY());
        graphics.drawLine(cubePoints.get("center_height").getX(), cubePoints.get("center_height").getY(), cubePoints.get("left_height").getX(), cubePoints.get("left_height").getY());

        graphics.drawLine(cubePoints.get("center_mid").getX(), cubePoints.get("center_mid").getY(), cubePoints.get("center_depth").getX(), cubePoints.get("center_depth").getY());

        graphics.drawLine(cubePoints.get("left_height").getX(), cubePoints.get("left_height").getY(), cubePoints.get("left_depth").getX(), cubePoints.get("left_depth").getY());
        graphics.drawLine(cubePoints.get("left_depth").getX(), cubePoints.get("left_depth").getY(), cubePoints.get("center_depth").getX(), cubePoints.get("center_depth").getY());
        graphics.drawLine(cubePoints.get("center_depth").getX(), cubePoints.get("center_depth").getY(), cubePoints.get("right_depth").getX(), cubePoints.get("right_depth").getY());
        graphics.drawLine(cubePoints.get("right_depth").getX(), cubePoints.get("right_depth").getY(), cubePoints.get("right_height").getX(), cubePoints.get("right_height").getY());
    }

    public void drawFrame(Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        int i = (framePoints.get("right_height").getX() - framePoints.get("left_height").getX()) / gridSize;
        for (int j = 0; j < framePoints.get("right_height").getX() - framePoints.get("left_height").getX(); j += i) {
            graphics.drawLine(framePoints.get("left_height").getX() + j, framePoints.get("left_height").getY(), framePoints.get("left_depth").getX() + j, framePoints.get("left_depth").getY());
        }

        i = (framePoints.get("left_depth").getY() - framePoints.get("left_height").getY()) / gridSize;
        for (int j = 0; j < framePoints.get("left_depth").getY() - framePoints.get("left_height").getY(); j += i) {
            graphics.drawLine(framePoints.get("left_height").getX(), framePoints.get("left_height").getY() + j, framePoints.get("right_height").getX(), framePoints.get("right_height").getY() + j);
        }
    }

    public void castCubePoints() {
        int i;
        int c;

        cubePoints = new LinkedHashMap<>();
        cubePoints.put("center_height", new Point());
        cubePoints.put("center_mid", new Point());
        cubePoints.put("center_depth", new Point());
        cubePoints.put("left_height", new Point());
        cubePoints.put("left_depth", new Point());
        cubePoints.put("right_height", new Point());
        cubePoints.put("right_depth", new Point());

        i = (framePoints.get("right_height").getX() - framePoints.get("left_height").getX()) / gridSize;
        c = 1;
        for (int j = 0; j < framePoints.get("right_height").getX() - framePoints.get("left_height").getX(); j += i) {
            if (c == (gridSize / 4) + 1) {
                cubePoints.get("left_height").setX(framePoints.get("left_height").getX() + j);
                cubePoints.get("left_depth").setX(framePoints.get("left_height").getX() + j);
            } else if (c == (gridSize / 4 * 3) + 1) {
                cubePoints.get("right_height").setX(framePoints.get("left_height").getX() + j);
                cubePoints.get("right_depth").setX(framePoints.get("left_height").getX() + j);
            } else if (c == (gridSize / 2) + 1) {
                cubePoints.get("center_height").setX(framePoints.get("left_height").getX() + j);
                cubePoints.get("center_mid").setX(framePoints.get("left_height").getX() + j);
                cubePoints.get("center_depth").setX(framePoints.get("left_height").getX() + j);
            }
            c++;
        }

        i = (framePoints.get("left_depth").getY() - framePoints.get("left_height").getY()) / gridSize;
        c = 1;
        for (int j = 0; j < framePoints.get("left_depth").getY() - framePoints.get("left_height").getY(); j += i) {
            if (c == gridSize / 16 * 4) {
                cubePoints.get("center_height").setY(framePoints.get("left_height").getY() + j);
            } else if (c == gridSize / 16 * 7) {
                cubePoints.get("left_height").setY(framePoints.get("left_height").getY() + j);
                cubePoints.get("right_height").setY(framePoints.get("left_height").getY() + j);
            } else if (c == gridSize / 16 * 10) {
                cubePoints.get("center_mid").setY(framePoints.get("left_height").getY() + j);
            } else if (c == gridSize / 16 * 13){
                cubePoints.get("left_depth").setY(framePoints.get("left_height").getY() + j);
                cubePoints.get("right_depth").setY(framePoints.get("left_height").getY() + j);
            } else if (c == gridSize) {
                cubePoints.get("center_depth").setY(framePoints.get("left_height").getY() + j);
            }
            c++;
        }
    }

    public void renderTelemetry(Graphics graphics, State state) {
        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 4), Color.RED,
                "ROTATION : ", (windowWidth / 100 * 3), (windowHeight / 100 * 20));

        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 10), Color.RED,
                state.getLastRotation(), (windowWidth / 100 * 3) + (windowWidth / 100 * 3),
                (windowHeight / 100 * 30) + (windowHeight / 100 * 5));

        /*int newLine = 0;
        int space = -1;*/
        /*for (int i = 0; i < state.getScramble().size(); i++) {
            if (i % 5 == 0 && i > 4) {
                newLine++;
                space = -1;
            }
            Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 3), Color.RED,
                    state.getScramble().get(i), (windowWidth / 100 * 3) + (windowWidth / 100 * 3 * ++space),
                    (windowHeight / 100 * 25) + (windowHeight / 100 * 5 * newLine));
        }*/

        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 4), Color.GREEN,
                "SOLVE : ", (windowWidth / 100 * 3), (windowHeight / 100 * 60));
        for (int i = 0; i < state.getSolve().size(); i++) {
            Fonts.drawString(graphics, new Font("Arial", Font.BOLD, windowHeight / 100 * 3), Color.GREEN,
                    state.getSolve().get(i) + "", (windowWidth / 100 * 3),
                    (windowHeight / 100 * 65) + (windowHeight / 100 * 5 * i));
        }
    }

    public void renderPlanes(Graphics graphics, State state) {
        int plane_width = cubePoints.get("center_depth").getX() - cubePoints.get("left_depth").getX();
        int plane_height = cubePoints.get("left_depth").getY() - cubePoints.get("left_height").getY();
        int node_width = plane_width / 3;
        int node_height = plane_height / 3;

        //FRONT
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "11").getNode3D().getFace("F"));
                graphics.drawLine(cubePoints.get("left_depth").getX(), (cubePoints.get("left_depth").getY() - j) - (node_height * i),
                        cubePoints.get("left_depth").getX() + (node_width), (cubePoints.get("left_depth").getY() + ((node_height / 2) - j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "12").getNode3D().getFace("F"));
                graphics.drawLine(cubePoints.get("left_depth").getX() + (node_width), (cubePoints.get("left_depth").getY() + ((node_height / 2) - j)) - (node_height * i),
                        cubePoints.get("left_depth").getX() + (node_width * 2), (cubePoints.get("left_depth").getY() + (node_height - j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "13").getNode3D().getFace("F"));
                graphics.drawLine((cubePoints.get("left_depth").getX() + (node_width * 2)), (cubePoints.get("left_depth").getY() + ((node_height - j))) - (node_height * i),
                        cubePoints.get("left_depth").getX() + (node_width * 3), cubePoints.get("left_depth").getY() + ((node_height / 2 * 3) - j) - (node_height * i));
            }
        }
        //RIGHT
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "13").getNode3D().getFace("R"));
                graphics.drawLine(cubePoints.get("center_depth").getX(), (cubePoints.get("center_depth").getY() - j) - (node_height * i),
                        cubePoints.get("center_depth").getX() + (node_width), (cubePoints.get("center_depth").getY() - ((node_height / 2) + j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "23").getNode3D().getFace("R"));
                graphics.drawLine((cubePoints.get("center_depth").getX() + (node_width)), (cubePoints.get("center_depth").getY() - ((node_height / 2) + j)) - (node_height * i),
                        cubePoints.get("center_depth").getX() + (node_width * 2), (cubePoints.get("center_depth").getY() - ((node_height) + j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get((i + 1) + "33").getNode3D().getFace("R"));
                graphics.drawLine((cubePoints.get("center_depth").getX() + (node_width * 2)), (cubePoints.get("center_depth").getY() - ((node_height) + j)) - (node_height * i),
                        cubePoints.get("center_depth").getX() + (node_width * 3), (cubePoints.get("center_depth").getY() - ((node_height / 2 * 3) + j)) - (node_height * i));
            }
        }
        //UP
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get("3" + (i + 1) + "1").getNode3D().getFace("U"));
                graphics.drawLine(cubePoints.get("left_height").getX() + (j) + (i * node_width) , (cubePoints.get("left_height").getY()) + (j / 8 * 5) - ((i * (node_height) / 2)),
                        cubePoints.get("left_height").getX() + (node_width) + (j) + (i * node_width), cubePoints.get("left_height").getY() - ((node_height / 2)) + (j / 8 * 5) - ((i * (node_height)) / 2));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get("3" + (i + 1) + "2").getNode3D().getFace("U"));
                graphics.drawLine(cubePoints.get("left_height").getX() + (node_width) + (j) + (i * node_width), cubePoints.get("left_height").getY() + (node_height / 2) + (j / 8 * 5) - ((i * (node_height) / 2)),
                        cubePoints.get("left_height").getX() + (node_width * 2) + (j) + (i * node_width), cubePoints.get("left_height").getY() + (j / 8 * 5) - ((i * (node_height) / 2)));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getCube().get("3" + (i + 1) + "3").getNode3D().getFace("U"));
                graphics.drawLine(cubePoints.get("left_height").getX() + (node_width * 2) + (j) + (i * node_width), cubePoints.get("left_height").getY() + (node_height) + (j / 8 * 5) - ((i * (node_height) / 2)),
                        cubePoints.get("left_height").getX() + (node_width * 3) + (j) + (i * node_width), cubePoints.get("left_height").getY() + (node_height / 2) + (j / 8 * 5) - ((i * (node_height) / 2)));
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void toggleGridVisible() {
        this.gridVisible = gridVisible;
        if (this.gridVisible == true) {
            this.gridVisible = false;
        } else {
            this.gridVisible = true;
        }
    }

    public Renderer(final Rubix rubix){
        //Frame
        castFramePoints();
        castCubePoints();
        frame = new JFrame(TITLE);
        frame.add(this);
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.err.println("Exiting Rubix");
                rubix.stop();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
