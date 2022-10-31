package mychessgame;

import javax.swing.*;

import mychessgame.twoplayers.TwoPlayersState;

import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    private static final int DELAY = 25;

    private GameState state;

    private double lastTime;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));

        state = new TwoPlayersState(this);

        lastTime = System.nanoTime();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void render(Graphics2D g) {
        if(state != null) state.render(g);
    }

    private void update(double dt) {
        if(state != null) state.update(dt);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        render(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double now = System.nanoTime();
        double deltaTime = (now - lastTime) / 1e6;
        update(deltaTime);
        repaint();
        lastTime = now;
    }
}
