package com.rubix.artifacts;

import java.awt.*;

public class Node {

    private String id;
    private Color color;
    private String type;

    public Node(String id, Color color, String type) {
        this.id = id;
        this.color = color;
        this.type = type;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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
