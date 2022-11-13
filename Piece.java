/*
 * Idea for a more efficient way to get legal moves for bishop and rook: 
 * 4 arrays for all moves in one particular direction. 
 * push to a stack so long as its null. 
 * when you hit a non-null position, if (game[pos].getColor != color)
 * then pop the stack and add it to legalMoves. reuse it for each array. 
 * 
 * 
 * this might be more efficient. it might not. idk
 */



import java.util.*;

public /*Abstract?*/ class Piece {
    public static Piece[] game = new Piece[64]; // this array will be used throughout the game to keep track of pieces. 

    // each piece has a position, color (T = white, F = black), captured (F by default), and list of legalMoves as indicies in game
    protected int position; 
    protected boolean color, captured;
    protected ArrayList<Integer> legalMoves;

    public Piece(int x, boolean c) {
        position = x;
        color = c;
        captured = false;
        legalMoves = new ArrayList<Integer>();
    }

    // this method is intentionally blank, and is overridden in all subclasses. 
    public void setLegalMoves(){}

    //accessor methods
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

    // modifiers 
    public void capture() {
        captured = true;
    }

    // if it's legal, this method moves a piece to the destination index in game[]. returns success status. 
    // this method is similar to a setPosition() method
    public boolean move(int destination) {
        setLegalMoves(); // possible source of error if this doesn't call subclass method. 
        if (legalMoves.contains(destination)) {
            position = destination;
            return true;
        }
        return false;
    }

    // transformation methods. they return mathematical transformations necessary to perform the move described in the method name
    public static int up()
    {
        return -8; 
    }
    public static int up(int x)
    {    return x*-8; } // used for setting up pieces

    public static int down()
    {
        return 8; 
    }
    public static int down(int x)
    { return x*8;}

    public int forward(int n) {
        //makes sure the move won't go off the board
        if ((color && position + n * -8 < 0) || (!color && position + n * 8 > 63))
            throw new IllegalStateException("This piece cannot move forward");
        if (color) // if its a white piece, forward is upwards on the board
            return n * -8;
        return n * 8; // if its a black piece, forward is down on the board
    }

    public int backward(int n) {
        //makes sure the move won't go off the board
        if ((!color && position + n * -8 < 0) || (color && position + n * 8 > 63))
            throw new IllegalStateException("This piece cannot move backward");
        if (color) // if its a white piece, backwards is down on the board
            return n * 8;
        return n * -8;// if its a black piece, backwards is up on the board
    }

    public static int getColumn(int n) // returns the column of an index from the array. remember, it starts with 0
    {
        return n%8;
    }

    public static int getRow(int n) // returns the row of an index in the array. remember, it starts with 0
    {
        return n/8; 
    }

    public int right(int n) {
        int start = getColumn(position); 
        int end = getColumn(position + n);
        //ensures the ending column is to the right of the starting column and they're on the same row. 
        if (end < start || Math.abs(end-start) >= 8) 
            throw new IllegalStateException("This piece cannot move to the right!");
        return n;
    }

    public int left(int n) {
        int start = getColumn(position);
        int end = getColumn(position - n);
        //ensures the ending column is to the left of the starting column and they're on the same row. 
        if (end > start || Math.abs(end-start) >= 8)
            throw new IllegalStateException("This piece cannot move to the left!");
        return -n;
    }

    // we will need to update this to ensure the moves will not place the king in check. using a stack?
    public void cleanMoves() {
        for (int x = 0; x < legalMoves.size(); x++) {
            // if a piece is in the legal move spot and that piece is the same color, remove
            // that legal move from legalMoves
            if (game[legalMoves.get(x)] != null && game[legalMoves.get(x)].getColor() == color)
                legalMoves.remove(x);
        }
    }

}
