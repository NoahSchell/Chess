import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;

public /* Abstract? */ class Piece {
    public static LinkedList<String> notation = new LinkedList<String>();
    public static LinkedList<Object> gameHistory = new LinkedList<Object>(); // will need to be worked on. 
                                                                             // what if move happens on previous state? stack.pop()?
    public int currentGame = 0; 
    public static Piece[] game = new Piece[64]; // this array will be used throughout the game to keep track of pieces.

    // each piece has a position, color (T = white, F = black), captured (F by
    // default), and list of legalMoves as indicies in game
    protected int position;
    protected boolean color, captured, hasMoved = false;
    protected static boolean turn = true; // white goes first

    protected ArrayList<Integer> pseudoLegalMoves;
    protected ImageIcon image;
    private static boolean endGame = false;
    protected char fenLetter;

    public char getFenLetter()
    {
        return fenLetter;
    }

    public static void endGame() {
        endGame = true;
    }

    public Piece(int x, boolean c) {
        position = x;
        color = c;
        captured = false;
        pseudoLegalMoves = new ArrayList<Integer>();
    }

    // this method is intentionally blank, and is overridden in all subclasses. It's
    // purpose is to determine set the pieces PseudoLegalMoves (meaning checks are
    // ignored)
    public void setPseudoLegalMoves() {
    }

    // accessor methods

    // returns a list of the pieces PseudoLegalMoves (checks are ignored)
    public ArrayList<Integer> getPseudoLegalMoves() {
        pseudoLegalMoves.clear(); // clear everything
        setPseudoLegalMoves(); // set everything
        return pseudoLegalMoves;
    }

    public ImageIcon getImage() {
        return image;
    }

    // returns pseudoLegalMoveslegalMoves after each move has been filtered. These
    // are the pieces ACTUAL legal moves in the position
    public ArrayList<Integer> getLegalMoves() {
        pseudoLegalMoves.clear(); // clear everything
        setPseudoLegalMoves(); // set everything
        cleanMoves();
        return pseudoLegalMoves;
    }

    public static boolean getTurn() {
        return turn;
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
        String m = "";
        if (! (this instanceof Pawn))
            m += Character.toLowerCase((char)getFenLetter()); // the move starts with the fen letter (lower case) if not a pawn
        if (endGame)
            return false;
        ArrayList<Integer> moves = getLegalMoves();
        if (getColor() != turn) // if the piece you want to move isn't turn, get out of here
            return false;
        if (moves.contains(destination)) { // if the destination is a legal move
            // if the king is being moved to a castling square and it can castle in that
            // direction
            if (color && this instanceof King && destination == 62 && canCastleKingSide())
                castleKingSide(); // move the rook into it's correct position
            if (!color && this instanceof King && destination == 6 && canCastleKingSide())
                castleKingSide();
            if (color && this instanceof King && destination == 58 && canCastleQueenSide())
                castleQueenSide();
            if (!color && this instanceof King && destination == 2 && canCastleQueenSide())
                castleQueenSide();

            if (game[destination] != null) // if this is a capture
                m += "x"; // add an x to the move
            game[destination] = game[position]; // set the piece at destination = piece at
            game[position] = null; // set old spot to null because nothing is there
            position = destination; // update position variable for the piece to be destination

            turn = !turn; // changes which sides turn it is

            hasMoved = true;

            if (turn) {
                ChessBoard.updateThreads(true);
            } else {
                ChessBoard.updateThreads(false);
            }
            // if a pawn was promoted
            if (this instanceof Pawn && color && destination >= 0 && destination <= 8)
                promote(true);
            else if (this instanceof Pawn && !color && destination >= 56 && destination <= 63)
                promote(false);


            m += (char)(getColumn(destination) + 97);
            m += 8 - getRow(destination);
            notation.add(m);
            try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("move.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            } catch (Exception e) {}
            gameHistory.add(game);
            currentGame++;
            return true;
        }
        return false;
    }

    static JButton rook, knight, bishop, queen; 
    static JFrame f;
    public void promote(boolean c)
    {
        f = new JFrame();
        f.setLayout(new FlowLayout());
        Container window = f.getContentPane();
        if (c)
        {
            rook = new JButton(new ImageIcon("Pieces/WhiteRook.png"));
            knight = new JButton(new ImageIcon("Pieces/WhiteKnight.png"));
            bishop = new JButton(new ImageIcon("Pieces/WhiteBishop.png"));
            queen = new JButton(new ImageIcon("Pieces/WhiteQueen.png"));
        }
        else
        {
            rook = new JButton(new ImageIcon("Pieces/BlackRook.png"));
            knight = new JButton(new ImageIcon("Pieces/BlackKnight.png"));
            bishop = new JButton(new ImageIcon("Pieces/BlackBishop.png"));
            queen = new JButton(new ImageIcon("Pieces/BlackQueen.png"));
        }
        PromotionButtons listener = new PromotionButtons();
        rook.addActionListener(listener );
        knight.addActionListener(listener);
        bishop.addActionListener(listener);
        queen.addActionListener(listener);

        window.add(rook);
        window.add(knight);
        window.add(bishop);
        window.add(queen);
        f.pack();
        f.setVisible(true);
    }

    public class PromotionButtons implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == rook)
                game[position] = new Rook(position, getColor());
            if (e.getSource() == knight)
                game[position] = new Knight(position, getColor());
            if (e.getSource() == bishop)
                game[position] = new Bishop(position, getColor());
            if (e.getSource() == queen)
                game[position] = new Queen(position, getColor());
            f.setVisible(false);
            ChessBoard.displayGame();
        }
    }

    // returns true if the king can castle kingside
    public boolean canCastleKingSide() {
        if (color && !hasMoved && game[62] == null && game[61] == null && game[63] instanceof Rook && game[63].color
                && !game[63].hasMoved)
            return true;
        if (!color && !hasMoved && game[5] == null && game[6] == null && game[7] instanceof Rook && !game[7].color
                && !game[7].hasMoved)
            return true;
        return false;
    }

    // returns true if the king can castle queenside
    public boolean canCastleQueenSide() {
        if (color && !hasMoved && game[57] == null && game[58] == null && game[59] == null && game[56] instanceof Rook
                && game[56].color && !game[56].hasMoved)
            return true;
        if (!color && !hasMoved && game[1] == null && game[2] == null && game[3] == null && game[0] instanceof Rook
                && !game[0].color && !game[0].hasMoved)
            return true;
        return false;
    }

    // moves the rook to it's correct position after the king moves to its kingside
    // castling square
    public void castleKingSide() {
        if (color) {
            game[61] = game[63];
            game[63] = null;
            game[61].position = 61;
        }
        if (!color) {
            game[5] = game[7];
            game[7] = null;
            game[5].position = 5;
        }
    }

    // moves the rook to it's correct position after the king moves to its queenside
    // castling square
    public void castleQueenSide() {
        if (color) {
            game[59] = game[56];
            game[56] = null;
            game[59].position = 59;
        }
        if (!color) {
            game[3] = game[0];
            game[0] = null;
            game[3].position = 3;
        }
    }

    // transformation methods. they return mathematical transformations necessary to
    // perform the move described in the method name.
    public static int up() {
        return -8;
    }

    public static int up(int x) {
        return x * -8;
    }

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

    public static void changeTurn() {
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

    // filters through all of the PseudoLegal moves and removes the ones that are
    // Illegal because of checks
    public void cleanMoves() {
        if (color != turn || getPseudoLegalMoves().isEmpty()) // if the piece we are looking at is not turn, or it has
                                                              // no pseudolegal moves, we do not need to clean it's
                                                              // moves
            return; // so get the heck out of here
        // this speeds up the filtration process ALOT

        Piece[] copy = game.clone(); // make a copy of the current position so that we can reset the game after we
                                     // try each move
        King k = new King(0, true); // creates temp variable for the king that is relavent to the cleaning (the one
                                    // who's color matches turn)
        ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();

        if (turn) // if it's whites turn
            k = ChessBoard.getWhiteKing(); // get the white king
        else // if it's blacks turn
            k = ChessBoard.getBlackKing(); // get the black king

        // loop through each of the pieces PseudoLegalMoves
        for (int x = 0; x < getPseudoLegalMoves().size(); x++) {
            int target = getPseudoLegalMoves().get(x), original = position; // target = the move

            // play the move
            game[target] = game[position];
            game[position] = null;
            position = target;

            if (k.isInCheck()) // if the king is in check
            {
                toBeRemoved.add(target); // add the move to the list of moves that need to be removed
            }

            // undo the move using copy
            game[original] = copy[original];
            game[target] = copy[target];
            position = original;
        }

        // if the current piece is a king, don't allow it to castle out of check
        if (color && this instanceof King && position == 60 && ((King) this).isInCheck()) // if it is on it's starting
                                                                                          // square, and it is in check,
                                                                                          // don't allow it to castle
        {
            toBeRemoved.add(62);
            toBeRemoved.add(58);
        }
        if (!color && this instanceof King && position == 4 && ((King) this).isInCheck()) {
            toBeRemoved.add(6);
            toBeRemoved.add(2);
        }

        // if the current piece is a king, don't allow it to castle through check
        if (color && this instanceof King && position == 60) // if it is a white king and it's on its starting square
        {
            if (toBeRemoved.contains(61)) // if moving one square to the right is illegal
                toBeRemoved.add(62); // then castling in that direction is also illegal
            if (toBeRemoved.contains(59))
                toBeRemoved.add(59);
        }
        if (!color && this instanceof King && position == 4) {
            if (toBeRemoved.contains(5))
                toBeRemoved.add(6);
            if (toBeRemoved.contains(3))
                toBeRemoved.add(2);
        }

        pseudoLegalMoves.removeAll(toBeRemoved); // remove all of the moves that were determined to be illegal
    }
}