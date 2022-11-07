import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//  FALSE == BLACK PIECE, TRUE == WHITE PIECE

public class Board extends JFrame {
    static Container win;
    static Color color1 = Color.decode("#61492f");
    static Color color2 = Color.decode("#d4bca3");

    public Board() {
        super("Chess");
        win = getContentPane();
        win.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8, 8));

        win.add(p2, BorderLayout.WEST);
        win.add(p1, BorderLayout.CENTER);

        boolean lastWhite = false;
        JPanel[][] squares = new JPanel[8][8];
        for (int x = 0; x < 8; x++) {
            lastWhite = !lastWhite;
            for (int y = 0; y < 8; y++) {
                squares[x][y] = new JPanel();
                if (lastWhite) {
                    squares[x][y].setBackground(color2);
                    lastWhite = false;
                } else {
                    squares[x][y].setBackground(color1);
                    lastWhite = true;
                }

                p1.add(squares[x][y]);
            }
        }

        setSize(500, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Board();
    }

}