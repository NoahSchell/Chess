import javax.swing.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessBoard extends JFrame {
    static JPanel[] squares = new JPanel[64]; // what the GUI actually displays
    static Piece[] game = Piece.game; // computers internal representation of the board 
    
    static Color dark = new Color(150, 111, 67); // color for dark squares
    static Color light = new Color(242, 210, 173); // color for light squares
    static Color selected = Color.decode("#a5e68c"); // color to display where a selected piece can move
    
    static String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"; // FEN string for the starting postion
    
    static King whiteKing, blackKing; // static variables for the kings so that we can easily refer to them when looking at Checks
    JPanel board; 
    
    Piece selectedPiece = null;
    
    public ChessBoard() {
        board = new JPanel();
        board.setLayout(new GridLayout(8, 8));

        addMouseListener(new Mouse());
        for (int x = 0; x < 64; x++) {
            squares[x] = new JPanel();
            squares[x].setSize((int)(getWidth()/8.0), (int)(getHeight()/8.0));
        }
        Container win = getContentPane();
        
        int z = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares[z] = new JPanel();
                if ((x + y) % 2 != 0)
                    squares[z].setBackground(dark);
                else
                    squares[z].setBackground(light);
                board.add(squares[z]);
                z++;
            }
        }

        win.add(board);

        loadPosition(startFen); // loads the starting position
        displayGame();
        
        setSize(600, 600);
        setVisible(true);
    }

    public static void displayGame() {
        for (int x = 0; x < 64; x++) { // redisplay game
            squares[x].removeAll();
            if (game[x] != null) {
                JLabel temp = new JLabel(game[x].getImage()); 
                squares[x].add(temp);
                squares[x].revalidate(); // the cornerstone of our program.
            }
        }
    }
    
    // returns the index in squares[] or game[] of where the mouse is. 
    public int getIndex(MouseEvent e) 
    {
        Point mp = getMousePosition();
        mp.translate(0, -30);
        Component panel = board.getComponentAt(mp);
        if (panel instanceof JPanel)
            for (int x =0; x < 64; x++)
                if (squares[x] == panel)
                    return x; 
        return -1;
    }

    public class Mouse extends MouseInputAdapter  {
        public void mouseClicked(MouseEvent e)
        {
            int index = getIndex(e);
            if (selectedPiece == null) // if no piece is currently selected
            {
                if (game[index] == null) // if you clicked a blank space
                {
                    resetColors(); // reset the colors
                    selectedPiece = null; // you did not select a piece
                }
                else // if you clicked a space that has a piece
                {
                    selectedPiece = game[index]; // you selected that piece 
                    if (Piece.getTurn() == selectedPiece.getColor()) // if its that colors turn
                        setGreen(index); // set legal moves of that index green
                }
            }
            else // if a piece is currently selected
            {
                selectedPiece.move(index); // play the move
                resetColors();
                
                selectedPiece = null; // unselect that piece
                resetColors(); //reset the colors
            }
            displayGame(); // update the GUI
        }
        
        public void mouseEntered(MouseEvent e)
        {
            
        }

    }

    public static void resetColors()
    {
        int z = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (game[z] == null)
                    squares[z].setBackground(selected);
                if ((x + y) % 2 != 0)
                {
                    squares[z].setBackground(dark);
                }
                else
                    squares[z].setBackground(light);
                z++;
            }
        }
    }
    
    public static void setGreen(int pos) {
        resetColors();
        if (game[pos] == null)
            return;
        ArrayList<Integer> indicies = game[pos].getLegalMoves();
        for(int x = 0; x<indicies.size(); x++)
        {
            squares[indicies.get(x)].setBackground(selected);
        }
    }

    // loads a position into the game array from a FEN string
    public static void loadPosition(String fen) {
        int column = 0, row = 0; // we start at position 0
        for (int x = 0; x < fen.length(); x++) { // loops through all characters in the FEN string
            char c = fen.charAt(x);
            if (c == '/') { // if there's a '/'
                column = 0; // reset column
                row++; // move to the next row
            } else {
                if (Character.isDigit(c)) { // if there's an integer
                    column += Character.getNumericValue(c); // skip that number of spaces (columns in that row)
                } else { // if there's a letter add that piece to our current location on the board [row * 8 + column] converts the columns and rows to the index of that square
                    // K = WhiteKing, Q = WhiteQueen, B = WhiteBishop,  N = WhiteKnight, R = WhiteRook, P = WhitePawn
                    // k = BlackKing, q = BlackQueen, b = BlackBishop, n = BlackKnight, r = BlackRook, p = BlackPawn
                    switch (c) {
                        case 'k':
                            blackKing = new King(row * 8 + column, false); // give Black's king a name so we can refer to it later
                            game[row * 8 + column] = blackKing;
                            break;
                            
                        case 'q':
                            game[row * 8 + column] = new Queen(row * 8 + column, false);
                            break;
                            
                        case 'b':
                            game[row * 8 + column] = new Bishop(row * 8 + column, false);
                            break;

                        case 'n':
                            game[row * 8 + column] = new Knight(row * 8 + column, false);
                            break;

                        case 'r':
                            game[row * 8 + column] = new Rook(row * 8 + column, false);
                            break;

                        case 'p':
                            game[row * 8 + column] = new Pawn(row * 8 + column, false);
                            break;

                        case 'K':
                            whiteKing = new King(row * 8 + column, true); // give White's king a name so that we can refer to it later
                            game[row * 8 + column] = whiteKing;
                            break;

                        case 'Q':
                            game[row * 8 + column] = new Queen(row * 8 + column, true);
                            break;

                        case 'B':
                            game[row * 8 + column] = new Bishop(row * 8 + column, true);
                            break;

                        case 'N':
                            game[row * 8 + column] = new Knight(row * 8 + column, true);
                            break;

                        case 'R':
                            game[row * 8 + column] = new Rook(row * 8 + column, true);
                            break;

                        case 'P':
                            game[row * 8 + column] = new Pawn(row * 8 + column, true);
                    }
                    column++; // once we have added the piece, move to the next column
                }
            }
        }
    }
    
    // acessor methods for the Kings so that they may be easily referenced in King.isInCheck
    public static King getWhiteKing()
    {
        return whiteKing;
    }
    
    public static King getBlackKing()
    {
        return blackKing;
    }
    
    // returns an ArrayList of all the sqaures that black attacks
    public static ArrayList<Integer> blackSquares()
    {
        ArrayList<Integer> s = new ArrayList<Integer>(); // list of all squares black attacks
        for(int x = 0; x < 64; x++) // loop through all the squares on the board
        {
            // if there's a piece there and its black and its not a pawn
            if(game[x] != null && !game[x].getColor() && !(game[x] instanceof Pawn))
            {
                s.addAll(game[x].getPseudoLegalMoves());
            }else if(game[x] != null && !game[x].getColor() && game[x] instanceof Pawn) // else if there's a piece and its black and its a pawn
            {
                 // pawns only control the sqaures diagonally infront of them, so the squares they attack must be added differently from the rest of the pieces
                 // try catch so that we don't move out of bounds
                 try{s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1));}catch(IllegalStateException e){} // add the pawns position plus forward and right/left
                 try{s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));}catch(IllegalStateException e){}
            }
        }
        
        return s;
    }
    
    // returns an ArrayList of all of the squares that white attacks
    public static ArrayList<Integer> whiteSquares()
    {
        ArrayList<Integer> s = new ArrayList<Integer>(); // array list of all squares that white attacks
        for(int x = 0; x < 64; x++) // loop through all the squares on the board
        {
            // if there's a piece there and its white and its not a pawn
            if(game[x] != null && game[x].getColor() && !(game[x] instanceof Pawn))
            {
                s.addAll(game[x].getPseudoLegalMoves());
            }else if(game[x] != null && game[x].getColor() && game[x] instanceof Pawn) // else if there's a piece and its white and its a pawn
            {
                 // pawns only control the sqaures diagonally infront of them, so the squares they attack must be added differently from the rest of the pieces
                 // try catch so that we don't move out of bounds
                 try{s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1));}catch(IllegalStateException e){} // add the pawns position plus forward and right/left
                 try{s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));}catch(IllegalStateException e){}
            }
        }
        
        return s;
    }
    
    public static void main(String args[]) {
        new ChessBoard();
        new PromotionMenu(true); // create the promotion menu. Will be visible when needed and invisible when it's not
    }
}
