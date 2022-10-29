package mychessgame.Figures;

import mychessgame.Figures.Figure.*;
import mychessgame.Images;

import java.awt.*;
import java.awt.geom.*;

public class Figures {
    Figure[][] grid;
    Images images;
    Move prevMove;
    public int moveCounter;
    public boolean isPromotion;
    public FigureColor onTurn;
    public String tempLogInfo;

    public final FigureType[] promotions = { FigureType.QUEEN, FigureType.KNIGHT, FigureType.ROOK, FigureType.BISHOP };

    public Figures(Images images) {
        grid = new Figure[8][8];
        this.images = images;
        moveCounter = 0;
        onTurn = FigureColor.WHITE;
        isPromotion = false;

        // // White Figures
        grid[7][0] = new Rook(FigureColor.WHITE, images.wRook, new Position(0, 7));
        grid[7][7] = new Rook(FigureColor.WHITE, images.wRook, new Position(7, 7));
        grid[7][1] = new Knight(FigureColor.WHITE, images.wKnight, new Position(1, 7));
        grid[7][6] = new Knight(FigureColor.WHITE, images.wKnight, new Position(6, 7));
        grid[7][2] = new Bishop(FigureColor.WHITE, images.wBishop, new Position(2, 7));
        grid[7][5] = new Bishop(FigureColor.WHITE, images.wBishop, new Position(5, 7));
        grid[7][4] = new King(FigureColor.WHITE, images.wKing, new Position(4, 7));
        grid[7][3] = new Queen(FigureColor.WHITE, images.wQueen, new Position(3, 7));
        for(int i=0;i<8;i++) {
            grid[6][i] = new Pawn(FigureColor.WHITE, images.wPawn, new Position(i, 6));
        }

        // // Black Figures
        grid[0][0] = new Rook(FigureColor.BLACK, images.bRook, new Position(0, 0));
        grid[0][7] = new Rook(FigureColor.BLACK, images.bRook, new Position(7, 0));
        grid[0][1] = new Knight(FigureColor.BLACK, images.bKnight, new Position(1, 0));
        grid[0][6] = new Knight(FigureColor.BLACK, images.bKnight, new Position(6, 0));
        grid[0][2] = new Bishop(FigureColor.BLACK, images.bBishop, new Position(2, 0));
        grid[0][5] = new Bishop(FigureColor.BLACK, images.bBishop, new Position(5, 0));
        grid[0][4] = new King(FigureColor.BLACK, images.bKing, new Position(4, 0));
        grid[0][3] = new Queen(FigureColor.BLACK, images.bQueen, new Position(3, 0));
        for(int i=0;i<8;i++) {
            grid[1][i] = new Pawn(FigureColor.BLACK, images.bPawn, new Position(i, 1));
        }

        // Log
        System.out.println("Game Started!");
    }

    public void render(Graphics g, int tileSize, int mouseX, int mouseY) {
        Figure selectedFigure = null;

        if(prevMove != null) prevMove.render(g, tileSize);

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && (f.isClicked || f.isSelected)) {
                    selectedFigure = f;
                    f.renderAvailablePositions(g, this, tileSize, mouseX, mouseY);
                }
            }
        }

        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && !f.isClicked && !f.isSelected) {
                    f.render(g, tileSize, mouseX, mouseY, this);
                }
            }
        }

        if(selectedFigure != null) selectedFigure.render(g, tileSize, mouseX, mouseY, this);

        if(isPromotion) {
            renderPromotions(g, tileSize, mouseX, mouseY);
        }
    }

    public Figure[][] getGrid() {
        return grid;
    }

    public void switchTurn() {
        onTurn = onTurn == FigureColor.BLACK ? FigureColor.WHITE : FigureColor.BLACK;
    }

    public boolean isChecked(FigureColor color) {
        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && f.type == FigureType.KING && f.getColor() == color) {
                    King king = (King)f;

                    return king.isUnderCheck(this);
                }
            }
        }

        return false;
    }

    public int numberOfMovesLeft(FigureColor color) {
        int sum = 0;
        for(Figure[] fs : grid) {
            for(Figure f : fs) {
                if(f != null && f.getColor() == color) sum += f.getAvailablePositions(this).size();
            }
        }
        return sum;
    }

    public boolean isCheckmated(FigureColor color) {
        return isChecked(color) && numberOfMovesLeft(color) == 0;
    }

    public boolean isStalemated(FigureColor color) {
        return !isChecked(color) && numberOfMovesLeft(color) == 0;
    }

    public void setPreviousMove(Position from, Position to, FigureType type) {
        prevMove = new Move(from, to, type);
    }

    public Move getPreviousMove() {
        return prevMove;
    }

    public Image getPromotionFigureImage(FigureType type) {
        switch(type) {
            case QUEEN: return onTurn == FigureColor.WHITE ? images.wQueen : images.bQueen;
            case KNIGHT: return onTurn == FigureColor.WHITE ? images.wKnight : images.bKnight;
            case ROOK: return onTurn == FigureColor.WHITE ? images.wRook : images.bRook;
            case BISHOP: return onTurn == FigureColor.WHITE ? images.wBishop : images.bBishop;
            default: return null;
        }
    }

    private void renderPromotions(Graphics g, int tileSize, int mouseX, int mouseY) {
        g.setColor(new Color(0, 0, 0, 128));
            g.fillRect(0, 0, tileSize * 8, tileSize * 8);

            if(prevMove != null) {
                int k = prevMove.to.getY() == 7 ? -1 : 1;
                int padding = tileSize / 10;

                for(int i=0;i<promotions.length;i++) {
                    int posX = prevMove.to.getX(), posY = ( prevMove.to.getY() + k * i );
                    if(
                        mouseX >= tileSize * posX && mouseX < tileSize * (posX + 1) &&
                        mouseY >= tileSize * posY && mouseY < tileSize * (posY + 1)
                    ) {
                        Point2D center = new Point2D.Float(posX * tileSize + (float)tileSize / 2, posY * tileSize + (float)tileSize / 2);
                        float radius = (float)tileSize * 1.3f / 2;
                        float[] dist = { 0f, 1f};
                        Color[] colors = {
                            new Color(176, 176, 176), new Color(214, 79, 0)
                        };
                        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setPaint(paint);
                        g2d.fillRect(posX * tileSize, posY * tileSize, tileSize, tileSize);
                        g2d.drawImage(
                            getPromotionFigureImage(promotions[i]), 
                            posX * tileSize, posY * tileSize,
                            tileSize, tileSize, null
                        );
                    }
                    else {
                        Point2D center = new Point2D.Float(posX * tileSize + (float)tileSize / 2, posY * tileSize + (float)tileSize / 2);
                        float radius = (float)tileSize * 1.3f / 2;
                        float[] dist = { 0f, 1.f};
                        Color[] colors = {
                            new Color(176, 176, 176), new Color(128, 128, 128)
                        };
                        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setPaint(paint);
                        g2d.fillArc(posX * tileSize, posY * tileSize, tileSize, tileSize, 0, 360);
                        g2d.drawImage(
                            getPromotionFigureImage(promotions[i]), 
                            posX * tileSize + padding, 
                            posY * tileSize + padding,
                            tileSize - 2*padding, tileSize - 2*padding, null
                        );
                    }
                }
            }
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

        public void render(Graphics g, int tileSize) {
            g.setColor(new Color( 160, 200, 38, 128));
            g.fillRect(tileSize * from.getX(), tileSize * from.getY(), tileSize, tileSize);
            g.fillRect(tileSize * to.getX(), tileSize * to.getY(), tileSize, tileSize);
        }
    }
}
