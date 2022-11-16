import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// K = WhiteKing, Q = WhiteQueen, B = WhiteBishop,  N = WhiteKnight, R = WhiteRook, P = WhitePawn
// k = BlackKing, q = BlackQueen, b = BlackBishop, n = BlackKnight, r = BlackRook, p = BlackPawn
public class ChessBoard extends JFrame {
    static JPanel[] squares = new JPanel[64];
    static Piece[] game = Piece.game;

    static Color dark = new Color(150, 111, 67);
    static Color light = new Color(242, 210, 173);
    static Color selected = Color.decode("#a5e68c");
    static String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    JPanel board; 
    Piece selectedPiece = null;

    public ChessBoard() {
        addMouseListener(new Mouse());
        for (int x = 0; x < 64; x++) {
            squares[x] = new JPanel();
            squares[x].setSize((int)(getWidth()/8.0), (int)(getHeight()/8.0));
        }
        Container win = getContentPane();

        board = new JPanel();
        board.setLayout(new GridLayout(8, 8));

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

        loadPosition(startFen);
        this.displayGame();
        setSize(600, 600);
        setVisible(true);
    }

    public void displayGame() {
        for (int x = 0; x < 64; x++)
        {
            squares[x].removeAll(); // remove everything from each JPanel
        }
        for (int x = 0; x < 64; x++) { // redisplay game
            JButton button;
            if (game[x] != null) {
                button = new JButton(game[x].getImage());
                button.setText(null);
                button.setVisible(true);
                button.setOpaque(true);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                //button.addActionListener(new Mouse());
                button.addMouseListener(new Mouse());
                squares[x].add(button);
            }
        }
    }

    //returns the index in squares[] or game[] of where the mouse is. 
    public int getIndex() 
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

    public class Mouse extends MouseInputAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton)
            {
                for (int x = 0; x < 64; x++) {
                    Component [] stuff = squares[x].getComponents();
                    for(int y = 0; y < stuff.length; y++)
                    {
                        if (stuff[y] == e.getSource())
                        {
                            ChessBoard.setGreen(x);
                        }
                    }
                }
            }
            
        }
        

        public void mouseClicked(MouseEvent e)
        {
            int index = getIndex();
            if (selectedPiece == null) // if no piece is currently selected
            {
                if (game[index] == null) // if you clicked a blank space
                {
                    resetColors(); // reset the colors
                    selectedPiece = null; // you did not select a piece
                }
                else // if you clicked a space that has a piece
                {
                    setGreen(index); // set legal moves of that index green
                    selectedPiece = game[index]; // you selected that piece 
                }
            }
            else // if a piece is currently selected
            {
                if (selectedPiece.move(index)) // if that piece can move where you clicked, do it
                    displayGame(); // and update the board
                selectedPiece = null; // unselect that piece
                resetColors(); //reset the colors
            }
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
                if ((x + y) % 2 != 0)
                    squares[z].setBackground(dark);
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

    public static void loadPosition(String fen) {
        int column = 0, row = 0;
        for (int x = 0; x < fen.length(); x++) {
            char c = fen.charAt(x);
            if (c == '/') {
                column = 0;
                row++;
            } else {
                if (Character.isDigit(c)) {
                    column += Character.getNumericValue(c);
                } else {
                    switch (c) {
                        case 'k':
                            game[row * 8 + column] = new King(row * 8 + column, false);
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
                            game[row * 8 + column] = new King(row * 8 + column, true);
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
                    column++;
                }
            }
        }

    }

    public static void main(String args[]) {
        new ChessBoard();
    }
}
