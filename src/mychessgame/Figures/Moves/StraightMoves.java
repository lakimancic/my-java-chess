package mychessgame.Figures.Moves;

import mychessgame.Figures.*;

import java.util.List;
import java.util.ArrayList;

public interface StraightMoves {
    default List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        return moves;
    }
}
