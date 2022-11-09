/*
BLACK PIECE == FALSE COLOR, WHITE PIECE == TRUE COLOR;
*/

import java.util.*;

public class Piece {
    protected int x, y; // this could become a Space
    protected boolean color, captured;
    protected ArrayList<int[]> legalMoves = new ArrayList<int[]>(); // this would be ArrayList<Space>

    public Piece(int pos1, int pos2, boolean c) { // we set the space
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

    public boolean move(int a, int b) {
        int[] move = { a, b };
        if (legalMoves.contains(move)) {
            x = a;
            y = b;
            return true;
        }
        return false;
    }

    public ArrayList<int[]> getLegalMoves() {
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

    /*
     * We should have methods that return spaces for up/down/left/right that
     * we can use to set the legalMoves.
     * 
     * for example, a pawn can set legal moves with up() or up(up()) if its the
     * first move
     * a knight can set legal moves with left( up( up() ) ) and other ways too
     * 
     * of course this is only true if the spaces that those methods return are
     * empty, which we
     * can use isEmpty() from the space class to determine
     * 
     * I think this will help remove some of the manual work on us for the math
     * needed for setting legal moves.
     * This way, we can just use up, down, left, and right.
     * 
     * 
     * We will also need to be careful with the knight. Since it can jump over other
     * pieces, we will need to only
     * check isEmpty() on the final destination space, not each space to get there.
     * This is not the case with other pieces.
     *
     */

}
