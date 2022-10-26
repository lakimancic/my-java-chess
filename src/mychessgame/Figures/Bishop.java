package mychessgame.Figures;

import mychessgame.Figures.Moves.DiagonalMoves;

import java.awt.*;
import java.util.List;

public class Bishop extends Figure implements DiagonalMoves {

    public Bishop(FigureColor color, Image image, Position pos) {
        this.color = color;
        this.image = image;
        this.type = FigureType.BISHOP;
        this.pos = pos;
        this.isSelected = false;
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        return DiagonalMoves.super.getAvailablePositions(figures);
    }
}
