package mychessgame.Figures.Moves;

import mychessgame.Figures.*;
import mychessgame.Figures.Figure.*;

import java.util.List;
import java.util.ArrayList;

public interface StraightMoves {
    public Position getPosition();
    public boolean isValidMove(Position newPos, Figures figures);
    public FigureColor getColor();

    default List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position temp = null;
        Position pos = getPosition();

        Figure[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1;i<8 && (grid[i][pos.getX()] == null || grid[i][pos.getX()].getColor() != getColor());i++) {
            temp = new Position(pos.getX(), i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][pos.getX()] != null) {
                break;
            }
        }

        for(int i=pos.getY() - 1;i>=0 && (grid[i][pos.getX()] == null || grid[i][pos.getX()].getColor() != getColor());i--) {
            temp = new Position(pos.getX(), i);
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[i][pos.getX()] != null) {
                break;
            }
        }

        for(int i=pos.getX() + 1;i<8 && (grid[pos.getY()][i] == null || grid[pos.getY()][i].getColor() != getColor());i++) {
            temp = new Position(i, pos.getY());
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[pos.getY()][i] != null) {
                break;
            }
        }

        for(int i=pos.getX() - 1;i>=0 && (grid[pos.getY()][i] == null || grid[pos.getY()][i].getColor() != getColor());i--) {
            temp = new Position(i, pos.getY());
            if(isValidMove(temp, figures)) moves.add(temp);

            if(grid[pos.getY()][i] != null) {
                break;
            }
        }

        return moves;
    }
}
