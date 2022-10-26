package mychessgame;

import mychessgame.Figures.*;

import java.awt.event.*;

public class Mouse extends MouseAdapter {
    GamePanel panel;
    Figure selectedFigure = null;

    public Mouse(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        panel.mouseX = e.getX();
        panel.mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() < 0 || e.getX() > GamePanel.WIDTH) return;
        if(e.getY() < 0 || e.getY() > GamePanel.HEIGHT) return;

        int tileSize = GamePanel.WIDTH / 8;
        int tileX = e.getX() / tileSize;
        int tileY = e.getY() / tileSize;

        for(Figure f : panel.figures.getFiguresList()) {
            if(f.pos.getX() == tileX && f.pos.getY() == tileY && f.getColor() == panel.onTurn) {
                f.isClicked = true;
                selectedFigure = f;

                panel.mouseX = e.getX();
                panel.mouseY = e.getY();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selectedFigure != null) {
            selectedFigure.isClicked = false;
        }
    }
}
