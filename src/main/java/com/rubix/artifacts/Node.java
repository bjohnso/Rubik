package com.rubix.artifacts;

import java.awt.*;

public class Node {

    private String id;
    private Color color;

    public Node(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
    }
}
