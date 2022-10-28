package mychessgame.Figures;

import java.awt.*;
import java.awt.geom.*;
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

    public void renderCheck(Graphics g, int tileSize, Figures figures) {
        if(isUnderCheck(figures)) {
            Point2D center = new Point2D.Float(pos.x * tileSize + (float)tileSize / 2, pos.y * tileSize + (float)tileSize / 2);
            float radius = (float)tileSize * 1.3f / 2;
            float[] dist = { 0.f, 0.25f, 1.f};
            Color[] colors = {
                Color.RED, new Color(231, 0, 0), new Color(169, 0, 0, 0)
            };
            RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(paint);
            g2d.fillRect(pos.x * tileSize, pos.y * tileSize, tileSize, tileSize);
        }
    }

    @Override
    public List<Position> getAvailablePositions(Figures figures) {
        List<Position> moves = new ArrayList<Position>();
        Position temp = null;

        Figure[][] grid = figures.getGrid();

        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(i == 0 && j == 0) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] != null && grid[pos.y + j][pos.x + i].getColor() == color) continue;
                
                temp = new Position(pos.x + i, pos.y + j);
                if(isValidMove(temp, figures)) moves.add(temp);
            }
        }

        return moves;
    }

    // Is King under Check
    public boolean isUnderCheck(Figures figures) {
        return 
            isUnderCheckByKing(figures) ||
            isUnderCheckByPawn(figures) ||
            isUnderCheckByKnight(figures) ||
            isUnderCheckByStraightLine(figures) ||
            isUnderCheckByDiagonalLine(figures);
    }

    private boolean isUnderCheckByKing(Figures figures) {
        Figure[][] grid = figures.getGrid();

        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(i == 0 && j == 0) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] == null) continue;

                if(grid[pos.y + j][pos.x + i].type == FigureType.KING) return true;
            }
        }

        return false;
    }

    private boolean isUnderCheckByPawn(Figures figures) {
        Figure[][] grid = figures.getGrid();

        int k = color == FigureColor.WHITE ? -1 : 1;

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x + 1 < 8 && 
            grid[pos.y + k][pos.x + 1] != null && grid[pos.y + k][pos.x + 1].type == FigureType.PAWN && grid[pos.y + k][pos.x + 1].getColor() != color
        ) {
            return true;
        }

        if(pos.y + k >= 0 && pos.y + k < 8 && pos.x - 1 >= 0 && 
            grid[pos.y + k][pos.x - 1] != null && grid[pos.y + k][pos.x - 1].type == FigureType.PAWN && grid[pos.y + k][pos.x - 1].getColor() != color
        ) {
            return true;
        }

        return false;
    }

    private boolean isUnderCheckByKnight(Figures figures) {
        Figure[][] grid = figures.getGrid();

        for(int i=-2;i<=2;i++) {
            if(i == 0) continue;

            for(int j=-2;j<=2;j++) {
                if(j == 0 || Math.abs(j) == Math.abs(i)) continue;
                if(pos.x + i < 0 || pos.x + i >= 8) continue;
                if(pos.y + j < 0 || pos.y + j >= 8) continue;
                if(grid[pos.y + j][pos.x + i] == null) continue;

                if(grid[pos.y + j][pos.x + i].type == FigureType.KNIGHT && grid[pos.y + j][pos.x + i].getColor() != color) return true;
            }
        }

        return false;
    }

    private boolean isUnderCheckByStraightLine(Figures figures) {
        Figure[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1;i<8 && (grid[i][pos.getX()] == null || grid[i][pos.getX()].getColor() != getColor());i++) {
            if(grid[i][pos.getX()] != null) {
                if(grid[i][pos.getX()].type == FigureType.QUEEN || grid[i][pos.getX()].type == FigureType.ROOK) {
                    return true;
                }
                else{
                    break;
                }
            }
        }

        for(int i=pos.getY() - 1;i>=0 && (grid[i][pos.getX()] == null || grid[i][pos.getX()].getColor() != getColor());i--) {
            if(grid[i][pos.getX()] != null) {
                if(grid[i][pos.getX()].type == FigureType.QUEEN || grid[i][pos.getX()].type == FigureType.ROOK) {
                    return true;
                }
                else{
                    break;
                }
            }
        }

        for(int i=pos.getX() + 1;i<8 && (grid[pos.getY()][i] == null || grid[pos.getY()][i].getColor() != getColor());i++) {
            if(grid[pos.getY()][i] != null) {
                if(grid[pos.getY()][i].type == FigureType.QUEEN || grid[pos.getY()][i].type == FigureType.ROOK) {
                    return true;
                }
                else{
                    break;
                }
            }
        }

        for(int i=pos.getX() - 1;i>=0 && (grid[pos.getY()][i] == null || grid[pos.getY()][i].getColor() != getColor());i--) {
            if(grid[pos.getY()][i] != null) {
                if(grid[pos.getY()][i].type == FigureType.QUEEN || grid[pos.getY()][i].type == FigureType.ROOK) {
                    return true;
                }
                else{
                    break;
                }
            }
        }

        return false;
    }

    private boolean isUnderCheckByDiagonalLine(Figures figures) {
        Figure[][] grid = figures.getGrid();

        for(int i=pos.getY() + 1, j=pos.getX() + 1;i<8 && j<8 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i++,j++) {
            if(grid[i][j] != null) {
                if(grid[i][j].type == FigureType.QUEEN || grid[i][j].type == FigureType.BISHOP) {
                    return true;
                }
                else {
                    break;
                }
            }
        }

        for(int i=pos.getY() + 1, j=pos.getX() - 1;i<8 && j>=0 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i++,j--) {
            if(grid[i][j] != null) {
                if(grid[i][j].type == FigureType.QUEEN || grid[i][j].type == FigureType.BISHOP) {
                    return true;
                }
                else {
                    break;
                }
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() + 1;i>=0 && j<8 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i--,j++) {
            if(grid[i][j] != null) {
                if(grid[i][j].type == FigureType.QUEEN || grid[i][j].type == FigureType.BISHOP) {
                    return true;
                }
                else {
                    break;
                }
            }
        }

        for(int i=pos.getY() - 1, j=pos.getX() - 1;i>=0 && j>=0 && (grid[i][j] == null || grid[i][j].getColor() != getColor());i--,j--) {
            if(grid[i][j] != null) {
                if(grid[i][j].type == FigureType.QUEEN || grid[i][j].type == FigureType.BISHOP) {
                    return true;
                }
                else {
                    break;
                }
            }
        }

        return false;
    }
}
