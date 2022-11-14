import javax.swing.*;
public class Bishop extends Piece{

    public Bishop(int x, boolean col)
    {
        super(x, col);
        if(!color)
            image = new ImageIcon("PNG Files\\BlackBishop.png");
        else
            image = new ImageIcon("PNG Files\\WhiteBishop.png");
    }
    
    public void setLegalMoves()
    {
        int potentialSpace = 0; 
        // up right diagonal
        int pointer = position;
        while (getRow(pointer) > 0 && getColumn(pointer) < 7) // while the row below the 0 row and the column is left of 7 row
        {
            potentialSpace = pointer + up() + right(1); 
            if (game[potentialSpace] == null) // if nothing is to the right and up, add it. Move pointer to that space now. 
            {
                legalMoves.add(potentialSpace);
                pointer = potentialSpace;
            }
            else // if something is in game[potentialSpace]...
            {  
                //this if statement is not technically necessary, sine cleanMoves() should check this. but just to make sure. 
                if (game[potentialSpace].getColor() != color) // if the piece is a different color, add it as a possible move
                    legalMoves.add(potentialSpace);
                break; // get out of the while loop. We finished. 
            }
        }

        // up left diagonal. follows the same pattern as above
        pointer = position;
        while (getRow(pointer) > 0 && getColumn(pointer) > 0) // while the row is below 0 and the column is to the right of 0
        {
            potentialSpace = pointer + up() + left(1);
            if(game[potentialSpace] == null)
            {
                legalMoves.add(potentialSpace);
                pointer = potentialSpace;
            }
            else
            {
                if (game[potentialSpace].getColor() != color)
                    legalMoves.add(potentialSpace);
                break;
            }
        }

        // down right diagonal
        pointer = position;
        while (getRow(pointer) < 7 && getColumn(pointer) < 7) // while the row is above 7 and column is to the left of 7
        {
            potentialSpace = pointer + down() + right(1);
            if(game[potentialSpace] == null)
            {
                legalMoves.add(potentialSpace);
                pointer = potentialSpace; 
            }
            else
            {
                if(game[potentialSpace].getColor() != color)
                    legalMoves.add(potentialSpace);
                break;
            }
        }

        // down left diagonal
        pointer = position; 
        while(getRow(pointer) < 7 && getColumn(pointer) > 0) // while the row is above 7 and column is right of 0
        {
            potentialSpace = pointer + down() + left(1);
            if (game[potentialSpace] == null)
            {
                legalMoves.add(potentialSpace);
                pointer = potentialSpace;
            }
            else
            {
                if(game[potentialSpace].getColor() != color)
                    legalMoves.add(potentialSpace);
                break;
            }
        }

        //make sure we all good
        cleanMoves();
    }
}
