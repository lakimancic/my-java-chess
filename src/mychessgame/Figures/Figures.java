package mychessgame.Figures;

import mychessgame.Figures.Figure.*;
import mychessgame.Images;

import java.util.List;

import java.util.ArrayList;
import java.awt.*;

public class Figures {
    List<Figure> figures;

    public Figures(Images images) {
        figures = new ArrayList<Figure>();

        // White Figures
        figures.add(new Rook(FigureColor.WHITE, images.wRook, new Position(0, 7)));
        figures.add(new Rook(FigureColor.WHITE, images.wRook, new Position(7, 7)));
        figures.add(new Knight(FigureColor.WHITE, images.wKnight, new Position(1, 7)));
        figures.add(new Knight(FigureColor.WHITE, images.wKnight, new Position(6, 7)));
        figures.add(new Bishop(FigureColor.WHITE, images.wBishop, new Position(2, 7)));
        figures.add(new Bishop(FigureColor.WHITE, images.wBishop, new Position(5, 7)));
        figures.add(new King(FigureColor.WHITE, images.wKing, new Position(4, 7)));
        figures.add(new Queen(FigureColor.WHITE, images.wQueen, new Position(3, 7)));
        for(int i=0;i<8;i++) {
            figures.add(new Pawn(FigureColor.WHITE, images.wPawn, new Position(i, 6)));
        }

        // Black Figures
        figures.add(new Rook(FigureColor.BLACK, images.bRook, new Position(0, 0)));
        figures.add(new Rook(FigureColor.BLACK, images.bRook, new Position(7, 0)));
        figures.add(new Knight(FigureColor.BLACK, images.bKnight, new Position(1, 0)));
        figures.add(new Knight(FigureColor.BLACK, images.bKnight, new Position(6, 0)));
        figures.add(new Bishop(FigureColor.BLACK, images.bBishop, new Position(2, 0)));
        figures.add(new Bishop(FigureColor.BLACK, images.bBishop, new Position(5, 0)));
        figures.add(new King(FigureColor.BLACK, images.bKing, new Position(4, 0)));
        figures.add(new Queen(FigureColor.BLACK, images.bQueen, new Position(3, 0)));
        for(int i=0;i<8;i++) {
            figures.add(new Pawn(FigureColor.BLACK, images.bPawn, new Position(i, 1)));
        }
    }

    public void render(Graphics g, int tileSize, int mouseX, int mouseY) {
        Figure selectedFigure = null;

        for(Figure f : figures) {
            if(f.isClicked || f.isSelected) {
                selectedFigure = f;
                f.renderAvailablePositions(g, this, tileSize, mouseX, mouseY);
            }
        }

        for(Figure f : figures) {
            if(!f.isClicked && !f.isSelected) {
                f.render(g, tileSize, mouseX, mouseY);
            }
        }

        if(selectedFigure != null) selectedFigure.render(g, tileSize, mouseX, mouseY);
    }

    public List<Figure> getFiguresList() {
        return figures;
    }

    public FigureColor[][] getGrid() {
        FigureColor[][] grid = new FigureColor[8][8];

        for(int i=0;i<8;i++) for(int j=0;j<8;j++) grid[i][j] = null;

        for(Figure f : figures) grid[f.pos.getY()][f.pos.getX()] = f.getColor();

        return grid;
    }

    public void takeFigureIfExists(Position p) {
        int i = 0, index = -1;

        for(Figure f : figures) {
            if(f.pos.getX() == p.getX() && f.pos.getY() == p.getY()) {
                index = i;
                break;
            }
            i++;
        }

        if(index != -1) {
            figures.remove(index);
        }
    }
}
