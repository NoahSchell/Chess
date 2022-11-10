public class Knight extends Piece {

    public Knight(int pos, boolean c) {
        super(pos, c);
    }

    public void setLegalMoves() {

        // FORWARD TWO, LEFT AND RIGHT
        legalMoves.add(position + forward(2) + right(1));
        legalMoves.add(position + forward(2) + left(1));
        // BACKWARD TWO, LEFT AND RIGHT
        legalMoves.add(position + backward(2) + left(1));
        legalMoves.add(position + backward(2) + right(1));
        // TWO TO THE LEFT, FORWARD AND BACKWARDS
        legalMoves.add(position + left(2) + forward(1));
        legalMoves.add(position + left(2) + backward(1));
        // TWO TO THE RIGHT, FORWARD AND BACKWARDS
        legalMoves.add(position + right(2) + forward(1));
        legalMoves.add(position + right(2) + backward(1));

        return;
    }

}
