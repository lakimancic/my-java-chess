package mychessgame.Figures;

import mychessgame.Figures.Figure.*;
import mychessgame.Images;

import java.awt.*;

public class Figures {
    Figure[][] grid;
    Move prevMove;

    public Figures(Images images) {
        grid = new Figure[8][8];

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
    }

    public Figure[][] getGrid() {
        return grid;
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

    public void setPreviousMove(Position from, Position to) {
        prevMove = new Move(from, to);
    }

    private class Move {
        public Position from;
        public Position to;

        public Move(Position from, Position to) {
            this.from = from;
            this.to = to;
        }

        public void render(Graphics g, int tileSize) {
            g.setColor(new Color( 160, 200, 38, 128));
            g.fillRect(tileSize * from.getX(), tileSize * from.getY(), tileSize, tileSize);
            g.fillRect(tileSize * to.getX(), tileSize * to.getY(), tileSize, tileSize);
        }
    }
}
