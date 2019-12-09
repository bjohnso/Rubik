package parser;

import parser.Plane;

public class Cube{

    private Plane Front;
    private Plane Back;
    private Plane Up;
    private Plane Down;
    private Plane Left;
    private Plane Right;
    private int dimensions;


    public Cube (int dimension){
        dimensions = dimension;
        Front = new Plane('F', dimensions);
        Back = new Plane('B', dimensions);
        Up = new Plane('U', dimensions);
        Down = new Plane('D', dimensions);
        Left = new Plane('L', dimensions);
        Right = new Plane('R', dimensions);
    }
    
    public int readInstructions(String instructions){
        for (String temp : instructions.split(" ")){
            switch(temp){
                
                case "U":
                    break;
            }
        }
        return (0);
    }
}