package mychessgame.mainmenu;

import java.awt.event.*;
import java.util.Map;

import mychessgame.GameState.StateType;

public class MainMenuMouse extends MouseAdapter {
    private MainMenuState state;

    private static final Map<String, StateType> stringToState = Map.of(
            "Singleplayer", StateType.SINGLEPLAYER,
            "Two Players", StateType.TWOPLAYER,
            "Multiplayer", StateType.MULTIPLAYER);

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
        for (Button b : state.getButtons()) {
            if (b.isHovered(e.getX(), e.getY())) {
                state.changeState(stringToState.get(b.getText()));
            }
        }
    }
}
