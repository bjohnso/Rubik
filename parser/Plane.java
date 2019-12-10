package parser;

public class Plane {

    private char[][] face;
    private int dimensions;
    private char rep;

    public Plane(char c, int dim) {
        rep = c;
        dimensions = dim;
        face = new char[3][3];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                face[i][j] = c;
            }
        }
    }

    public int setRotate(int ind, char[] row) {
        if (ind < 1 || ind > 4) {
            return (-1);
        }
        switch (ind) {
        case 1:
            setRow(0, row);
            break;
        case 2:
            setRow(2, row);
            break;
        case 3:
            setColumn(0, row);
            break;
        case 4:
            setColumn(2, row);
            break;
        }
        return (0);
    }

    public char[] getRotate(int ind) {
        if (ind < 1 || ind > 4) {
            return null;
        }
        switch (ind) {
            case 1:
                return getRow(0);
            case 2:
                return getRow(2);
            case 3:
                return getColumn(0);
            case 4:
                return getColumn(2);
            default:
                return null;
        }
    }

    public int setRow(int ind, char[] row) {
        if (ind < 0 || ind >= dimensions) {
            return (-1);
        }
        System.out.println(rep);
        face[ind] = row;
        System.out.println(face[ind]);
        return (0);
    }

    public int setColumn(int ind, char[] col) {
        if (ind < 0 || ind >= dimensions) {
            return (-1);
        }
        for (int i = 0; i < dimensions; i++) {
            face[i][ind] = col[i];
        }
        return (0);
    }

    public char[] getRow(int ind) {
        if (ind < 0 || ind >= dimensions) {
            return null;
        }
        // System.out.println(face[ind]); 
        // printFace();               
        return face[ind];
    }

    public char[] getColumn(int ind) {
        if (ind < 0 || ind >= dimensions) {
            return null;
        }
        char[] res = new char[3];
        for (int i = 0; i < dimensions; i++) {
            res[i] = face[ind][i];
        }
        System.out.println(res);
        return res;
    }

    public void printFace(){
        for (int i = 0; i < dimensions; i++){
            System.out.println(face[i]);
        }
        System.out.println("");
    }

}