package com.rubix.artifacts;

public class Node {

    private String id;
    private String color;

    public Node(String id, String color) {
        this.id = id;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
    }
}
