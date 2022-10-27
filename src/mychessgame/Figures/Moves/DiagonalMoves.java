package mychessgame.Figures.Moves;

import mychessgame.Figures.*;
import mychessgame.Figures.Figure.*;

import java.util.List;
import java.util.ArrayList;

public interface DiagonalMoves {
    public Position getPosition();
    public FigureColor getColor();

    default List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position pos = getPosition();

        FigureColor[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1, j=pos.getX() + 1;i<8 && j<8 && grid[i][j] != getColor();i++,j++) {
            moves.add(new Position(j, i));

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() + 1, j=pos.getX() - 1;i<8 && j>=0 && grid[i][j] != getColor();i++,j--) {
            moves.add(new Position(j, i));

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() + 1;i>=0 && j<8 && grid[i][j] != getColor();i--,j++) {
            moves.add(new Position(j, i));

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() - 1;i>=0 && j>=0 && grid[i][j] != getColor();i--,j--) {
            moves.add(new Position(j, i));

            if(grid[i][j] != null) {
                break;
            }
        }

        return moves;
    }
}
