package mychessgame.Figures;

import mychessgame.Figures.Moves.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Queen extends Figure implements StraightMoves, DiagonalMoves {

    public Queen(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.PAWN;
        this.pos = pos;
        this.isSelected = false;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        moves.addAll(StraightMoves.super.getAvailablePositions(figures));
        moves.addAll(DiagonalMoves.super.getAvailablePositions(figures));
        return moves;
    }
}
