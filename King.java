import javax.swing.*;
public class King extends Piece {

    private boolean canCastle;
    public King(int pos, boolean c) {
        super(pos, c);
        canCastle = true;
        if(!color)
            image = new ImageIcon("PNG Files\\BlackKing.png");
        else
            image = new ImageIcon("PNG Files\\WhiteKing.png");
    }

    public void setLegalMoves() {

        // Cardinal Directions
        try { legalMoves.add(position + forward(1)); } catch (IllegalStateException e) {}
        try { legalMoves.add(position + backward(1));} catch (IllegalStateException e) {}
        try {legalMoves.add(position + left(1)); } catch (IllegalStateException e) {}
        try {legalMoves.add(position + right(1));} catch (IllegalStateException e) {}
        // Diagonal Directions
        try {legalMoves.add(position + forward(1) + right(1));} catch (IllegalStateException e) {}
        try {legalMoves.add(position + forward(1) + left(1));} catch (IllegalStateException e) {}
        try {legalMoves.add(position + backward(1) + right (1));} catch (IllegalStateException e) {}
        try {legalMoves.add(position + backward(1) + left(1));} catch (IllegalStateException e) {}
        
        //castling
        if(canCastle)
        {
            // do something here...
        }


        /*
         * we will somehow need to ensure with each move that the king is not put in check by his own side, 
         * and that if the king is in check, legalMoves are restricted. 
         */


        //only keeps moves where pieces in legalMoves list are of the opposite color
        cleanMoves();
    }

}
