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
        int start = getColumn(position);
        for (int x = start; x < 8; x++) // takes the starting column and goes to the right adding all possible legal moves
        {
            if(game[pointer + 1] != null)
            {
                legalMoves.add(pointer + 1);
                pointer += 1; 
            }
            else 
                break; // get out of the for loop once you hit a piece
        }

        pointer = position;
        for(int x = start; x > 1; x--) // gets the legal moves to the left
        {
            if(game[pointer - 1] != null)
            {
                legalMoves.add(pointer - 1);
                pointer -=1;
            }
            else   
                break; // get out of the for loop once you hit a piece
        }

        if (canCastle)
        {
            // set up castling here
        }

        cleanMoves();
    }
}
