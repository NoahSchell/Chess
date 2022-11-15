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

    public ChessBoard() {
        for (int x = 0; x < 64; x++) {
            squares[x] = new JPanel();
        }
        Container win = getContentPane();

        JPanel board = new JPanel();
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

        loadPosition("r1bq1rk1/bppp1ppp/p1n2n2/4p3/2B1P3/2PP1N2/PP1N1PPP/R1BQ1RK1");
        this.displayGame();
        setSize(600, 600);
        setVisible(true);
    }

    public void displayGame() {
        for (int x = 0; x < 64; x++) {
            if (game[x] != null) {
                JButton button = new JButton(game[x].getImage());
                button.setOpaque(true);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.addActionListener(new Mouse());
                squares[x].add(button);
            } else
                squares[x].removeAll();
        }
    }

    public class Mouse extends MouseInputAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object button = e.getSource();
            
            for (int x = 0; x < 64; x++) {
                Component [] stuff = squares[x].getComponents();
                for(int y = 0; y < stuff.length; y++)
                {
                    if (stuff[y] == button)
                    {
                        ChessBoard.setGreen(x);
                        return;
                    }
                }
            }
            resetColors();
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
