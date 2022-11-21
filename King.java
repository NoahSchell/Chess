import javax.swing.*;
import java.util.*;
public class King extends Piece {
    private boolean canCastle;
    private boolean inCheck;
    public King(int pos, boolean c) {
        super(pos, c);
        canCastle = true;
        if (color)
            image = new ImageIcon("Pieces/WhiteKing.png");
        if (!color)
            image = new ImageIcon("Pieces/BlackKing.png");
    }

    public void setCanCastle(boolean a)
    {
        canCastle = a; 
    }

    public void setLegalMoves() {
        // Cardinal Directions
        try {
            legalMoves.add(position + forward(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + backward(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + left(1));
        } catch (IllegalStateException e) {
        }
        try {
            if (game[position + right(1)] == null || game[position + right(1)].getColor() != color)
            {
                legalMoves.add(position + right(1));
            } 
        } catch (IllegalStateException e) {
        }
        // Diagonal Directions
        
        try {
            if (game[position + forward(1) + right(1)] == null || game[position + forward(1) + right(1)].getColor() != color)
            {
                legalMoves.add(position + forward(1) + right(1));
            }
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + forward(1) + left(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + backward(1) + right(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + backward(1) + left(1));
        } catch (IllegalStateException e) {
        }

        // castling
        if (canCastle) {
            // do something here...
        }

        /*
         * we will somehow need to ensure with each move that the king is not put in
         * check by his own side,
         */

        // only keeps moves where pieces in legalMoves list are of the opposite color
        cleanMoves();
    
    }
    
    // returns true if the king is in check(any legal move is a capture of our king)
    boolean isInCheck()
    {
        if(this.color)
        {
            if(ChessBoard.blackSqaures().contains(position))
                return true;
            return false;
        }
        else
        {
            if(ChessBoard.whiteSqaures().contains(position))
                return true;
            return false;
        }
    }
}
