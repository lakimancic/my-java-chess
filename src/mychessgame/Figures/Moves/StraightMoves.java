package mychessgame.Figures.Moves;

import mychessgame.Figures.*;
import mychessgame.Figures.Figure.*;

import java.util.List;
import java.util.ArrayList;

public interface StraightMoves {
    public Position getPosition();
    public FigureColor getColor();

    default List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position pos = getPosition();

        FigureColor[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1;i<8 && grid[i][pos.getX()] != getColor();i++) {
            moves.add(new Position(pos.getX(), i));

            if(grid[i][pos.getX()] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1;i>=0 && grid[i][pos.getX()] != getColor();i--) {
            moves.add(new Position(pos.getX(), i));

            if(grid[i][pos.getX()] != null) {
                break;
            }
        }

        for(int i=pos.getX() + 1;i<8 && grid[pos.getY()][i] != getColor();i++) {
            moves.add(new Position(i, pos.getY()));

            if(grid[pos.getY()][i] != null) {
                break;
            }
        }

        for(int i=pos.getX() - 1;i>=0 && grid[pos.getY()][i] != getColor();i--) {
            moves.add(new Position(i, pos.getY()));

            if(grid[pos.getY()][i] != null) {
                break;
            }
        }

        return moves;
    }
}
