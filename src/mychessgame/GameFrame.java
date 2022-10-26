package mychessgame;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public GameFrame() {
        super("My Chess Game");

        add(new GamePanel());

        initUI();
    }

    private void initUI() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setLocation((size.width - WIDTH) / 2, (size.height - HEIGHT) / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
