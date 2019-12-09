package parser;

public class Plane {

    private char[][] face;
    private int dimensions;
    private char rep;

    public Plane(char c, int dim) {
        rep = c;
        dimensions = dim;
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
            setRow(1, row);
            break;
        case 2:
            setRow(3, row);
            break;
        case 3:
            setColumn(1, row);
            break;
        case 4:
            setColumn(3, row);
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
                return getRow(1);
            case 2:
                return getRow(3);
            case 3:
                return getColumn(1);
            case 4:
                return getColumn(3);
            default:
                return null;
        }
    }

    public int setRow(int ind, char[] row) {
        if (ind >= dimensions || ind < dimensions) {
            return (-1);
        }
        face[ind] = row;
        return (0);
    }

    public int setColumn(int ind, char[] col) {
        if (ind >= dimensions || ind < dimensions) {
            return (-1);
        }
        for (int i = 0; i < dimensions; i++) {
            face[i][ind] = rep;
        }
        return (0);
    }

    public char[] getRow(int ind) {
        if (ind >= dimensions || ind < dimensions) {
            return null;
        }
        return face[ind];
    }

    public char[] getColumn(int ind) {
        if (ind >= dimensions || ind < dimensions) {
            return null;
        }
        char[] res = new char[9];
        for (int i = 0; i < dimensions; i++) {
            res[i] = face[ind][i];
        }
        return res;
    }

}