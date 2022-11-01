package mychessgame.mainmenu;

import java.awt.event.*;

import mychessgame.GameState.StateType;

public class MainMenuMouse extends MouseAdapter {
    private MainMenuState state;

    public MainMenuMouse(MainMenuState state) {
        this.state = state;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        state.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        state.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Button b : state.getButtons()) {
            if(b.isHovered(e.getX(), e.getY())) {
                state.changeState(StateType.TWOPLAYER);
            }
        }
    }
}
