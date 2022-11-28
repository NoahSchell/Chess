import javax.swing.*;
import javax.swing.Timer;
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

    static King whiteKing, blackKing; // static variables for the kings so that we can easily refer to them when
                                      // looking at Checks
    JPanel board, options;

    Piece selectedPiece = null;

    public ChessBoard() {
        board = new JPanel();
        options = new JPanel();
        board.setLayout(new GridLayout(8, 8));

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

        setUpOptions();
        loadPosition(startFen); // loads the starting position
        displayGame();

        setSize(650, 600);
        setLocation(300, 100);
        setVisible(false);
    }

    static Thread whiteTimeThread;
    static Thread blackTimeThread;

    public static void updateThreads(boolean co) {
        if (co) {
            whiteTimeThread = null;
            blackTimeThread.start();

        } else {
            blackTimeThread = null;
            whiteTimeThread.start();

        }
    }

    public void updateVisible() {
        options.setLayout(new GridLayout(6, 1, 0, 60));
        JPanel whiteTime = new JPanel();
        whiteTime.setBackground(Color.decode("#7d5d3b"));
        JPanel blackTime = new JPanel();
        blackTime.setBackground(Color.decode("#7d5d3b"));
        forefit = new JButton("Forefit");
        forefit.setBackground(Color.decode("#7d5d3b"));
        forefit.setBorderPainted(false);
        forefit.setFocusPainted(false);
        forefit.addActionListener(new OptionsPaneButtons());
        fen = new JButton("Get FEN String");
        fen.setBackground(Color.decode("#7d5d3b"));
        fen.setBorderPainted(false);
        fen.setFocusPainted(false);
        fen.addActionListener(new OptionsPaneButtons());
        notation = new JButton("Get notation");
        notation.setBackground(Color.decode("#7d5d3b"));
        notation.setBorderPainted(false);
        notation.setFocusPainted(false);
        notation.addActionListener(new OptionsPaneButtons());
        back = new JButton("<");
        back.setBackground(Color.decode("#7d5d3b"));
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        //back.addActionListener(new OptionsPaneButtons());
        forward = new JButton(">");
        forward.setBackground(Color.decode("#7d5d3b"));
        forward.setBorderPainted(false);
        forward.setFocusPainted(false);
        //forward.addActionListener(new OptionsPaneButtons());
        JPanel forback = new JPanel();
        forback.setLayout(new GridLayout(1, 2));
        forback.add(back);
        forback.add(forward);

        Timers whiteTimer = new Timers(true);
        Timers blackTimer = new Timers(false);

        whiteTimeThread = new Thread(whiteTimer);
        blackTimeThread = new Thread(blackTimer);

        whiteTime.add(whiteTimer);
        blackTime.add(blackTimer);

        options.setBackground(Color.decode("#c9a885"));
        options.add(blackTime);
        options.add(forefit);
        options.add(notation);
        options.add(fen);
        options.add(forback);
        options.add(whiteTime);
        this.setVisible(true);
        whiteTimeThread.start();
        blackTimeThread.start();
    }

    static JButton five, ten, fifteen, thirty, forefit, fen, notation, back, forward;
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
        frame.setLocation(500, 300);
        frame.setVisible(true);
    }

    public class OptionsPaneButtons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == five){
                startTime = 5;
                updateVisible();
            }
            if (e.getSource() == ten){
                startTime = 10;
                updateVisible();
            }
            if (e.getSource() == fifteen){
                startTime = 15;
                updateVisible();
            }
            if (e.getSource() == thirty){
                startTime = 30;
                updateVisible();
            }
            if (e.getSource() == forefit)
                forefit();
            if (e.getSource() == fen)
            {
                String f = getFen();
                JOptionPane.showInputDialog(null, "Current Position", f);
            }
            if (e.getSource() == notation)
                showNotation();
            if (e.getSource() == back)
            {
                if (Piece.endGame)
                    moveGame(false);
            }
            if (e.getSource() == forward)
            if (Piece.endGame)
            {

                moveGame(true);
            }
            frame.setVisible(false);
        }
    }

    // true means move forward, false means move back
    public void moveGame(boolean direction)
    {
        int loaded = Piece.findLoaded(getFen());
        if (direction)
        {
            try {loadPosition(Piece.gameHistory.get(loaded+1)); 
                 resetColors();
            } catch (IndexOutOfBoundsException e) {}
        }
        if (!direction)
        {
            try {loadPosition(Piece.gameHistory.get(loaded-1)); 
                 resetColors();
            } catch (IndexOutOfBoundsException e) {}
        }
        displayGame();
    }

    public void showNotation()
    {
        JFrame fr = new JFrame();
        Container wi = fr.getContentPane();
        String notat = "";
        for (int x = 0; x < Piece.notation.size(); x++)
        {
            if (x % 2 == 0)
                notat += (x/2 + 1 + ". ");
            notat += Piece.notation.get(x) + "  ";
            if (x % 2 == 1)
                notat += "\n";
        }
        wi.add(new JTextArea(notat));
        System.out.println(Piece.notation);

        fr.pack();
        fr.setVisible(true);
    }
    public void forefit() {
        boolean current = Piece.getTurn();
        win(!current);
        Piece.endGame();
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
                whiteTimeThread = new Thread();
                blackTimeThread = new Thread();
                if (Piece.getTurn() == color && !Piece.endGame) {
                    setRemainingTime(remainingTime - 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public static void checkCheckmate()
    {
        // if it's white's turn and white is in check
        if (Piece.getTurn() && whiteKing.isInCheck()) {
            // loop through all spaces
            for (int x = 0; x < 64; x++)
                // check white's moves
                if (game[x] != null && game[x].getColor() && !(game[x].getLegalMoves().isEmpty()))
                    return; // if white has a move, get out
            win(false); // otherwise black wins
        }
        // if its blacks turn and the black king is in check
        else if (!Piece.getTurn() && blackKing.isInCheck()) {
            // loop through all spaces
            for (int y = 0; y < 64; y++)
                // check blacks's moves
                if (game[y] != null && !game[y].getColor() && !(game[y].getLegalMoves().isEmpty())) {
                    System.out.println("run");
                    return; // if black has a move, get out
                    
                }
            win(true); // otherwise white wins
        }
        
    }

    public static void displayGame() {
        for (int x = 0; x < 64; x++) { // redisplay game
            squares[x].removeAll();
            squares[x].revalidate();
            if (game[x] != null) {
                JLabel temp = new JLabel(game[x].getImage());
                squares[x].add(temp);
                squares[x].revalidate(); // the cornerstone of our program.
            }
        }
        checkCheckmate();
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
            displayGame(); // update the GUI
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

    // loads a position into the game array from a FEN string
    public void loadPosition(String fen) {
        for (int x = 0; x < 64; x++)
        {
            game[x] = null;
        }
        int column = 0, row = 0; // we start at position 0
        for (int x = 0; x < fen.length(); x++) { // loops through all characters in the FEN string
            char c = fen.charAt(x);
            if (c == '/') { // if there's a '/'
                column = 0; // reset column
                row++; // move to the next row
            } else {
                if (Character.isDigit(c)) { // if there's an integer
                    column += Character.getNumericValue(c); // skip that number of spaces (columns in that row)
                } else { // if there's a letter add that piece to our current location on the board [row
                         // * 8 + column] converts the columns and rows to the index of that square
                    // K = WhiteKing, Q = WhiteQueen, B = WhiteBishop, N = WhiteKnight, R =
                    // WhiteRook, P = WhitePawn
                    // k = BlackKing, q = BlackQueen, b = BlackBishop, n = BlackKnight, r =
                    // BlackRook, p = BlackPawn
                    switch (c) {
                        case 'k':
                            blackKing = new King(row * 8 + column, false); // give Black's king a name so we can refer
                                                                           // to it later
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
                            whiteKing = new King(row * 8 + column, true); // give White's king a name so that we can
                                                                          // refer to it later
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
        Piece.gameHistory.add(getFen());
    }

    // acessor methods for the Kings so that they may be easily referenced in
    // King.isInCheck
    public static King getWhiteKing() {

        return whiteKing;
    }

    public static King getBlackKing() {
        return blackKing;
    }

    // returns an ArrayList of all the sqaures that black attacks
    public static ArrayList<Integer> blackSquares() {
        ArrayList<Integer> s = new ArrayList<Integer>(); // list of all squares black attacks
        for (int x = 0; x < 64; x++) // loop through all the squares on the board
        {
            // if there's a piece there and its black and its not a pawn
            if (game[x] != null && !game[x].getColor() && !(game[x] instanceof Pawn)) {
                s.addAll(game[x].getPseudoLegalMoves());
            } else if (game[x] != null && !game[x].getColor() && game[x] instanceof Pawn) // else if there's a piece and
                                                                                          // its black and its a pawn
            {
                // pawns only control the sqaures diagonally infront of them, so the squares
                // they attack must be added differently from the rest of the pieces
                // try catch so that we don't move out of bounds
                try {
                    s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1));
                } catch (IllegalStateException e) {
                } // add the pawns position plus forward and right/left
                try {
                    s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));
                } catch (IllegalStateException e) {
                }

            }
        }

        return s;
    }

    // returns an ArrayList of all of the squares that white attacks
    public static ArrayList<Integer> whiteSquares() {
        ArrayList<Integer> s = new ArrayList<Integer>(); // array list of all squares that white attacks
        for (int x = 0; x < 64; x++) // loop through all the squares on the board
        {
            // if there's a piece there and its white and its not a pawn
            if (game[x] != null && game[x].getColor() && !(game[x] instanceof Pawn)) {
                s.addAll(game[x].getPseudoLegalMoves());
            } else if (game[x] != null && game[x].getColor() && game[x] instanceof Pawn) // else if there's a piece and
                                                                                         // its white and its a pawn
            {
                // pawns only control the sqaures diagonally infront of them, so the squares
                // they attack must be added differently from the rest of the pieces
                // try catch so that we don't move out of bounds
                try {
                    s.add(game[x].getPosition() + game[x].forward(1) + game[x].right(1));
                } catch (IllegalStateException e) {
                } // add the pawns position plus forward and right/left
                try {
                    s.add(game[x].getPosition() + game[x].forward(1) + game[x].left(1));
                } catch (IllegalStateException e) {
                }

            }
        }

        return s;
    }

    public static void main(String args[]) {
        new ChessBoard();
    }

    public static String getFen()
    {
        String fen = "";
        int empty = 0; 
        for (int x = 0; x < 64; x++)
        {
            if (x%8 == 0 && x !=0)
            {
                if (empty != 0)
                    fen += empty;
                empty = 0;
                fen += "/";
            }
            if (game[x] == null)
                empty ++;
            else {
                if (empty != 0)
                    fen += empty;
                empty = 0; 
                fen += game[x].getFenLetter();
            }
        }
        return fen;
    }

    public static void win(boolean c) {
        Piece.endGame(); // end the game (stops timers, stops allowing moves, etc)
        if (c) // If white wins
            JOptionPane.showMessageDialog(null, "White wins! Congrats", "End of Game", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Black wins! Congrats", "End of Game", JOptionPane.INFORMATION_MESSAGE);
    }
}