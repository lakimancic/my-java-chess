package mychessgame.Figures;

import java.util.List;
import java.util.ArrayList;

public class Knight extends Figure {

    public Knight(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.KNIGHT;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = new ArrayList<Position>();
        Figure[][] grid = board.getGrid();

        for(int i=-2;i<=2;i++) {
            if(i == 0) continue;

            for(int j=-2;j<=2;j++) {
                if(j == 0 || Math.abs(j) == Math.abs(i)) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] != null && grid[pos.y + j][pos.x + i].getColor() == color) continue;

                moves.add(new Position(pos.x + i, pos.y + j));
            }
        }
        return moves;
    }
}