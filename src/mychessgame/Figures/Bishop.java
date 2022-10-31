package mychessgame.Figures;

import java.util.List;

public class Bishop extends Figure {

    public Bishop(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.BISHOP;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = Moves.getDiagonalMoves(pos, color, board);

        return moves;
    }
}