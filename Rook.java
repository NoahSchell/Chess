import javax.swing.*;

public class Rook extends Piece {
    private boolean canCastle;

    public Rook(int x, boolean col) {
        super(x, col);
        canCastle = true;
        if (color)
            image = new ImageIcon("Pieces/WhiteRook.png");
        if (!color)
            image = new ImageIcon("Pieces/BlackRook.png");
    }

    public void setCanCastle(boolean a)
    {
        canCastle = a;
    }

    public void setLegalMoves() {
        // gets the spaces below position
        int pointer = position;
        while (pointer + down() <= 63) {
            if (game[pointer + down()] == null) // if the space below is empty...
            {
                legalMoves.add(pointer + down()); // add it to legal moves
                pointer += down(); // update pointer
            } else // if the space is not empty
            {
                if (game[pointer + down()].getColor() != color) // if the piece is of the opposite color
                    legalMoves.add(pointer + down()); // it can capture
                break; // but it can't go further
            }
        }

        // gets the spaces above position
        pointer = position;
        while (pointer + up() >= 0) {
            if (game[pointer + up()] == null) // if the space above is empty
            {
                legalMoves.add(pointer + up()); // add it
                pointer += up(); // update pointer
            } else // if the space is not empty
            {
                if (game[pointer + up()].getColor() != color) // if the piece is of the opposite color
                    legalMoves.add(pointer + up());
                break; // but go no further
            }
        }

        // gets the spaces to the right of position
        pointer = position;
        int start = getColumn(position);
        for (int x = start; x < 7; x++) // takes the starting column and goes to the right adding all possible legal
                                        // moves
        {
            if (game[pointer + 1] == null) // if the space to the right is empty
            {
                legalMoves.add(pointer + 1); // add it
                pointer += 1; // increment where you look
            } else // if the space is not empty
            {
                if (game[pointer + 1].getColor() != color) // and its of the opposite color
                    legalMoves.add(pointer + 1); // add it
                break; // but get out
            }

        }

        // gets spaces to the left of position
        pointer = position;
        for (int x = start; x > 0; x--) // starts in column and moves left until leftmost column
        {
            if (game[pointer - 1] == null) // if the space is empty
            {
                legalMoves.add(pointer - 1); // add it
                pointer -= 1; // decrement pointer
            } else // if the space is not empty
            {
                if (game[pointer - 1].getColor() != color) // if its of the opposite color
                    legalMoves.add(pointer - 1); // you can capture
                break; // but go no further
            }
        }

        if (canCastle) {
            // set up castling here
        }

        cleanMoves();
    }
}
