import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

//  FALSE == BLACK PIECE, TRUE == WHITE PIECE
//  For now, click the piece, then click the space. We can add drag stuff later. 

public class Board extends JFrame{
    static Piece[] game = Piece.game;
    public static void main(String[] args) {
        Piece.setUpBoard();
        new Board();
    }

    static Container win;
    static Color color1 = Color.decode("#61492f");
    static Color color2 = Color.decode("#d4bca3");
    static JPanel p1; 
    public Board() {
        super("Chess");
        try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch (Exception ex) {}
        win = getContentPane();
        win.setLayout(new BorderLayout()); // would gridbaglayout work?

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());

        p1 = new JPanel();
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

    
    public class MouseStuff extends MouseInputAdapter
    {
        private Component component; 

        public void mousePressed(MouseEvent e)
        {
            component = e.getComponent();
            highlightLegalMoves(e.getSource());
        }

        public void highlightLegalMoves(Object obj)
        {
            if (obj instanceof Piece)
            {
                Piece piece = (Piece)obj;
                ArrayList<Integer> squares = piece.getLegalMoves();
                for(int x=0; x<64; x++)
                    if (squares.contains(x))
                    {
                        //JPanel panelToChange = p1.getComponent(x/8, x%8);
                    }
            }
        }

        public void paintComponent(Graphics g)
        {
            for(int x = 0; x<64; x++)
            {
                if (game[x] != null)
                {
                    
                }
            }
        }
    }

}