import javax.swing.*;

public class Pawn extends Piece {

    public Pawn(int pos, boolean c) {
        super(pos, c);
        if (color)
            image = new ImageIcon("Pieces/WhitePawn.png");
        if (!color)
            image = new ImageIcon("Pieces/BlackPawn.png");
    }

    public void setLegalMoves() {

        if ((color && position >= 48 && position <= 55) || (!color && position >= 8 && position <= 15)) // checks for
                                                                                                        // double
                                                                                                        // forward
        {
            try {
            if (game[position + forward(1)] == null && game[position + forward(2)] == null)// makes sure there's nothing in front of it 
                legalMoves.add(position + forward(2));
            } catch (IllegalStateException e) {
            }
        }
        // adds standard forward move
        try {
            if (game[position + forward(1)] == null) // makes sure there's no piece in front of it 
                legalMoves.add(position + forward(1));
        } catch (IllegalStateException e) {
        }

        // checks capture spaces. try blocks make sure game doesn't go out of bounds
        Piece potentialCapture;
        try {
            potentialCapture = game[position + forward(1) + left(1)];
            if (potentialCapture != null && potentialCapture.getColor() != color) {
                legalMoves.add(position + forward(1) + left(1));
            }
        } catch (IllegalStateException e) {
        }
        try {
            potentialCapture = game[position + forward(1) + right(1)];
            if (potentialCapture != null && potentialCapture.getColor() != color) {
                legalMoves.add(position + forward(1) + right(1));
            }
        } catch (IllegalStateException e) {
        }

        cleanMoves();
    }

}
