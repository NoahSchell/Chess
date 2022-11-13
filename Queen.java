public class Queen extends Piece{

    public Queen(int x, boolean col)
    {
        super(x, col);
    }

    public void setLegalMoves()
    {
        // the legal moves for a queen are the combination of a bishop and rook in that same spot!
        Bishop temp = new Bishop(position, color);
        temp.setLegalMoves();
        legalMoves.addAll(temp.getLegalMoves());
        Rook temp2 = new Rook(position, color);
        temp2.setLegalMoves();
        legalMoves.addAll(temp2.getLegalMoves());
        cleanMoves();
    }
}
