/*
BLACK PIECE == FALSE COLOR, WHITE PIECE == TRUE COLOR;
*/

import java.util.*;

public class Piece {
    int x, y;
    boolean color, captured;
    ArrayList<Arrays> legalMoves = new ArrayList<Arrays>();

    public Piece(int pos1, int pos2, boolean c) {
        x = pos1;
        y = pos2;
        color = c;
        captured = false;
    }

    public Piece() {
        x = 0;
        y = 0;
        color = false;
        captured = false;
    }

    public ArrayList<Arrays> getLegalMoves() {
        return legalMoves;
    }

    public int[] getPosition() {
        int[] pos = { x, y };
        return pos;
    }

    public boolean getColor() {
        return color;
    }

    public boolean getCaptured() {
        return captured;
    }

}
