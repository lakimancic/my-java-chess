package mychessgame.Figures;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Figure {

    public Knight(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.KNIGHT;
        this.pos = pos;
        this.isSelected = false;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position temp = null;

        Figure[][] grid = figures.getGrid();

        for(int i=-2;i<=2;i++) {
            if(i == 0) continue;

            for(int j=-2;j<=2;j++) {
                if(j == 0 || Math.abs(j) == Math.abs(i)) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] != null && grid[pos.y + j][pos.x + i].getColor() == color) continue;

                temp = new Position(pos.x + i, pos.y + j);
                if(isValidMove(temp, figures)) moves.add(temp);
            }
        }

        return moves;
    }
}
