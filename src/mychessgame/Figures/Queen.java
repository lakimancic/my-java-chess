package mychessgame.Figures;

import java.util.List;

public class Queen extends Figure {

    public Queen(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.QUEEN;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = Moves.getDiagonalMoves(pos, color, board);
        moves.addAll(Moves.getStraightMoves(pos, color, board));
        
        return moves;
    }
}