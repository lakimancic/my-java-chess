package mychessgame;

import java.awt.*;
import java.awt.event.*;

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
}
