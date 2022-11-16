import java.util.*;
import javax.swing.*;

public /* Abstract? */ class Piece {
    public static Piece[] game = new Piece[64]; // this array will be used throughout the game to keep track of pieces.

    // each piece has a position, color (T = white, F = black), captured (F by
    // default), and list of legalMoves as indicies in game
    protected int position;
    protected boolean color, captured;
    protected ArrayList<Integer> legalMoves;
    protected ImageIcon image;

    public Piece(int x, boolean c) {
        position = x;
        color = c;
        captured = false;
        legalMoves = new ArrayList<Integer>();
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
        if (legalMoves.contains(destination)) { // if the destination is a legal move
            game[destination] = game[position]; // set the piece at destination = piece at 
            game[position] = null; // set old spot to null because nothing is there
            position = destination; // update position variable for the piece to be destination
            return true;
        }
        return false;
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

    // we will need to update this to ensure the moves will not place the king in
    // check. using a stack?
    public void cleanMoves() {
        for (int x = 0; x < legalMoves.size(); x++) {
            // if a piece is in the legal move spot and that piece is the same color, remove
            // that legal move from legalMoves
            if (game[legalMoves.get(x)] != null && game[legalMoves.get(x)].getColor() == color)
                legalMoves.remove(x);
        }
    }

    // this method initializes the pieces in their correct positions to begin the
    // game
    public static void setUpBoard() {
        for (int x = 0; x < 8; x++) // pawns
        {
            game[x + Piece.down()] = new Pawn(x + Piece.down(), false); // second row filled with black pawns
            game[x + Piece.down(6)] = new Pawn(x + Piece.down(6), true);// seventh row filled with white pawns
        }
        for (int x = 0; x < 8; x += 7) // rooks
        {
            game[x] = new Rook(x, false); // space 0 and 7 are black rooks
            game[x + Piece.down(7)] = new Rook(x + Piece.down(7), true); // down 7 rows are white rooks
        }
        for (int x = 1; x < 7; x += 5) // knights
        {
            game[x] = new Knight(x, false); // spaces 1 and 6 are black knights
            game[x + Piece.down(7)] = new Knight(x + Piece.down(7), true); // down 7 rows are white knights
        }
        for (int x = 2; x < 6; x += 3) // bishops
        {
            game[x] = new Bishop(x, false); // spaces 2 and 5 are black bishiops
            game[x + Piece.down(7)] = new Bishop(x + Piece.down(7), true); // down 7 rows are white bishops
        }
        // queens
        game[3] = new Queen(3, false); // space 3 is the black queen
        game[3 + Piece.down(7)] = new Queen(3 + Piece.down(7), true); // down 7 rows is the white queen
        // kings
        game[4] = new King(4, false); // space 4 is the black king
        game[4 + Piece.down(7)] = new King(4 + Piece.down(0), true); // down 7 rows is the white king
    }

}
