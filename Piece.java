import java.util.*;
public class Piece {
    int x,y;
    boolean color, captured;
    ArrayList<Arrays> legalmoves = new ArrayList<>();

    public Piece(int pos1, int pos2, boolean c)
    {
        x = pos1;
        y = pos2;
        color = c;
        captured = false;
    }

    public Piece()
    {
        x = 0;
        y = 0;
        color = false;
        captured = false;
    }

}

