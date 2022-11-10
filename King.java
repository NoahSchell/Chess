public class King extends Piece {

    public King(int pos, boolean c) {
        super(pos, c);
    }

    public void setLegalMoves() {

        // Cardinal Directions
        legalMoves.add(position + forward(1));
        legalMoves.add(position + backward(1));
        legalMoves.add(position + left(1));
        legalMoves.add(position + right(1));
        // Diagonal Directions
        legalMoves.add(position + forward(1) + right(1));

        return;
    }

}
