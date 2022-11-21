import javax.swing.*;
import javax.swing.Timer;
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
    static King whiteKing, blackKing;
    JPanel board, options;
    Piece selectedPiece = null;

    public ChessBoard() {
        board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        options = new JPanel();
        setUpOptions();

        addMouseListener(new Mouse());
        for (int x = 0; x < 64; x++) {
            squares[x] = new JPanel();
            squares[x].setSize((int) (getWidth() / 8.0), (int) (getHeight() / 8.0));
        }
        Container win = getContentPane();
        win.setLayout(new BorderLayout());

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

        win.add(board, BorderLayout.CENTER);
        win.add(options, BorderLayout.EAST);

        loadPosition(startFen);
        displayGame();
        setSize(600, 600);
        setVisible(false);
    }

    static Thread whiteTimeThread;
    static Thread blackTimeThread;

    public static void updateThreads(boolean co) {
        if (co) {
            whiteTimeThread.stop();
            blackTimeThread.start();

        } else {
            whiteTimeThread.start();
            blackTimeThread.stop();
        }
    }

    public void updateVisible() {
        options.setLayout(new GridLayout(2, 1, 0, 500));
        JPanel whiteTime = new JPanel();
        JPanel blackTime = new JPanel();

        Timers whiteTimer = new Timers(true);
        Timers blackTimer = new Timers(false);

        whiteTimeThread = new Thread(whiteTimer);
        blackTimeThread = new Thread(blackTimer);

        whiteTime.add(whiteTimer);
        blackTime.add(blackTimer);

        options.add(blackTime);
        options.add(whiteTime);
        this.setVisible(true);
        whiteTimeThread.start();
        blackTimeThread.start();
    }

    static JButton five, ten, fifteen, thirty;
    static int startTime;
    JFrame frame;

    public void setUpOptions() {
        frame = new JFrame();
        five = new JButton("5:00");
        ten = new JButton("10:00");
        fifteen = new JButton("15:00");
        thirty = new JButton("30:00");
        five.addActionListener(new OptionsPaneButtons());
        ten.addActionListener(new OptionsPaneButtons());
        fifteen.addActionListener(new OptionsPaneButtons());
        thirty.addActionListener(new OptionsPaneButtons());

        frame.setLayout(new GridLayout(2, 1));
        JPanel question = new JPanel();
        frame.add(question);
        JPanel buttons = new JPanel();
        frame.add(buttons);

        question.add(new JLabel("Select the amount of starting time you wish to have"));
        buttons.add(five);
        buttons.add(ten);
        buttons.add(fifteen);
        buttons.add(thirty);

        frame.pack();
        frame.setVisible(true);
    }

    public class OptionsPaneButtons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == five)
                startTime = 5;
            if (e.getSource() == ten)
                startTime = 10;
            if (e.getSource() == fifteen)
                startTime = 15;
            if (e.getSource() == thirty)
                startTime = 30;
            frame.setVisible(false);
            updateVisible();
        }
    }

    public class Timers extends JLabel implements Runnable {
        private int remainingTime = startTime * 60 * 1000; // starting minutes * 60 seconds * 1000 ms
        Timer timer;
        boolean color;

        public Timers(boolean c) {
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    repaint();
                    revalidate();
                }
            });
            timer.start();
            timer.setRepeats(true);
            color = c;
        }

        public String getText() {
            return getRemainingTime();
        }

        public String getRemainingTime() {

            int hours = (int) ((remainingTime / 3600000) % 60);
            int minutes = (int) ((remainingTime / 60000) % 60);
            int seconds = (int) (((remainingTime) / 1000) % 60);

            return (format(hours) + ":" + format(minutes) + ":" + format(seconds));
        }

        public String format(int x) {
            String yeah = "" + x;
            while (yeah.length() < 2)
                yeah = "0" + x;
            return yeah;
        }

        public void setRemainingTime(int n) {
            remainingTime = n;
        }

        public void run() {
            while (true) {
                if (Piece.getTurn() == color) {
                    setRemainingTime(remainingTime - 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void displayGame() {
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
    public int getIndex(MouseEvent e) {
        Point mp = getMousePosition();
        mp.translate(0, -30);
        Component panel = board.getComponentAt(mp);
        if (panel instanceof JPanel)
            for (int x = 0; x < 64; x++)
                if (squares[x] == panel)
                    return x;
        return -1;
    }

    public class Mouse extends MouseInputAdapter {
        public void mouseClicked(MouseEvent e) {
            int index = getIndex(e);
            if (selectedPiece == null) // if no piece is currently selected
            {
                if (game[index] == null) // if you clicked a blank space
                {
                    resetColors(); // reset the colors
                    selectedPiece = null; // you did not select a piece
                } else // if you clicked a space that has a piece
                {
                    selectedPiece = game[index]; // you selected that piece
                    if (Piece.getTurn() == selectedPiece.getColor()) // if its that colors turn
                        setGreen(index); // set legal moves of that index green
                }
            } else // if a piece is currently selected
            {
                if (selectedPiece.getLegalMoves().contains(index)) {
                    selectedPiece.move(index);
                    resetColors();
                }
                selectedPiece = null; // unselect that piece
                resetColors(); // reset the colors
            }
            displayGame();
        }

        public void mouseEntered(MouseEvent e) {

        }

    }

    public static void resetColors() {
        int z = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (game[z] == null)
                    squares[z].setBackground(selected);
                if ((x + y) % 2 != 0) {
                    squares[z].setBackground(dark);
                } else
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
        for (int x = 0; x < indicies.size(); x++) {
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
                            blackKing = new King(row * 8 + column, false);
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
                            whiteKing = new King(row * 8 + column, true);
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
                    column++;
                }
            }
        }

    }

    public static King getWhiteKing() {
        return whiteKing;
    }

    public static King getBlackKing() {
        return blackKing;
    }

    public static ArrayList<Integer> blackSqaures() {
        ArrayList<Integer> s = new ArrayList<Integer>(); // array list of all squares that black controls

        for (int x = 0; x < 63; x++) {
            // if theres a piece there and its black and its not a pawn
            if (game[x] != null && !game[x].getColor() && !(game[x] instanceof Pawn)) {
                s.addAll(game[x].getLegalMoves());
            } else if (game[x] != null && !game[x].getColor() && game[x] instanceof Pawn) // else if theres a piece and
                                                                                          // its black and its a pawn
            {
                // pawns only control the sqaures diagonally infront of them
                s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1)); // add the pawns position plus
                                                                                      // forward and right/left
                s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));
            }
        }

        return s;
    }

    public static ArrayList<Integer> whiteSqaures() {
        ArrayList<Integer> s = new ArrayList<Integer>(); // array list of all squares that white controls

        for (int x = 0; x < 63; x++) {
            // if theres a piece there and its white and its not a pawn
            if (game[x] != null && game[x].getColor() && !(game[x] instanceof Pawn)) {
                s.addAll(game[x].getLegalMoves());
            } else if (game[x] != null && game[x].getColor() && game[x] instanceof Pawn) // else if theres a piece and
                                                                                         // its white and its a pawn
            {
                // pawns only control the sqaures diagonally infront of them
                s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1)); // add the pawns position plus
                                                                                      // forward and right/left
                s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));
            }
        }

        return s;
    }

    public static void psuedoLegalMoves() {
        ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
        Piece[] copy = game.clone(); // copy is a fresh copy of the current position. Altering copy will not change
                                     // game.
        for (int x = 0; x < 63; x++) {
            // theres a piece in that index that is turn
            if (game[x] != null && Piece.getTurn() == game[x].getColor()) {
                // loop through its legal moves
                for (int y = 0; y < game[x].getLegalMoves().size(); y++) {
                    // for each legal move
                    int target = game[x].getLegalMoves().get(y);
                    // do the legal move
                    game[target] = game[x]; // this should probably happen in copy
                    game[x] = null;

                    // if after the move, the king is in check
                    if (whiteKing.isInCheck()) // should be dynamic to adapt to black king too
                    {
                        toBeRemoved.add(target);// this is not ACTUALLY a legal move
                    }

                    // undo the move
                    game[x] = game[target];
                    // stuff idk
                    if (copy[target] != null)
                        game[target] = copy[target];
                    else
                        game[target] = null;
                }
            }
        }
        System.out.println(toBeRemoved);
    }

    public static void main(String args[]) {
        new ChessBoard();
        // psuedoLegalMoves();
    }
}