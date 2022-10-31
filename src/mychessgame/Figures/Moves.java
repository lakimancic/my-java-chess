package mychessgame.Figures;

import mychessgame.Figures.Figure.*;

import java.util.List;
import java.util.ArrayList;

public class Moves {
    public static List<Position> getDiagonalMoves(Position pos, FigureColor color, Board board) {
        List<Position> moves = new ArrayList<Position>();

        moves.addAll(getDirectedMoves(1, 1, pos, color, board));
        moves.addAll(getDirectedMoves(1, -1, pos, color, board));
        moves.addAll(getDirectedMoves(-1, 1, pos, color, board));
        moves.addAll(getDirectedMoves(-1, -1, pos, color, board));

        return moves;
    }

    public static List<Position> getStraightMoves(Position pos, FigureColor color, Board board) {
        List<Position> moves = new ArrayList<Position>();

        moves.addAll(getDirectedMoves(0, 1, pos, color, board));
        moves.addAll(getDirectedMoves(0, -1, pos, color, board));
        moves.addAll(getDirectedMoves(-1, 0, pos, color, board));
        moves.addAll(getDirectedMoves(1, 0, pos, color, board));

        return moves;
    }

    public static List<Position> getDirectedMoves(int dx, int dy, Position pos, FigureColor color, Board board) {
        List<Position> moves = new ArrayList<Position>();
        Figure[][] grid = board.getGrid();

        for(int i=pos.getY() + dy, j=pos.getX() + dx; i != (dy == 1 ? 8 : -1) && j != (dx == 1 ? 8 : -1) && (grid[i][j] == null || grid[i][j].getColor() != color); i+=dy , j+=dx ) {
            moves.add(new Position(j, i));

            if(grid[i][j] != null) {
                break;
            }
        }

        return moves;
    }
}
