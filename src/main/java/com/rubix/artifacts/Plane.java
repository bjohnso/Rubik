package com.rubix.artifacts;

import java.awt.*;

public class Plane {

    private String alias;
    private String color;
    private String polar;
    private Node nodes[][];

    public Plane(String alias, String color, String polar){
        setAlias(alias);
        setColor(color);
        setPolar(polar);
        initNodes();
    }

    public void initNodes() {
        Node temp[][] = {
                {new Node("DL", this.getColor()), new Node("DM", this.getColor()), new Node("DR", this.getColor())},
                {new Node("ML", this.getColor()), new Node("MM", this.getColor()), new Node("MR", this.getColor())},
                {new Node("UL", this.getColor()), new Node("UM", this.getColor()), new Node("UR", this.getColor())}
        };
        nodes = temp;
    }

    public void setPolar(String polar) {
        this.polar = polar;
    }

    public String getPolar() {
        return polar;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlias() {
        return alias;
    }

    public String getColor() {
        return color;
    }

    public Node[][] getNodes() {
        return nodes;
    }
}
