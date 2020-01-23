package com.rubix.artifacts;

import java.awt.*;
import java.util.HashMap;

public class Plane {

    private String alias;
    private Color color;
    private String polar;
    private String rule;
    private Node nodes[][];

    public Plane(String alias, Color color, String rule, String polar){
        setAlias(alias);
        setColor(color);
        setPolar(polar);
        initNodes();
    }

    public void initNodes() {
        Node temp[][] = {
                {new Node("DL", this.getColor(), "CORNER"), new Node("DM", this.getColor(), "EDGE"), new Node("DR", this.getColor(), "CORNER")},
                {new Node("ML", this.getColor(), "EDGE"), new Node("MM", this.getColor(), "MIDDLE"), new Node("MR", this.getColor(), "EDGE")},
                {new Node("UL", this.getColor(), "CORNER"), new Node("UM", this.getColor(), "EDGE"), new Node("UR", this.getColor(),"CORNER")}
        };

        for (Node[] nodes : temp){
            for (Node n : nodes){
                n.setParentAlias(alias);
            }
        }
        nodes = temp;
    }

    public void insertStem(Node[] stem, int x, int y, int z) {
        if (z == 0) {
            if (y > 1) {
                this.nodes[x][y] = stem[0];
                this.nodes[x][y - 1] = stem[1];
                this.nodes[x][y - 2] = stem[2];
            } else {
                this.nodes[x][y] = stem[0];
                this.nodes[x][y + 1] = stem[1];
                this.nodes[x][y + 2] = stem[2];
            }
        }
        else {
            if (x > 1) {
                this.nodes[x][y] = stem[0];
                this.nodes[x - 1][y] = stem[1];
                this.nodes[x - 2][y] = stem[2];
            } else {
                this.nodes[x][y] = stem[0];
                this.nodes[x + 1][y] = stem[1];
                this.nodes[x + 1][y] = stem[2];
            }
        }
    }

    public Node[] cloneStem(int x, int y, int z) {
        Node stem[] = new Node[3];

        if (z == 0) {
            if (y > 1){
                stem[0] = nodes[x][y];
                stem[1] = nodes[x][y - 1];
                stem[2] = nodes[x][y - 2];
            } else {
                stem[0] = nodes[x][y];
                stem[1] = nodes[x][y + 1];
                stem[2] = nodes[x][y + 2];
            }
        } else  {
            if (x > 1){
                stem[0] = nodes[x][y];
                stem[1] = nodes[x - 1][y];
                stem[2] = nodes[x - 2][y];
            } else {
                stem[0] = nodes[x][y];
                stem[1] = nodes[x + 1][y];
                stem[2] = nodes[x + 2][y];
            }
        }

        return stem;
    }

    public void rotatePlane(int direction) {

        if (direction > 0) {
            Node[] u = cloneStem(2, 0, 0);
            Node[] r = cloneStem(0, 2, 1);
            Node[] d = cloneStem(0, 0, 0);
            Node[] l = cloneStem(0, 0, 1);

            insertStem(u, 2, 2, 1);
            insertStem(r, 0, 0, 0);
            insertStem(d, 2, 0, 1);
            insertStem(l, 2, 0, 0);
        } else {
            Node[] u = cloneStem(2, 2, 0);
            Node[] l = cloneStem(2, 0, 1);
            Node[] d = cloneStem(0, 2, 0);
            Node[] r = cloneStem(2, 2, 1);

            insertStem(u, 2, 0, 1);
            insertStem(l, 0, 0, 0);
            insertStem(d, 2, 2, 1);
            insertStem(r, 2, 0, 0);
        }
    }

    public void updateNodes(){
        for (Node nodes[] : nodes){
            for (Node n : nodes){
                n.setCurrentPlaneAlias(alias);
            }
        }
    }

    public void setRule(String rule) {
        this.rule = rule;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public String getAlias() {
        return alias;
    }

    public Color getColor() {
        return color;
    }

    public String getRule() {
        return rule;
    }

    public Node[][] getNodes() {
        return nodes;
    }

    public void printPlane() {
        System.out.println(this.getAlias());
        for (int i = nodes.length - 1; i >= 0; i--){
            for (int j = 0; j < nodes[i].length; j++){
                System.out.print(nodes[i][j].getColor() + ", ");
            }
            System.out.print("\n");
        }
    }
}
