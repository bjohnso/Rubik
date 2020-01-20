package com.rubix.rendering.runes;

public class Point {

    private int x;
    private int y;
    private int m;

    //The parameters x and y define this point's coordinates in space
    //The parameter m defines the nature of this point's connection to successive points
    //This process is know as Pathing
    public Point(int x, int y, int m){
        this.x = x;
        this.y = y;
        this.m = m;
    }

    public Point(){
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getM() {
        return m;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setM(int m) {
        this.m = m;
    }

    public String toString(){
        return "X : " + x + " | Y : " + y + " | M : " + m;
    }
}
