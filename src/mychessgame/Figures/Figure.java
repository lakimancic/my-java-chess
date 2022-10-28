package mychessgame.Figures;

import java.util.List;
import java.awt.*;
import java.awt.geom.*;

public abstract class Figure {

    public enum FigureType {
        KING, QUEEN, ROOK, KNIGHT, BISHOP, PAWN
    }

    public enum FigureColor {
        BLACK, WHITE
    }

    public Position pos;
    protected FigureType type;
    protected FigureColor color;
    protected Image image;
    public boolean isClicked, isSelected, isMoved;

    public abstract List<Position> getAvailablePositions(Figures figures);

    public void render(Graphics g, int tileSize, int mouseX, int mouseY, Figures figures) {
        if(isClicked || isSelected) {
            Graphics2D g2d = (Graphics2D)g;
            g.setColor(new Color(20, 86, 30, 128));
            g.fillRect(tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize);
            if(type == FigureType.KING) {
                King king = (King)this;
                king.renderCheck(g, tileSize, figures);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, isClicked ? 0.3f : 1.f));
            g.drawImage(image, tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            if(isClicked) g.drawImage(image, mouseX - tileSize / 2, mouseY - tileSize / 2, tileSize, tileSize, null);
        } else {
            if(type == FigureType.KING) {
                King king = (King)this;
                king.renderCheck(g, tileSize, figures);
            }
            g.drawImage(image, tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
        }
    }

    public void renderAvailablePositions(Graphics g, Figures figures, int tileSize, int mouseX, int mouseY) {
        List<Position> moves = getAvailablePositions(figures);
        Figure[][] grid = figures.getGrid();

        Graphics2D g2d = (Graphics2D)g;

        g.setColor(new Color(20, 86, 30, 128));

        int R = tileSize / 8;

        for(Position p : moves) {
            if(
                mouseX >= tileSize * p.getX() && mouseX < tileSize * (p.getX() + 1) &&
                mouseY >= tileSize * p.getY() && mouseY < tileSize * (p.getY() + 1)
            ) {
                g.fillRect(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize);
            }
            else {
                if(grid[p.getY()][p.getX()] == null) {
                    // En Passant
                    if(
                        type == FigureType.PAWN && figures.prevMove != null && p.getX() == figures.prevMove.from.getX() &&
                        Math.abs(figures.prevMove.from.getY() - figures.prevMove.to.getY()) == 2 && pos.getY() == figures.prevMove.to.getY()
                    ) {
                        double coef = 0.6;
                        Area fillArea = new Area(new Rectangle(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize));
                        fillArea.subtract(new Area(
                            new RoundRectangle2D.Double(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize, tileSize * coef, tileSize * coef)
                        ));
                        g2d.fill(fillArea);
                    }
                    else {
                        g.fillArc(
                            tileSize * p.getX() + tileSize / 2 - R,
                            tileSize * p.getY() + tileSize / 2 - R,
                            2 * R, 2 * R, 0, 360
                        );
                    }
                }
                else {
                    double coef = 0.6;
                    Area fillArea = new Area(new Rectangle(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize));
                    fillArea.subtract(new Area(
                        new RoundRectangle2D.Double(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize, tileSize * coef, tileSize * coef)
                    ));
                    g2d.fill(fillArea);
                }
            }
        }
    }

    public void move(Position newPos, Figures figures) {        
        Figure[][] grid = figures.getGrid();

        String logInfo = getSymbolOfType(type);

        if(grid[newPos.getY()][newPos.getX()] != null) {
            if(type == FigureType.PAWN) logInfo += (char)(95 + pos.getX());
            logInfo += "x";
        }
        logInfo += (char)(95 + newPos.getX());
        logInfo += (8 - newPos.getY());

        grid[newPos.getY()][newPos.getX()] = this;
        grid[pos.getY()][pos.getX()] = null;

        // Small Castle
        if(type == FigureType.KING && newPos.x - pos.x == 2) {
            grid[newPos.getY()][newPos.getX()-1] = grid[pos.getY()][pos.getX()+3];
            grid[pos.getY()][pos.getX()+3] = null;
            grid[newPos.getY()][newPos.getX()-1].isMoved = true;
            grid[newPos.getY()][newPos.getX()-1].pos.setPosition(newPos.getX()-1, newPos.getY());

            logInfo = "O-O";
        }

        // Big Castle
        if(type == FigureType.KING && pos.x - newPos.x == 2) {
            grid[newPos.getY()][newPos.getX()+1] = grid[pos.getY()][pos.getX()-4];
            grid[pos.getY()][pos.getX()-4] = null;
            grid[newPos.getY()][newPos.getX()+1].isMoved = true;
            grid[newPos.getY()][newPos.getX()+1].pos.setPosition(newPos.getX()+1, newPos.getY());

            logInfo = "O-O-O";
        }

        // En Passant
        if(
            type == FigureType.PAWN && figures.prevMove != null && newPos.getX() == figures.prevMove.from.getX() &&
            Math.abs(figures.prevMove.from.getY() - figures.prevMove.to.getY()) == 2 && pos.getY() == figures.prevMove.to.getY()
        ) {
            grid[figures.prevMove.to.getY()][figures.prevMove.to.getX()] = null;

            logInfo += "(ep)";
        }

        figures.setPreviousMove(pos, newPos, type);
        pos = newPos;
        isMoved = true;

        if(figures.isCheckmated(color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE)) {
            logInfo += "#";

            System.out.println(logInfo);
            System.out.println("Game over! Winner is: " + (color == FigureColor.WHITE ? "WHITE" : "BLACK"));
        }
        else if(figures.isStalemated(color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE)) {
            logInfo += "$";

            System.out.println(logInfo);
            System.out.println("Game over! Stalemate!");
        }
        else {
            if(figures.isChecked(color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE)) logInfo += "+";

            if(color == FigureColor.WHITE) {
                System.out.print((figures.moveCounter / 2 + 1) + ".\t" + logInfo + "\t");
            }
            else {
                System.out.println(logInfo);
            }
        }

        figures.moveCounter++;
    }
    
    public boolean isValidMove(Position newPos, Figures figures) {
        Figure[][] grid = figures.getGrid();
        Figure temp = grid[newPos.getY()][newPos.getX()];
        grid[newPos.getY()][newPos.getX()] = this;
        grid[pos.getY()][pos.getX()] = null;
        Position tempPos = pos;
        pos = newPos;

        boolean result = !figures.isChecked(color);

        pos = tempPos;
        grid[newPos.getY()][newPos.getX()] = temp;
        grid[pos.getY()][pos.getX()] = this;


        return result;
    }

    public FigureColor getColor() {
        return color;
    }

    public Position getPosition() {
        return pos;
    }

    private static String getSymbolOfType(FigureType type) {
        switch(type) {
            case PAWN: return "";
            case KING: return "K";
            case QUEEN: return "Q";
            case KNIGHT: return "N";
            case ROOK: return "R";
            case BISHOP: return "B";
            default: return "";
        }
    }
}
