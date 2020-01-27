package com.rubix.cube;

import java.awt.*;

public class Cubicle {
    private Node3D node3D;
    private String position;
    private String type;

    public Cubicle(String position, String type) {
        this.position = position;
        this.type = type;
        initNode();
    }

    private void initNode() {
        this.node3D = new Node3D(this.position, this.type);
        if (position.charAt(1) == '1')
            node3D.addDefaultFace("F", Color.RED);
        if (position.charAt(1) == '3')
            node3D.addDefaultFace("B", Color.MAGENTA);
        if (position.charAt(0) == '1')
            node3D.addDefaultFace("D", Color.WHITE);
        if (position.charAt(0) == '3')
            node3D.addDefaultFace("U", Color.YELLOW);
        if (position.charAt(2) == '1')
            node3D.addDefaultFace("L", Color.BLUE);
        if (position.charAt(2) == '3')
            node3D.addDefaultFace("R", Color.GREEN);
        this.node3D.setFaceMap(this.node3D.cloneDefaultFaceMap());
        this.node3D.setCurrentCubicle(position);
    }

    public Node3D getNode3D() {
        return node3D;
    }

    public String getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNode3D(Node3D node3D) {
        this.node3D = node3D;
        this.node3D.setCurrentCubicle(position);
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Cubicle clone() {
        Cubicle clone = new Cubicle(position, this.type);
        clone.setNode3D(node3D.cloneNode3D());
        return clone;
    }
}
