public class Pawn extends Piece {

    public Pawn(int pos, boolean c) {
        super(pos, c);
    }

    public void setLegalMoves() {

        if ((color && position >= 49 && position <= 56) || (!color && position >= 9 && position <= 16)) // checks for
                                                                                                        // double
                                                                                                        // forward
        {
            try { legalMoves.add(position + forward(2)); } catch (IllegalStateException e) {}
        }
        // adds standard forward move
        try { legalMoves.add(position + forward(1)); } catch (IllegalStateException e) {}

        // checks capture spaces
        Piece potentialCapture = game[position + forward(1) + left(1)];
        if (potentialCapture != null && potentialCapture.getColor() != color) {
            try { legalMoves.add(position + forward(1) + left(1)); } catch (IllegalStateException e) {}
        }
        potentialCapture = game[position + forward(1) + right(1)];
        if (potentialCapture != null && potentialCapture.getColor() != color) {
            try { legalMoves.add(position + forward(1) + left(1)); } catch (IllegalStateException e) {}
        }

        cleanMoves();
    }

}
