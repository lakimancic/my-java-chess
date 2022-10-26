package mychessgame.Figures;

import java.util.List;
import java.awt.*;

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

    public void render(Graphics g, int tileSize, int mouseX, int mouseY) {
        if(isClicked || isSelected) {
            Graphics2D g2d = (Graphics2D)g;
            g.setColor(new Color(20, 86, 30, 128));
            g.fillRect(tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g.drawImage(image, tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g.drawImage(image, mouseX - tileSize / 2, mouseY - tileSize / 2, tileSize, tileSize, null);
        } else {
            g.drawImage(image, tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
        }
    }

    public void renderAvailablePositions(Graphics g, Figures figures, int tileSize) {
        List<Position> moves = getAvailablePositions(figures);

        g.setColor(new Color(20, 86, 30, 128));

        int R = tileSize / 10;

        for(Position p : moves) {
            g.fillArc(
                tileSize * p.getX() + tileSize / 2 - R,
                tileSize * p.getY() + tileSize / 2 - R,
                2 * R, 2 * R, 0, 360
            );
        }
    }

    public void move(Position newPos) {
        pos = newPos;
    }

    public FigureColor getColor() {
        return color;
    }
}
