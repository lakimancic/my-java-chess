package mychessgame;

import mychessgame.Figures.*;

import java.awt.event.*;
import java.util.List;

public class Mouse extends MouseAdapter {
    GamePanel panel;
    Figure selectedFigure = null;
    int prevX, prevY;

    public Mouse(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        panel.mouseX = e.getX();
        panel.mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        panel.mouseX = e.getX();
        panel.mouseY = e.getY();

        if(!panel.figures.isPromotion) gameMouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!panel.figures.isPromotion) gameMousePressed(e);
        else promotionMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!panel.figures.isPromotion) gameMouseReleased(e);
    }

    // Game Listeners

    public void gameMouseDragged(MouseEvent e) {
        if(selectedFigure != null && selectedFigure.isSelected) {
            int tileSize = GamePanel.WIDTH / 8;
            int tileX = e.getX() / tileSize;
            int tileY = e.getY() / tileSize;
            int prevTileX = prevX / tileSize;
            int prevTileY = prevY / tileSize;

            if(tileX != prevTileX || tileY != prevTileY) {
                selectedFigure.isSelected = false;
            }

            prevX = e.getX();
            prevY = e.getY();
        }
    }

    public void gameMousePressed(MouseEvent e) {
        if(e.getX() < 0 || e.getX() > GamePanel.WIDTH) return;
        if(e.getY() < 0 || e.getY() > GamePanel.HEIGHT) return;

        int tileSize = GamePanel.WIDTH / 8;
        int tileX = e.getX() / tileSize;
        int tileY = e.getY() / tileSize;

        Figure[][] grid = panel.figures.getGrid();
        Figure tileFigure = grid[tileY][tileX];

        if(tileFigure != null && tileFigure.getColor() == panel.figures.onTurn) {
            if(selectedFigure != tileFigure && selectedFigure != null) {
                selectedFigure.isSelected = false;
            }

            tileFigure.isClicked = true;
            tileFigure.isSelected = !tileFigure.isSelected;
            selectedFigure = tileFigure;

            panel.mouseX = e.getX();
            panel.mouseY = e.getY();

            prevX = e.getX();
            prevY = e.getY();
        }
    }

    public void gameMouseReleased(MouseEvent e) {
        if(selectedFigure != null) {
            selectedFigure.isClicked = false;

            int tileSize = GamePanel.WIDTH / 8;
            int tileX = e.getX() / tileSize;
            int tileY = e.getY() / tileSize;

            List<Position> moves = selectedFigure.getAvailablePositions(panel.figures);

            for(Position p : moves) {
                if(p.getX() == tileX && p.getY() == tileY && selectedFigure.getColor() == panel.figures.onTurn) {
                    selectedFigure.move(p, panel.figures);
                    selectedFigure.isSelected = false;

                    if(!panel.figures.isPromotion) panel.figures.switchTurn();
                }
            }
        }
    }

    // Promotion Listener

    public void promotionMousePressed(MouseEvent e) {
        int tileSize = GamePanel.WIDTH / 8;
        int k = panel.figures.getPreviousMove().to.getY() == 7 ? -1 : 1;
        Figure[][] grid = panel.figures.getGrid();

        for(int i=0;i<panel.figures.promotions.length;i++) {
            int posX = panel.figures.getPreviousMove().to.getX(), posY = ( panel.figures.getPreviousMove().to.getY() + k * i );
            if(
                e.getX() >= tileSize * posX && e.getX() < tileSize * (posX + 1) &&
                e.getY() >= tileSize * posY && e.getY() < tileSize * (posY + 1)
            ) {
                Pawn pawn = (Pawn)grid[panel.figures.getPreviousMove().to.getY()][panel.figures.getPreviousMove().to.getX()];
                pawn.promote(panel.figures, panel.figures.promotions[i]);

                return;
            }
        }
    }
}
