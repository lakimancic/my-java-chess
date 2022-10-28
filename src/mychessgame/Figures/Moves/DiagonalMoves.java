package mychessgame.Figures.Moves;

import mychessgame.Figures.*;
import mychessgame.Figures.Figure.*;

import java.util.List;
import java.util.ArrayList;

public interface DiagonalMoves {
    public Position getPosition();
    public boolean isValidMove(Position newPos, Figures figures);
    public FigureColor getColor();

    default List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position temp = null;
        Position pos = getPosition();

        Figure[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1, j=pos.getX() + 1;i<8 && j<8 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i++,j++) {
            temp = new Position(j, i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() + 1, j=pos.getX() - 1;i<8 && j>=0 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i++,j--) {
            temp = new Position(j, i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() + 1;i>=0 && j<8 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i--,j++) {
            temp = new Position(j, i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][j] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() - 1;i>=0 && j>=0 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i--,j--) {
            temp = new Position(j, i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][j] != null) {
                break;
            }
        }

        return moves;
    }
}
