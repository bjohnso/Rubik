package com.rubix.rendering.ui;

import com.rubix.Rubix;
import com.rubix.artifacts.Plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Window extends Canvas {

    public static final String TITLE = "RUBIX";
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = screenSize.width / 2;
    public static final int HEIGHT = screenSize.height /2;
    private int cube[][][] = new int[9][2][2];

    int plain_width = ((Window.WIDTH / 2) - (Window.WIDTH / 3));
    int node_size = ((Window.HEIGHT / 100 * 100) - (Window.HEIGHT / 100 * 60)) / 3;

    private JFrame frame;

    public void render(){
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(2);
            return ;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
        Fonts.drawString(graphics, new Font("Arial", Font.BOLD, Window.HEIGHT / 100 * 10), Color.GREEN, Window.TITLE, Window.HEIGHT / 100 * 10, false);
        initCubePoints();

        for (int i = 0; i < cube.length; i++) {
            graphics.drawLine(cube[i][0][0], cube[i][0][1], cube[i][1][0], cube[i][1][1]);
        }

        printPlane(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public void printPlane(Graphics graphics) {
        /*if(plane.getAlias() == "F"){
            for (int i = 0; i < plane.getNodes().length; i++){
                for (int j = 0; j < )
            }
        }*/
        graphics.setColor(Color.RED);
            for (int j = 0; j < node_size; j++){
                graphics.drawLine((Window.WIDTH / 3), (Window.HEIGHT / 100 * 80) - j,
                        (Window.WIDTH / 3) + (node_size), (Window.HEIGHT / 100 * 80) + ((node_size) - j));
            }
        graphics.setColor(Color.BLUE);
        for (int j = 0; j < node_size; j++){
            graphics.drawLine((Window.WIDTH / 3), (Window.HEIGHT / 100 * 80) - j,
                    (Window.WIDTH / 3) + (node_size * 2), (Window.HEIGHT / 100 * 80) + ((node_size) - j));
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
