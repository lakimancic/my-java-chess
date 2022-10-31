package mychessgame.Figures;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.*;

public class King extends Figure {

    public King(Board board, FigureColor color, Position pos) {
        super(board, pos);

        this.color = color;
        this.type = FigureType.KING;
    }

    @Override
    public List<Position> getUnfilteredMoves() {
        List<Position> moves = new ArrayList<Position>();

        Figure[][] grid = board.getGrid();

        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(i == 0 && j == 0) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] != null && grid[pos.y + j][pos.x + i].getColor() == color) continue;
                
                moves.add(new Position(pos.x + i, pos.y + j));
            }
        }

        if(checkKingsideCastle()) moves.add(new Position(pos.x+2, pos.y));

        if(checkQueensideCastle()) moves.add(new Position(pos.x-2, pos.y));

        return moves;
    }

    public boolean checkKingsideCastle() {
        Figure[][] grid = board.getGrid();

        return 
            !isMoved && 
            grid[pos.y][pos.x+1] == null && grid[pos.y][pos.x+2] == null &&
            grid[pos.y][pos.x+3] != null && grid[pos.y][pos.x+3].type == FigureType.ROOK && !grid[pos.y][pos.x+3].isMoved &&
            !isPositionUnderAttack(new Position(pos.x+1, pos.y), color, board) && !isPositionUnderAttack(new Position(pos.x+2, pos.y), color, board);
    }

    public boolean checkQueensideCastle() {
        Figure[][] grid = board.getGrid();

        return 
            !isMoved && 
            grid[pos.y][pos.x-1] == null && grid[pos.y][pos.x-2] == null && grid[pos.y][pos.x-3] == null &&
            grid[pos.y][pos.x-4] != null && grid[pos.y][pos.x-4].type == FigureType.ROOK && !grid[pos.y][pos.x-4].isMoved &&
            !isPositionUnderAttack(new Position(pos.x-1, pos.y), color, board) && !isPositionUnderAttack(new Position(pos.x-1, pos.y), color, board);
    }

    public boolean isUnderCheck() {
        return isPositionUnderAttack(pos, color, board);
    }

    public void renderCheck(Graphics2D g, int tileSize) {
        if(isUnderCheck()) {
            Point2D center = new Point2D.Float(pos.x * tileSize + (float)tileSize / 2, pos.y * tileSize + (float)tileSize / 2);
            float radius = (float)tileSize * 1.3f / 2;
            float[] dist = { 0.f, 0.25f, 1.f};
            Color[] colors = {
                Color.RED, new Color(231, 0, 0), new Color(169, 0, 0, 0)
            };
            RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
            g.setPaint(paint);
            g.fillRect(pos.x * tileSize, pos.y * tileSize, tileSize, tileSize);
        }
    }
}