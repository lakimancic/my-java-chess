package mychessgame.twoplayers;

import mychessgame.*;
import mychessgame.Figures.*;

import java.awt.event.*;
import java.util.List;

public class TwoPlayersMouse extends MouseAdapter {
    private TwoPlayersState state;
    private Figure selectedFigure = null;
    private int prevX, prevY;

    public TwoPlayersMouse(TwoPlayersState state) {
        this.state = state;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        state.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        state.setMousePosition(e.getX(), e.getY());

        if(!state.getBoard().checkPromotion()) gameMouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!state.getBoard().checkPromotion()) gameMousePressed(e);
        else promotionMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!state.getBoard().checkPromotion()) gameMouseReleased(e);
    }

    public void gameMouseDragged(MouseEvent e) {
        if(selectedFigure != null && selectedFigure.getSelected()) {
            int tileSize = GameFrame.WIDTH / 8;
            int tileX = e.getX() / tileSize;
            int tileY = e.getY() / tileSize;
            int prevTileX = prevX / tileSize;
            int prevTileY = prevY / tileSize;

            if(tileX != prevTileX || tileY != prevTileY) {
                selectedFigure.setSelected(false);
            }

            prevX = e.getX();
            prevY = e.getY();
        }
    }

    public void gameMousePressed(MouseEvent e) {
        if(e.getX() < 0 || e.getX() > GameFrame.WIDTH) return;
        if(e.getY() < 0 || e.getY() > GameFrame.HEIGHT) return;

        int tileSize = GameFrame.WIDTH / 8;
        int tileX = e.getX() / tileSize;
        int tileY = e.getY() / tileSize;

        Figure[][] grid = state.getBoard().getGrid();
        Figure tileFigure = grid[tileY][tileX];

        if(tileFigure != null && tileFigure.getColor() == state.getBoard().whoseTurn()) {
            if(selectedFigure != tileFigure && selectedFigure != null) {
                selectedFigure.setSelected(false);
            }

            tileFigure.setClicked(true);
            tileFigure.setSelected(!tileFigure.getSelected());

            tileFigure.setClicked(true);
            selectedFigure = tileFigure;

            state.setMousePosition(e.getX(), e.getY());

            prevX = e.getX();
            prevY = e.getY();
        }
    }

    public void gameMouseReleased(MouseEvent e) {
        if(selectedFigure != null) {
            selectedFigure.setClicked(false);

            int tileSize = GameFrame.WIDTH / 8;
            int tileX = e.getX() / tileSize;
            int tileY = e.getY() / tileSize;

            List<Position> moves = selectedFigure.getAvailablePositions();

            for(Position p : moves) {
                if(p.getX() == tileX && p.getY() == tileY && selectedFigure.getColor() == state.getBoard().whoseTurn()) {
                    selectedFigure.move(p);
                    selectedFigure.setSelected(false);

                    if(!state.getBoard().checkPromotion()) state.getBoard().switchTurn();
                }
            }
        }
    }

    public void promotionMousePressed(MouseEvent e) {
        int tileSize = GameFrame.WIDTH / 8;
        int k = state.getBoard().getPreviousMove().to.getY() == 7 ? -1 : 1;
        Figure[][] grid = state.getBoard().getGrid();

        for(int i=0;i<Board.PROMOTIONS.length;i++) {
            int posX = state.getBoard().getPreviousMove().to.getX(), posY = ( state.getBoard().getPreviousMove().to.getY() + k * i );
            if(
                e.getX() >= tileSize * posX && e.getX() < tileSize * (posX + 1) &&
                e.getY() >= tileSize * posY && e.getY() < tileSize * (posY + 1)
            ) {
                Pawn pawn = (Pawn)grid[state.getBoard().getPreviousMove().to.getY()][state.getBoard().getPreviousMove().to.getX()];
                pawn.promote(Board.PROMOTIONS[i]);

                return;
            }
        }
    }
}
