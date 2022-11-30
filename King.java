import javax.swing.*;
public class King extends Piece {
    public boolean hasMoved = false;
    public King(int pos, boolean c) {
        super(pos, c);
        if (color){
            image = new ImageIcon("Pieces/WhiteKing.png");
            fenLetter = 'K';
        }
        if (!color){
            image = new ImageIcon("Pieces/BlackKing.png");
            fenLetter = 'k';
        }
    }

    public void setPseudoLegalMoves() {
        // Cardinal Directions
        try {
            if (game[position + forward(1)] == null || game[position + forward(1)].color != color)
                pseudoLegalMoves.add(position + forward(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + backward(1)] == null || game[position + backward(1)].color != color)
                pseudoLegalMoves.add(position + backward(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + left(1)] == null || game[position + left(1)].color != color)
                pseudoLegalMoves.add(position + left(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + right(1)] == null || game[position + right(1)].color != color)
                pseudoLegalMoves.add(position + right(1));
        } catch (Exception e) {
        }
        // Diagonal Directions
        
        try {
            if (game[position + forward(1) + right(1)] == null || game[position + forward(1) + right(1)].color != color)
                pseudoLegalMoves.add(position + forward(1) + right(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + forward(1) + left(1)] == null || game[position + forward(1) + left(1)].color != color)
                pseudoLegalMoves.add(position + forward(1) + left(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + backward(1) + right(1)] == null || game[position + backward(1) + right(1)].color != color)
                pseudoLegalMoves.add(position + backward(1) + right(1));
        } catch (Exception e) {
        }
        try {
            if (game[position + backward(1) + left(1)] == null || game[position + backward(1) + left(1)].color != color)
                pseudoLegalMoves.add(position + backward(1) + left(1));
        } catch (Exception e) {
        }
        
        // castling
        // if the king can castle in a direction, add the appropriate castling square to its moves
        if(color && canCastleKingSide())
            pseudoLegalMoves.add(62); // white kingside castling square is 62
        if(!color && canCastleKingSide())
            pseudoLegalMoves.add(6); // black kingside castling square is 6 
        if(color && canCastleQueenSide())
            pseudoLegalMoves.add(58); // white queenside castling square is 58
        if(!color && canCastleQueenSide())
            pseudoLegalMoves.add(2); // black queenside castling square is 2
    }
    
    // returns true if the king is in check(any legal move is a capture of our king)
    public boolean isInCheck()
    {
        if(color)
            return ChessBoard.blackSquares().contains(position);
        return ChessBoard.whiteSquares().contains(position);
    }

}

