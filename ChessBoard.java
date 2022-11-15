import javax.swing.*;
import java.awt.*;

// K = WhiteKing, Q = WhiteQueen, B = WhiteBishop,  N = WhiteKnight, R = WhiteRook, P = WhitePawn
// k = BlackKing, q = BlackQueen, b = BlackBishop, n = BlackKnight, r = BlackRook, p = BlackPawn
public class ChessBoard extends JFrame
{   
    static JPanel [] sqaures = new JPanel [64];
    
    static Color dark = new Color(150,111,67);
    static Color light = new Color(242,210,173);
    static String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public ChessBoard()
    {
        JFrame frame = new JFrame("Chess");
         
        Container win = frame.getContentPane();
        
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8,8));
        
        int z = 63; 
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                sqaures[z] = new JPanel();
                if((x+y) % 2 != 0)
                    sqaures[z].setBackground(dark);
                else
                    sqaures[z].setBackground(light);
                board.add(sqaures[z]);
                z--;
            }
        }
        
        win.add(board);
        
        loadPosition(startFen);
        
        if(isOccupied(sqaures[16]))
            sqaures[16].add(new JLabel(new ImageIcon("Pieces/WhitePawn.png")));
        
        frame.setSize(600,600);
        frame.setVisible(true);
    }
    
    boolean isOccupied(JPanel j)
    {
        Component[] components = j.getComponents();
        for (Component component : components) {
            if (component == null) {
                return true;
            }
        }
        return false;
    }
    
    public static void loadPosition(String fen)
    {
        int file = 7, rank = 7;
        for(int x = 0; x < fen.length(); x++)
        {
            char c = fen.charAt(x);
            if(c == '/')
            {
                file = 7;
                rank--;
            } 
            else 
            {
                if(Character.isDigit(c))
                {
                    file -= Character.getNumericValue(c);
                }
                else
                {
                   switch(c)
                   {
                       case 'k': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackKing.png")));
                           break;
                           
                       case 'q': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackQueen.png")));
                           break;
                           
                       case 'b': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackBishop.png")));
                           break;
                           
                       case 'n': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackKnight.png")));
                           break;
                           
                       case 'r': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackRook.png")));
                           break;
                           
                       case 'p': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/BlackPawn.png")));
                           break;
                           
                       case 'K': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhiteKing.png")));
                           break;
                           
                       case 'Q': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhiteQueen.png")));
                           break;
                           
                       case 'B': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhiteBishop.png")));
                           break;
                           
                       case 'N': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhiteKnight.png")));
                           break;
                           
                       case 'R': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhiteRook.png")));
                           break;
                           
                       case 'P': sqaures[rank * 8 + file].add(new JLabel(new ImageIcon("Pieces/WhitePawn.png"))); 
                   }
                   file--;
                }
            }
        }
    }
    
    public static void main (String args [])
    {
        new ChessBoard();
    }
}
    


