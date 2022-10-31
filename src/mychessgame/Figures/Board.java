package mychessgame.Figures;

import java.awt.*;
import java.awt.geom.*;

import mychessgame.*;
import mychessgame.Figures.Figure.*;

public class Board {
    private Image background;
    private Image[][] figureImages;
    
    private Figure[][] grid;
    private FigureColor onTurn;
    private boolean isPromotion;
    private Move prevMove;

    public static final FigureType[] PROMOTIONS = { FigureType.QUEEN, FigureType.KNIGHT, FigureType.ROOK, FigureType.BISHOP };

    public Board() {
        grid = new Figure[8][8];
        onTurn = FigureColor.WHITE;

        initFigures(FigureColor.WHITE);
        initFigures(FigureColor.BLACK);
    }

    public void initFigures(FigureColor color) {
        grid[color == FigureColor.WHITE ? 7 : 0][0] = new Rook(this, color, new Position(0, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][7] = new Rook(this, color, new Position(7, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][1] = new Knight(this, color, new Position(1, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][6] = new Knight(this, color, new Position(6, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][2] = new Bishop(this, color, new Position(2, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][5] = new Bishop(this, color, new Position(5, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][4] = new King(this, color, new Position(4, color == FigureColor.WHITE ? 7 : 0));
        grid[color == FigureColor.WHITE ? 7 : 0][3] = new Queen(this, color, new Position(3, color == FigureColor.WHITE ? 7 : 0));
        for(int i=0;i<8;i++) {
            grid[color == FigureColor.WHITE ? 6 : 1][i] = new Pawn(this, color, new Position(i, color == FigureColor.WHITE ? 6 : 1));
        }
    }

    public void render(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        Figure selectedFigure = null;
        g.drawImage(background, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);

        if(prevMove != null) prevMove.render(g, tileSize);

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && (f.isClicked || f.isSelected)) {
                    selectedFigure = f;
                    f.renderAvailablePositions(g, tileSize, mouseX, mouseY);
                }
            }
        }

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && !f.isSelected && !f.isClicked) {
                    f.render(g, tileSize, mouseX, mouseY);
                }
            }
        }

        if(selectedFigure != null) selectedFigure.render(g, tileSize, mouseX, mouseY);

        if(isPromotion) {
            renderPromotions(g, tileSize, mouseX, mouseY);
        }
    }

    public void renderPromotions(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, tileSize * 8, tileSize * 8);

        if(prevMove != null) {
            int k = prevMove.to.getY() == 7 ? -1 : 1;
            int padding = tileSize / 10;

            for(int i=0;i<PROMOTIONS.length;i++) {
                int posX = prevMove.to.getX(), posY = ( prevMove.to.getY() + k * i );
                Position tempPos = new Position(posX, posY);
                
                if(tempPos.isTileHovered(tileSize, mouseX, mouseY)) renderHoverPromotion(g, tileSize, mouseX, mouseY, k, padding, tempPos, i);
                else renderNormalPromotion(g, tileSize, mouseX, mouseY, k, padding, tempPos, i);
            }
        }
    }

    public void renderNormalPromotion(Graphics2D g, int tileSize, int mouseX, int mouseY, int k, int padding, Position pos, int i) {
        Point2D center = new Point2D.Float(pos.getX() * tileSize + (float)tileSize / 2, pos.getY() * tileSize + (float)tileSize / 2);
        float radius = (float)tileSize * 1.3f / 2;
        float[] dist = { 0f, 1.f};
        Color[] colors = {
            new Color(176, 176, 176), new Color(128, 128, 128)
        };
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
        g.setPaint(paint);
        g.fillArc(pos.getX() * tileSize, pos.getY() * tileSize, tileSize, tileSize, 0, 360);
        g.drawImage(
            figureImages[onTurn.ordinal()][PROMOTIONS[i].ordinal()], 
            pos.getX() * tileSize + padding, 
            pos.getY() * tileSize + padding,
            tileSize - 2*padding, tileSize - 2*padding, null
        );
    }

    public void renderHoverPromotion(Graphics2D g, int tileSize, int mouseX, int mouseY, int k, int padding, Position pos, int i) {
        Point2D center = new Point2D.Float(pos.getX() * tileSize + (float)tileSize / 2, pos.getY() * tileSize + (float)tileSize / 2);
        float radius = (float)tileSize * 1.3f / 2;
        float[] dist = { 0f, 1f};
        Color[] colors = {
            new Color(176, 176, 176), new Color(214, 79, 0)
        };
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(paint);
        g2d.fillRect(pos.getX() * tileSize, pos.getY() * tileSize, tileSize, tileSize);
        g2d.drawImage(
            figureImages[onTurn.ordinal()][PROMOTIONS[i].ordinal()],
            pos.getX() * tileSize, pos.getY() * tileSize,
            tileSize, tileSize, null
        );
    }

    public void setResources(Image background, Image[][] figureImages) {
        this.background = background;
        this.figureImages = figureImages;
    }

    public Image[][] getFigureImages() {
        return figureImages;
    }

    public Figure[][] getGrid() {
        return grid;
    }

    public FigureColor whoseTurn() {
        return onTurn;
    }

    public void switchTurn() {
        onTurn = onTurn == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;
    }

    public boolean isUnderCheck(FigureColor color) {
        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && f.type == FigureType.KING && f.getColor() == color) {
                    King king = (King)f;

                    return king.isUnderCheck();
                }
            }
        }

        return false;
    }

    public boolean checkPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        this.isPromotion = promotion;
    }

    public void setPreviousMove(Position from, Position to, FigureType type) {
        this.prevMove = new Move(from, to, type);
    }

    public Move getPreviousMove() {
        return prevMove;
    }

    public boolean isCheckmated(FigureColor color) {
        int movesLeft = 0;

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && f.getColor() == color) {
                    movesLeft += f.getAvailablePositions().size();
                }
            }
        }

        return isUnderCheck(color) && movesLeft == 0;
    }

    public boolean isStalemated(FigureColor color) {
        int movesLeft = 0;

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && f.getColor() == color) {
                    movesLeft += f.getAvailablePositions().size();
                }
            }
        }

        return !isUnderCheck(color) && movesLeft == 0;
    }

    public class Move {
        public Position from;
        public Position to;
        public FigureType type;

        public Move(Position from, Position to, FigureType type) {
            this.from = from;
            this.to = to;
            this.type = type;
        }

        public void render(Graphics2D g, int tileSize) {
            g.setColor(new Color( 160, 200, 38, 128));
            g.fillRect(tileSize * from.getX(), tileSize * from.getY(), tileSize, tileSize);
            g.fillRect(tileSize * to.getX(), tileSize * to.getY(), tileSize, tileSize);
        }
    }
}
