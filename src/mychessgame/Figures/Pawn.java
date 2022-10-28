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
        Position temp = null;

        Figure[][] grid = figures.getGrid();

        int k = color == FigureColor.WHITE ? -1 : 1;

        if(pos.y + k >= 0 && pos.y + k < 8 && grid[pos.y + k][pos.x] == null) {
            temp = new Position(pos.x, pos.y + k);
            if(isValidMove(temp, figures)) moves.add(temp);
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x + 1 < 8 && grid[pos.y + k][pos.x + 1] != null && grid[pos.y + k][pos.x + 1].getColor() != color) {
            temp = new Position(pos.x + 1, pos.y + k);
            if(isValidMove(temp, figures)) moves.add(temp);
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x - 1 >= 0 && grid[pos.y + k][pos.x - 1] != null && grid[pos.y + k][pos.x - 1].getColor() != color) {
            temp = new Position(pos.x - 1, pos.y + k);
            if(isValidMove(temp, figures)) moves.add(temp);
        }

        if(!isMoved && pos.y + 2*k >= 0 && pos.y + 2*k < 8 && grid[pos.y + 2*k][pos.x] == null) {
            temp = new Position(pos.x, pos.y + 2*k);
            if(isValidMove(temp, figures)) moves.add(temp);
        }

        // En Passant
        if(
            figures.prevMove != null && figures.prevMove.type == FigureType.PAWN && Math.abs(figures.prevMove.from.getY() - figures.prevMove.to.getY()) == 2 &&
            Math.abs(figures.prevMove.from.getX() - pos.getX()) == 1 && pos.getY() == figures.prevMove.to.getY()
        ) {
            temp = new Position(figures.prevMove.from.getX(), pos.y + k);
            if(isValidMove(temp, figures)) moves.add(temp);
        }

        return moves;
    }
}
