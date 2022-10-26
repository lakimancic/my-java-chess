package mychessgame;

import mychessgame.Figures.*;
import mychessgame.Figures.Figure.FigureColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int DELAY = 25;

    private double lastTime;
    private Timer timer;

    Mouse mouse;
    public int mouseX, mouseY;
    Images images;
    public Figures figures;
    public FigureColor onTurn;

    public GamePanel() {
        initGamePanel();
    }

    private void initGamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        lastTime = System.nanoTime();

        mouse = new Mouse(this);
        images = new Images();

        figures = new Figures(images);
        onTurn = FigureColor.WHITE;

        timer = new Timer(DELAY, this);
        timer.start();

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

        figures.render(g, WIDTH / 8, mouseX, mouseY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double now = System.nanoTime();
        double deltaTime = (now - lastTime) / 1e6;
        update(deltaTime);
        repaint();
        lastTime = now;
    }

    // Render Method
    private void render(Graphics g) {
        g.drawImage(images.background, 0, 0, getWidth(), getHeight(), this);
    }

    // Update Method
    private void update(double dt) {

    }
}
