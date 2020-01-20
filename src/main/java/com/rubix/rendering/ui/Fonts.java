package com.rubix.rendering.ui;

import com.rubix.rendering.window.Renderer;

import java.awt.*;

public class Fonts {

    public static void drawString(Graphics graphics, Font font, Color color, String text, int x, int y){

        if (x < 0) {
            drawString(graphics, font, color, text, y, false);
            return ;
        }

        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(text, x, y);
    }

    public static void drawString(Graphics graphics, Font font, Color color, String text){
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        int x = (Renderer.windowWidth - fontMetrics.stringWidth(text)) / 2; //Horizontal Center
        int y = ((Renderer.windowHeight - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent(); //Vertical Center

        drawString(graphics, font, color, text, x, y);
    }

    public static void drawString(Graphics graphics, Font font, Color color, String text, int size, boolean xY){
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int x;
        int y;
        if (xY) {
            x = size;
            y = ((Renderer.windowHeight - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent(); //Vertical
        }
        else {
            x = (Renderer.windowWidth - fontMetrics.stringWidth(text)) / 2; //Horizontal Center
            y = size;
        }
        drawString(graphics, font, color, text, x, y);
    }

}
