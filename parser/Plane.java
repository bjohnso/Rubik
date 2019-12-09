package parser;

public class Plane{

    private char[][] face;
    private int dimensions;
    private char rep;
    
    public Plane(char c, int dim){
        rep = c;
        dimensions = dim;
        for (int i = 0; i < dim ; i ++){
            for (int j = 0; j < dim ; j++){
                face[i][j] = c;
            }
        }
    }

    public int setRow(int ind, char[] row){
        if (ind >= dimensions || ind < dimensions){
            return (-1); 
        }
        face[ind] = row;
        return (0);
    }

    public int setColumn(int ind, char[] col){
        if (ind >= dimensions || ind < dimensions){
            return (-1); 
        }
        for (int i = 0; i < dimensions; i++){
            face[i][ind] = rep;
        }
        return (0);
    }

    public char[] getRow(int ind){
        if (ind >= dimensions || ind < dimensions){
            return null; 
        }
        return face[ind];
    }

    public char[] getColumn(int ind){
        if (ind >= dimensions || ind < dimensions){
            return null; 
        }
        char [] res = new char[9];
        for (int i = 0; i < dimensions ; i++){
            res[i] = face[ind][i];
        }
        return res;
    }


}