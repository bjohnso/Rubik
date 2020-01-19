package com.rubix.rendering.ui;

import com.rubix.Rubix;
import com.rubix.artifacts.Node;
import com.rubix.artifacts.Plane;
import com.rubix.artifacts.State;
import com.rubix.input.KeyInput;
import com.rubix.rendering.textures.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import static java.awt.event.KeyEvent.*;

public class Window extends Canvas {

    public static final String TITLE = "RUBIX";
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = screenSize.width / 2;
    public static final int HEIGHT = screenSize.height /2;

    private int cube[][][] = new int[9][2][2];
    private int plain_width = ((Window.WIDTH / 2) - (Window.WIDTH / 3));
    private int node_height = ((Window.HEIGHT / 100 * 100) - (Window.HEIGHT / 100 * 60)) / 3;
    private int node_width = plain_width / 3;

    private JFrame frame;

    public void render(State state){
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(2);
            return ;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        /*Texture background = new Texture("backgrounds/psy1", Window.WIDTH, Window.HEIGHT, false);
        background.render(graphics, 0, 0);*/

        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, Window.HEIGHT / 100 * 10), Color.GREEN, Window.TITLE,
                Window.HEIGHT / 100 * 10, false);
        initCubePoints();

        graphics.setColor(Color.GRAY);
        for (int i = 0; i < cube.length; i++) {
            graphics.drawLine(cube[i][0][0], cube[i][0][1], cube[i][1][0], cube[i][1][1]);
        }

        renderPlanes(graphics, state);
        renderTelemetry(graphics, state);

        graphics.dispose();
        bufferStrategy.show();
    }

    public void renderTelemetry(Graphics graphics, State state) {
        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, Window.HEIGHT / 100 * 5), Color.GREEN,
                "SCRAMBLE : ", (Window.WIDTH / 100 * 3), (Window.HEIGHT / 100 * 20));
        int newLine = 0;
        int space = -1;
        for (int i = 0; i < state.getScramble().length(); i++) {
            if (i % 5 == 0 && i > 4) {
                newLine++;
                space = -1;
            }
            Fonts.drawString(graphics, new Font("Arial", Font.BOLD, Window.HEIGHT / 100 * 4), Color.GREEN,
                    state.getScramble().charAt(i) + "", (Window.WIDTH / 100 * 3) + (Window.WIDTH / 100 * 3 * ++space),
                    (Window.HEIGHT / 100 * 25) + (Window.HEIGHT / 100 * 5 * newLine));
        }
    }

    public void renderPlanes(Graphics graphics, State state) {
        //FRONT
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("F").getNodes()[i][0].getColor());
                graphics.drawLine((Window.WIDTH / 3), ((Window.HEIGHT / 100 * 80) - j) - (node_height * i),
                        (Window.WIDTH / 3) + (node_width), ((Window.HEIGHT / 100 * 80) + ((node_height / 2) - j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("F").getNodes()[i][1].getColor());
                graphics.drawLine((Window.WIDTH / 3 + (node_width)), ((Window.HEIGHT / 100 * 80) + ((node_height / 2) - j)) - (node_height * i),
                        (Window.WIDTH / 3) + (node_width * 2), ((Window.HEIGHT / 100 * 80) + (node_height - j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("F").getNodes()[i][2].getColor());
                graphics.drawLine((Window.WIDTH / 3 + (node_width * 2)), ((Window.HEIGHT / 100 * 80) + ((node_height - j))) - (node_height * i),
                        (Window.WIDTH / 3) + (node_width * 3), (Window.HEIGHT / 100 * 80) + ((node_height / 2 * 3) - j) - (node_height * i));
            }
        }
        //RIGHT
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("R").getNodes()[i][0].getColor());
                graphics.drawLine((Window.WIDTH / 2), ((Window.HEIGHT / 100 * 100) - j) - (node_height * i),
                        (Window.WIDTH / 2) + (node_width), ((Window.HEIGHT / 100 * 100) - ((node_height / 2) + j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("R").getNodes()[i][1].getColor());
                graphics.drawLine((Window.WIDTH / 2 + (node_width)), ((Window.HEIGHT / 100 * 100) - ((node_height / 2) + j)) - (node_height * i),
                        (Window.WIDTH / 2) + (node_width * 2), ((Window.HEIGHT / 100 * 100) - ((node_height) + j)) - (node_height * i));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("R").getNodes()[i][2].getColor());
                graphics.drawLine((Window.WIDTH / 2 + (node_width * 2)), ((Window.HEIGHT / 100 * 100) - ((node_height) + j)) - (node_height * i),
                        (Window.WIDTH / 2) + (node_width * 3), ((Window.HEIGHT / 100 * 100) - ((node_height / 2 * 3) + j)) - (node_height * i));
            }
        }
        //UP
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("U").getNodes()[i][0].getColor());
                graphics.drawLine((Window.WIDTH / 3) + (j) + (i * node_width) , ((Window.HEIGHT / 100 * 40)) + (j /  2) - ((i * (node_height) / 2)),
                        (Window.WIDTH / 3) + (node_width) + (j) + (i * node_width), (Window.HEIGHT / 100 * 40) - ((node_width / 2)) + (j / 2) - ((i * (node_height)) / 2));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("U").getNodes()[i][1].getColor());
                graphics.drawLine((Window.WIDTH / 3) + (node_width) + (j) + (i * node_width), ((Window.HEIGHT / 100 * 40)) + (node_width / 2) + (j /  2) - ((i * (node_height) / 2)),
                        (Window.WIDTH / 3) + (node_width * 2) + (j) + (i * node_width), (Window.HEIGHT / 100 * 40) + (j / 2) - ((i * (node_height) / 2)));
            }
            for (int j = 0; j < node_width; j++) {
                graphics.setColor(state.getPlaneMap().get("U").getNodes()[i][2].getColor());
                graphics.drawLine((Window.WIDTH / 3) + (node_width * 2) + (j) + (i * node_width), ((Window.HEIGHT / 100 * 40)) + (node_width) + (j /  2) - ((i * (node_height) / 2)),
                    (Window.WIDTH / 3) + (node_width * 3) + (j) + (i * node_width), (Window.HEIGHT / 100 * 40) + (node_width / 2) + (j / 2) - ((i * (node_height) / 2)));
            }
        }
    }

    public void initCubePoints () {
        int temp[][][] = {
                {{Window.WIDTH / 3, Window.HEIGHT / 100 * 80}, {Window.WIDTH / 3, Window.HEIGHT / 100 * 40}},
                {{Window.WIDTH / 3, Window.HEIGHT / 100 * 80}, {Window.WIDTH / 2, Window.HEIGHT / 100 * 100}},
                {{Window.WIDTH / 2, Window.HEIGHT / 100 * 100}, {Window.WIDTH / 3 * 2, Window.HEIGHT / 100 * 80}},
                {{Window.WIDTH / 3 * 2, Window.HEIGHT / 100 * 80}, {Window.WIDTH / 3 * 2, Window.HEIGHT / 100 * 40}},
                {{Window.WIDTH / 3 * 2, Window.HEIGHT / 100 * 40}, {Window.WIDTH / 2, Window.HEIGHT / 100 * 60}},
                {{Window.WIDTH / 2, Window.HEIGHT / 100 * 60}, {Window.WIDTH / 3, Window.HEIGHT / 100 * 40}},
                {{Window.WIDTH / 3, Window.HEIGHT / 100 * 40}, {Window.WIDTH / 2, Window.HEIGHT / 100 * 20}},
                {{Window.WIDTH / 2, Window.HEIGHT / 100 * 20}, {Window.WIDTH / 3 * 2, Window.HEIGHT / 100 * 40}},
                {{Window.WIDTH / 2, Window.HEIGHT / 100 * 60}, {Window.WIDTH / 2, Window.HEIGHT / 100 * 100}}
        };
        cube = temp;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Window(final Rubix rubix){
        //Frame
        frame = new JFrame(TITLE);
        frame.add(this);
        frame.setSize(WIDTH, HEIGHT);
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
