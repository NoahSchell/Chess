import javax.swing.*;

public class Knight extends Piece {

    public Knight(int pos, boolean c) {
        super(pos, c);
        if (color)
            image = new ImageIcon("Pieces/WhiteKnight.png");
        if (!color)
            image = new ImageIcon("Pieces/BlackKnight.png");
    }

    public void setLegalMoves() {

        // FORWARD TWO, LEFT AND RIGHT
        try {
            legalMoves.add(position + forward(2) + right(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + forward(2) + left(1));
        } catch (IllegalStateException e) {
        }
        // BACKWARD TWO, LEFT AND RIGHT
        try {
            legalMoves.add(position + backward(2) + left(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + backward(2) + right(1));
        } catch (IllegalStateException e) {
        }
        // TWO TO THE LEFT, FORWARD AND BACKWARDS
        try {
            legalMoves.add(position + left(2) + forward(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + left(2) + backward(1));
        } catch (IllegalStateException e) {
        }
        // TWO TO THE RIGHT, FORWARD AND BACKWARDS
        try {
            legalMoves.add(position + right(2) + forward(1));
        } catch (IllegalStateException e) {
        }
        try {
            legalMoves.add(position + right(2) + backward(1));
        } catch (IllegalStateException e) {
        }

        // only keeps the moves that don't have pieces of the same color in them
        cleanMoves();
    }

}
