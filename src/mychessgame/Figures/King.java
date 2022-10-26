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
        return moves;
    }
}
