import javax.swing.*;

public class Queen extends Piece {

    public Queen(int x, boolean col) {
        super(x, col);
        if (color)
            image = new ImageIcon("Pieces/WhiteQueen.png");
        if (!color)
            image = new ImageIcon("Pieces/BlackQueen.png");

    }

    public void setPseudoLegalMoves() {
        // the legal moves for a queen are the combination of a bishop and rook in that
        // same spot!
        
        Bishop temp1 = new Bishop(position, color);
        Rook temp2 = new Rook(position, color);
        
        pseudoLegalMoves.addAll(temp1.getPseudoLegalMoves());
        pseudoLegalMoves.addAll(temp2.getPseudoLegalMoves());
    }
}
