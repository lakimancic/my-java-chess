package mychessgame.Figures;

import java.util.List;
import java.util.ArrayList;

public class Pawn extends Figure {

    public Pawn(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.PAWN;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = new ArrayList<Position>();

        Figure[][] grid = board.getGrid();

        int k = color == FigureColor.WHITE ? -1 : 1;

        if(pos.y + k >= 0 && pos.y + k < 8 && grid[pos.y + k][pos.x] == null) {
            moves.add(new Position(pos.x, pos.y + k));
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x + 1 < 8 && grid[pos.y + k][pos.x + 1] != null && grid[pos.y + k][pos.x + 1].getColor() != color) {
            moves.add(new Position(pos.x + 1, pos.y + k));
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x - 1 >= 0 && grid[pos.y + k][pos.x - 1] != null && grid[pos.y + k][pos.x - 1].getColor() != color) {
            moves.add(new Position(pos.x - 1, pos.y + k));
        }

        if(!isMoved && pos.y + 2*k >= 0 && pos.y + 2*k < 8 && grid[pos.y + 2*k][pos.x] == null) {
            moves.add(new Position(pos.x, pos.y + 2*k));
        }

        if(checkEnPassant()) {
            moves.add(new Position(board.getPreviousMove().from.getX(), pos.y + k));
        }

        return moves;
    }

    public boolean checkEnPassant() {
        return board.getPreviousMove() != null && board.getPreviousMove().type == FigureType.PAWN && 
        Math.abs(board.getPreviousMove().from.getY() - board.getPreviousMove().to.getY()) == 2 &&
        Math.abs(board.getPreviousMove().from.getX() - pos.getX()) == 1 && pos.getY() == board.getPreviousMove().to.getY();
    }

    public void promote(FigureType type) {
        Figure[][] grid = board.getGrid();

        grid[pos.getY()][pos.getX()] = Figure.factory(type, color, board, pos);
        board.setPromotion(false);

        checkEndgame();

        board.switchTurn();
    }
}