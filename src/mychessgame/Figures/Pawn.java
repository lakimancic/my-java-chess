package mychessgame.Figures;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Figure {

    public Pawn(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.PAWN;
        this.pos = pos;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();

        FigureColor[][] grid = figures.getGrid();

        int k = color == FigureColor.WHITE ? -1 : 1;

        if(pos.y + k >= 0 && pos.y + k < 8 && grid[pos.y + k][pos.x] == null) {
            moves.add(new Position(pos.x, pos.y + k));
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x + 1 < 8 && grid[pos.y + k][pos.x + 1] != null && grid[pos.y + k][pos.x + 1] != color) {
            moves.add(new Position(pos.x + 1, pos.y + k));
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x - 1 >= 0 && grid[pos.y + k][pos.x - 1] != null && grid[pos.y + k][pos.x - 1] != color) {
            moves.add(new Position(pos.x - 1, pos.y + k));
        }

        if(!isMoved && pos.y + 2*k >= 0 && pos.y + 2*k < 8 && grid[pos.y + 2*k][pos.x] == null) {
            moves.add(new Position(pos.x, pos.y + 2*k));
        }

        return moves;
    }
}
