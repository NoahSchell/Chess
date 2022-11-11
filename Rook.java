public class Rook extends Piece 
{
    private boolean canCastle; 

    public Rook(int x, boolean col)
    {
        super(x, col);
        canCastle = true;
    }
    
    public void noCastle()
    {
        canCastle = false;
    }

    public void setLegalMoves()
    {
        int pointer = position;
        do // while the spaces below the rook are not occupied, add them to legal moves
        {
            if (game[pointer + 8] != null)
            {
                legalMoves.add(pointer + 8);
                pointer += 8;
            }
        } while (pointer + 8 < 64 && game[pointer + 8] != null);

        pointer = position; 
        do // while the spaces above the rook are not occupied, add them to legal moves
        {
            if (game[pointer - 8] != null)
            {
                legalMoves.add(pointer - 8);
                pointer -= 8; 
            }
            
        } while (pointer - 8 > 0 && game[pointer - 8] != null);

        pointer = position;
        // add right and left legal moves 

        if (canCastle)
        {
            // set up castling here
        }


    }
}
