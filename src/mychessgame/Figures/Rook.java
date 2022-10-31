package mychessgame.Figures;

import java.util.List;

public class Rook extends Figure {

    public Rook(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.ROOK;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = Moves.getStraightMoves(pos, color, board);

        return moves;
    }
}