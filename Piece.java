/*
BLACK PIECE == FALSE COLOR, WHITE PIECE == TRUE COLOR;
*/

import java.util.*;

public class Piece {
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
