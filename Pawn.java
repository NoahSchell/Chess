public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn(int posx, int posy, boolean c) {
        super(posx, posy, c);
        firstMove = true;
    }

    public void setLegalMoves() {
        int moves = 1;
        if (firstMove)
            moves = 2;
        for (int x = 0; x < moves; x++) {

        }

    }
}
