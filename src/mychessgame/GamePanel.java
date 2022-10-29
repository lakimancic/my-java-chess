package mychessgame;

import mychessgame.Figures.*;

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

    public GamePanel() {
        initGamePanel();
    }

    private void initGamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        lastTime = System.nanoTime();

        mouse = new Mouse(this);
        images = new Images();

        figures = new Figures(images);

        timer = new Timer(DELAY, this);
        timer.start();

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
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

        renderCoords(g);
        figures.render(g, WIDTH / 8, mouseX, mouseY);
    }

    private void renderCoords(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int tileSize = WIDTH / 8;
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tileSize / 6));
        Color[] colors = { new Color(217, 224, 230), new Color(49, 89, 145) };
        int padding = 2;

        for(int i=0;i<8;i++) {
            g2d.setColor(colors[i % 2]);
            g2d.drawString(""+(char)(97 + i), tileSize * i + padding, tileSize * 8 - padding);
        }

        for(int i=0;i<8;i++) {
            g2d.setColor(colors[i % 2]);
            g2d.drawString(""+(8-i), tileSize * 8 - padding - tileSize / 10, tileSize * i + padding + tileSize / 6);
        }
    }

    // Update Method
    private void update(double dt) {

    }

    // Methods about Game
}
