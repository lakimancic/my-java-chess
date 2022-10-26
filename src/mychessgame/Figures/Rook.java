package mychessgame.Figures;

import mychessgame.Figures.Moves.StraightMoves;

import java.awt.*;
import java.util.List;

public class Rook extends Figure implements StraightMoves {

    public Rook(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.ROOK;
        this.pos = pos;
        this.isSelected = false;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        return StraightMoves.super.getAvailablePositions(figures);
    }
}
