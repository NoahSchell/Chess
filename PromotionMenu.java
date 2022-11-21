import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// this class is used for creating the menu that allows the user to select which piece they would like to promote to

public class PromotionMenu extends JFrame implements ActionListener
{
    static JButton [] buttons = new JButton [4];
    public static JFrame frame = new JFrame();
    static String [] labels;
    public PromotionMenu(boolean color)
    {
        if(color)
            labels = new String [] {"Pieces/WhiteQueen.png", "Pieces/WhiteKnight.png", "Pieces/WhiteBishop.png", "Pieces/WhiteRook.png"};
        else
            labels = new String [] {"Pieces/BlackQueen.png", "Pieces/BlackKnight.png", "Pieces/BlackBishop.png", "Pieces/BlackRook.png"};
        Container window = frame.getContentPane();
                
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,4));
                
        for(int x = 0; x < 4; x++)
        {   buttons[x] = new JButton(new ImageIcon(labels[x]));
            buttons[x].addActionListener(this);
            p.add(buttons[x]);
        }
                
        window.add(p);
                
        frame.pack();
        frame.setVisible(false);
    }
    
    public void actionPerformed( ActionEvent e )
    {
        int pos = 0;
        // loop through the game array until we find the pawn that was promoted and save it's position to the pos variable
        for(int x = 0; x < 64; x++)
        {
            if(Piece.game[x] instanceof Pawn && Piece.game[x].getColor() && x >= 0 && x <= 8)
            {
                pos = x; 
                break;
            }
            if(Piece.game[x] instanceof Pawn && !Piece.game[x].getColor() && x >= 56 && x <= 63)
            {
                pos = x;
                break;
            }
        }
        if( e.getSource() == buttons[0]) // if they pressed queen
        {
            Piece.game[pos] = new Queen(pos, Piece.game[pos].getColor()); // replace the piece at the pawns position with a queen
            frame.setVisible(false); // make the menu invisible
            ChessBoard.displayGame(); // update the display
        }
        if( e.getSource() == buttons[1])
        {
            Piece.game[pos] = new Knight(pos, Piece.game[pos].getColor());
            frame.setVisible(false);
            ChessBoard.displayGame();
        }
        if( e.getSource() == buttons[2])
        {
            Piece.game[pos] = new Bishop(pos, Piece.game[pos].getColor());
            frame.setVisible(false);
            ChessBoard.displayGame();
        }
        if( e.getSource() == buttons[3])
        {
           Piece.game[pos] = new Rook(pos, Piece.game[pos].getColor());
           frame.setVisible(false);
           ChessBoard.displayGame();
        }
    }
}
