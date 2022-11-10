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

    public boolean getColor() {
        return color;
    }

    public boolean getCaptured() {
        return captured;
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

    public int right(int n) {
        int start = position % 8; // gets the column of where the position is
        int end = (position + n) % 8;
        if (start == 0)
            start = 8;
        if (end == 0)
            end = 8;
        if (end < start)
            throw new IllegalStateException("This piece cannot move to the right!");
        return n;
    }

    public int left(int n) {
        int start = position % 8;
        int end = (position - n) % 8;
        if (start == 0)
            start = 8;
        if (end == 0)
            end = 8;
        if (end > start)
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
