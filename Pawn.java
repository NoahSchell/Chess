public class Pawn extends Piece {

    public Pawn(int pos, boolean c) {
        super(pos, c);
    }

    public void setLegalMoves() {

        if ((color && position >= 49 && position <= 56) || (!color && position >= 9 && position <= 16)) // checks for
                                                                                                        // double
                                                                                                        // forward
        {
            legalMoves.add(position + forward(2));
        }
        // adds standard forward move
        legalMoves.add(position + forward(1));

        Piece potentialCapture = game[position + forward(1) + left(1)];
        if (potentialCapture != null && potentialCapture.getColor() != color) {
            legalMoves.add(position + forward(1) + left(1));
        }
        potentialCapture = game[position + forward(1) + right(1)];
        if (potentialCapture != null && potentialCapture.getColor() != color) {
            legalMoves.add(position + forward(1) + left(1));
        }


    }

}
