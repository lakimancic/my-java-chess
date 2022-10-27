package mychessgame.Figures;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class King extends Figure {

    public King(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.KING;
        this.pos = pos;
        this.isSelected = false;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();

        FigureColor[][] grid = figures.getGrid();

        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(i == 0 && j == 0) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] == color) continue;

                moves.add(new Position(pos.x + i, pos.y + j));
            }
        }

        return moves;
    }
}
