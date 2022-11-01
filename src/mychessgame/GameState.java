package mychessgame;

import java.awt.*;
import java.awt.event.*;

import mychessgame.mainmenu.MainMenuState;
import mychessgame.twoplayers.TwoPlayersState;

public abstract class GameState {
    protected GamePanel panel;

    protected MouseAdapter mouseAdapter;

    public GameState(GamePanel panel) {
        this.panel = panel;

        loadResources();
    }

    public abstract void render(Graphics2D g);
    public abstract void update(double dt);

    public abstract void loadResources();

    protected void addEventListeners() {
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
    }

    protected void removeEventListeners() {
        panel.removeMouseListener(mouseAdapter);
        panel.removeMouseMotionListener(mouseAdapter);
    }

    public void changeState(StateType type) {
        panel.changeState(type);
    }

    public static GameState factory(GamePanel panel, StateType type) {
        switch(type) {
            case MAINMENU: return new MainMenuState(panel);
            case SINGLEPLAYER: return null;
            case TWOPLAYER: return new TwoPlayersState(panel);
            case MULTIPLAYER: return null;
            default: return null;
        }
    }

    public enum StateType {
        MAINMENU,
        SINGLEPLAYER,
        TWOPLAYER,
        MULTIPLAYER
    }
}
