import javax.swing.*;
import java.awt.*;
import java.io.*;

//  FALSE == BLACK PIECE, TRUE == WHITE PIECE
//  For now, click the piece, then click the space. We can add drag stuff later. 

public class Board extends JFrame {
    static Piece[] game = Piece.game;
    public static void main(String[] args) {
        Piece.setUpBoard();
        new Board();
    }
    
    static Container win;
    static Color color1 = Color.decode("#61492f");
    static Color color2 = Color.decode("#d4bca3");

    public Board() {
        super("Chess");
        try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch (Exception ex) {}
        win = getContentPane();
        win.setLayout(new BorderLayout()); // would gridbaglayout work?

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8, 8)); 

        win.add(p2, BorderLayout.WEST);
        win.add(p1, BorderLayout.CENTER);

        boolean lastWhite = false; 
        JPanel panel;
        for (int x = 0; x < 64; x++)
        {
            if (x%8==0)
                lastWhite = !lastWhite;
            panel = new JPanel();
            if (game[x] != null)
                panel.add(new JLabel(game[x].getImage()));
            if (lastWhite)
            {
                panel.setBackground(color2);
                lastWhite = false;
            }
            else   
            {
                panel.setBackground(color1);
                lastWhite = true;
            }
            p1.add(panel);
        }

        setSize(800, 800);
        setVisible(true);
    }
}