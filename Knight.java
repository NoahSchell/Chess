import javax.swing.*;

public class Knight extends Piece {
    public Knight(int pos, boolean c) {
        super(pos, c);
        if (color){
            image = new ImageIcon("Pieces/WhiteKnight.png");
            fenLetter = 'N';
        }
        if (!color){
            image = new ImageIcon("Pieces/BlackKnight.png");
            fenLetter = 'n';
        }
    }

    public void setPseudoLegalMoves() {

        // FORWARD TWO, LEFT AND RIGHT
        try {
            if(game[position + forward(2) + right(1)] == null || game[position + forward(2) + right(1)].color != color)
                if(position / 8 != (position + forward(2) + right(1))/8)
                    pseudoLegalMoves.add(position + forward(2) + right(1));
        } catch (IllegalStateException e) {
        }
        try {
            if(game[position + forward(2) + left(1)] == null || game[position + forward(2) + left(1)].color != color)
                if(position / 8 != (position + forward(2) + left(1))/8)
                    pseudoLegalMoves.add(position + forward(2) + left(1));
        } catch (IllegalStateException e) {
        }
        // BACKWARD TWO, LEFT AND RIGHT
        try {
            if(game[position + backward(2) + right(1)] == null || game[position + backward(2) + right(1)].color != color)
                if(position / 8 != (position + backward(2) + right(1))/8)
                    pseudoLegalMoves.add(position + backward(2) + right(1));
        } catch (IllegalStateException e) {
        }
        try {
            if(game[position + backward(2) + left(1)] == null || game[position + backward(2) + left(1)].color != color)
                if(position / 8 != (position + backward(2) + left(1))/8)
                    pseudoLegalMoves.add(position + backward(2) + left(1));
        } catch (IllegalStateException e) {
        }
        // TWO TO THE LEFT, FORWARD AND BACKWARDS
        try {
            if(game[position + left(2) + backward(1)] == null || game[position + left(2) + backward(1)].color != color)
                if(position / 8 != (position + left(2) + backward(1))/8)
                    pseudoLegalMoves.add(position + left(2) + backward(1));
        } catch (IllegalStateException e) {
        }
        try {
            if(game[position + left(2) + forward(1)] == null || game[position + left(2) + forward(1)].color != color)
                if(position / 8 != (position + left(2) + forward(1))/8)
                    pseudoLegalMoves.add(position + left(2) + forward(1));
        } catch (IllegalStateException e) {
        }
        // TWO TO THE RIGHT, FORWARD AND BACKWARDS
        try {
            if(game[position + right(2) + backward(1)] == null || game[position + right(2) + backward(1)].color != color)
                if(position / 8 != (position + right(2) + backward(1))/8)
                    pseudoLegalMoves.add(position + right(2) + backward(1));
        } catch (IllegalStateException e) {
        }
        try {
            if(game[position + right(2) + forward(1)] == null || game[position + right(2) + forward(1)].color != color)
                if(position / 8 != (position + right(2) + forward(1))/8)
                    pseudoLegalMoves.add(position + right(2) + forward(1));
        } catch (IllegalStateException e) {
        }
    }

}
