/*
BLACK PIECE == FALSE COLOR, WHITE PIECE == TRUE COLOR;
*/

import java.util.*;

public class Piece {
    public static Piece[] game = new Piece[64];

    protected int position;
    protected boolean color, captured;
    protected ArrayList<Integer> legalMoves;

    public Piece(int x, boolean c) {
        position = x;
        color = c;
        captured = false;
        legalMoves = new ArrayList<Integer>();
    }

    public boolean move(int destination) {
        if (legalMoves.contains(destination)) {
            position = destination;
            return true;
        }
        return false;
    }

    public ArrayList<Integer> getLegalMoves() {
        return legalMoves;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(int x) {
        position = x;
    }

    public boolean getColor() {
        return color;
    }

    public boolean getCaptured() {
        return captured;
    }

    public void capture() {
        captured = true;
    }

    public static int up()
    {
        return -8; 
    }

    public static int down()
    {
        return 8; 
    }
    
    public int forward(int n) {
        if ((color && position + n * -8 < 1) || (!color && position + n * 8 > 64))
            throw new IllegalStateException("This piece cannot move forward");

        if (color) // if its a white piece, forward is upwards on the board
            return n * -8;
        return n * 8; // if its a black piece, forward is down on the board
    }

    public int backward(int n) {
        if ((!color && position + n * -8 < 1) || (color && position + n * 8 > 64))
            throw new IllegalStateException("This piece cannot move backward");
        if (color) // if its a white piece, backwards is down on the board
            return n * 8;
        return n * -8;// if its a black piece, backwards is up on the board
    }

    public static int getColumn(int n) // returns the column of an index from the array
    {
        int column = n%8; 
        if (column == 0)
            column = 8; 
        return column;
    }

    public static int getRow(int n)
    {
        int row = (n-1)/8+1;
        return row; 
    }

    public int right(int n) {
        int start = getColumn(position); 
        int end = getColumn(position + n);
        if (end < start) // if the ending column is to the left of the starting column, throw exception
            throw new IllegalStateException("This piece cannot move to the right!");
        return n;
    }

    public int left(int n) {
        int start = getColumn(position);
        int end = getColumn(position - n);
        if (end > start) // if the ending column is to the right of the starting column, throw exception
            throw new IllegalStateException("This piece cannot move to the left!");
        return -n;
    }

    public void cleanMoves() {
        for (int x = 0; x < legalMoves.size(); x++) {
            // if a piece is in the legal move spot and that piece is the same color, remove
            // that legal move from legalMoves
            if (game[legalMoves.get(x)] != null && game[legalMoves.get(x)].getColor() == color)
                legalMoves.remove(x);

        }
    }

}
