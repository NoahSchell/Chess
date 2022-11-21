import java.util.*;
import javax.swing.*;

public /* Abstract? */ class Piece {
    public static Piece[] game = new Piece[64]; // this array will be used throughout the game to keep track of pieces.

    // each piece has a position, color (T = white, F = black), captured (F by
    // default), and list of legalMoves as indicies in game
    protected int position;
    protected boolean color, captured;
    protected static boolean turn = true; // white goes first
    protected ArrayList<Integer> legalMoves;
    protected ImageIcon image;

    public Piece(int x, boolean c) {
        position = x;
        color = c;
        captured = false;
        legalMoves = new ArrayList<Integer>();
    }
    
    public static boolean getTurn()
    {
        return turn;
    }

    // this method is intentionally blank, and is overridden in all subclasses.
    public void setLegalMoves() {
    }

    public ImageIcon getImage()
    {
        return image;
    }

    // accessor methods
    public ArrayList<Integer> getLegalMoves() {
        legalMoves.clear(); // clear everything
        setLegalMoves(); // get everything
        cleanMoves(); // make sure its good
        return legalMoves;
    }
    
    public void removeMove(int x)
    {
        legalMoves.remove(x);
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

    // if it's legal, this method moves a piece to the destination index in game[].
    // returns success status.
    // this method is similar to a setPosition() method
    public boolean move(int destination) {
        setLegalMoves(); // possible source of error if this doesn't call subclass method.
        if (getColor() != turn) // if the piece you want to move isn't turn, get out of here
            return false; 
        if (legalMoves.contains(destination)) { // if the destination is a legal move
            game[destination] = game[position]; // set the piece at destination = piece at 
            game[position] = null; // set old spot to null because nothing is there
            position = destination; // update position variable for the piece to be destination
            turn = !turn; // changes which sides turn it is
            if (turn)
            {
                ChessBoard.updateThreads(true);
            }
            else
            {
                ChessBoard.updateThreads(false);
            }
            
            if (this instanceof King || this instanceof Rook)
                this.setCanCastle(false);
            return true;
        }
        return false;
    }

    public void setCanCastle(boolean a)
    {
        //blank for the king and rook class
    }
    // transformation methods. they return mathematical transformations necessary to
    // perform the move described in the method name
    public static int up() {
        return -8;
    }

    public static int up(int x) {
        return x * -8;
    } // used for setting up pieces

    public static int down() {
        return 8;
    }

    public static int down(int x) {
        return x * 8;
    }

    public int forward(int n) {
        // makes sure the move won't go off the board
        if ((color && position + n * -8 < 0) || (!color && position + n * 8 > 63))
            throw new IllegalStateException("This piece cannot move forward");
        if (color) // if its a white piece, forward is upwards on the board
            return n * -8;
        return n * 8; // if its a black piece, forward is down on the board
    }

    public int backward(int n) {
        // makes sure the move won't go off the board
        if ((!color && position + n * -8 < 0) || (color && position + n * 8 > 63))
            throw new IllegalStateException("This piece cannot move backward");
        if (color) // if its a white piece, backwards is down on the board
            return n * 8;
        return n * -8;// if its a black piece, backwards is up on the board
    }

    public static int getColumn(int n) // returns the column of an index from the array. remember, it starts with 0
    {
        return n % 8;
    }

    public static int getRow(int n) // returns the row of an index in the array. remember, it starts with 0
    {
        return n / 8;
    }
    
    public static void changeTurn()
    {
        turn = !turn;
    }

    public int right(int n) {
        int start = getColumn(position);
        int end = getColumn(position + n);
        // ensures the ending column is to the right of the starting column and they're
        // on the same row.
        if (end < start || Math.abs(end - start) >= 8)
            throw new IllegalStateException("This piece cannot move to the right!");
        return n;
    }

    public int left(int n) {
        int start = getColumn(position);
        int end = getColumn(position - n);
        // ensures the ending column is to the left of the starting column and they're
        // on the same row.
        if (end > start || Math.abs(end - start) >= 8)
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
