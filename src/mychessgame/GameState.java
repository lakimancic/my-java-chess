package mychessgame;

import java.awt.*;
import java.awt.event.*;

import mychessgame.mainmenu.MainMenuState;
import mychessgame.multiplayer.MultiplayerState;
import mychessgame.twoplayers.TwoPlayersState;

public abstract class GameState {
    protected GamePanel panel;

    protected MouseAdapter mouseAdapter;
    protected KeyAdapter keyAdapter;

    public GameState(GamePanel panel) {
        this.panel = panel;

        loadResources();
    }

    public abstract void render(Graphics2D g);

    public abstract void update(double dt);

    public abstract void loadResources();

    protected void addEventListeners() {
        if (mouseAdapter != null)
            panel.addMouseListener(mouseAdapter);
        if (mouseAdapter != null)
            panel.addMouseMotionListener(mouseAdapter);
        if (keyAdapter != null)
            panel.addKeyListener(keyAdapter);
    }

    protected void removeEventListeners() {
        if (mouseAdapter != null)
            panel.removeMouseListener(mouseAdapter);
        if (mouseAdapter != null)
            panel.removeMouseMotionListener(mouseAdapter);
        if (keyAdapter != null)
            panel.removeKeyListener(keyAdapter);
    }

    public void changeState(StateType type) {
        panel.changeState(type);
    }

    public static GameState factory(GamePanel panel, StateType type) {
        switch (type) {
            case MAINMENU:
                return new MainMenuState(panel);
            case SINGLEPLAYER:
                return null;
            case TWOPLAYER:
                return new TwoPlayersState(panel);
            case MULTIPLAYER:
                return new MultiplayerState(panel);
            default:
                return null;
        }
    }

    public enum StateType {
        MAINMENU,
        SINGLEPLAYER,
        TWOPLAYER,
        MULTIPLAYER
    }
}
