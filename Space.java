public class Space {
    private int x, y; // describes position on the grid
    private Piece piece;
    private boolean color;

    public Space(int a, int b)
    {
        x = a;
        y = b;
        piece = null;
    }

    public Space(int a, int b, Piece p)
    {
        x = a;
        y = b;
        piece = p;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece p)
    {
        piece = p;
    }
}
