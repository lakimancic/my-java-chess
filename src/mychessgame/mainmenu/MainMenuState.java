package mychessgame.mainmenu;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import mychessgame.*;
import mychessgame.components.Button;
import mychessgame.utils.*;

public class MainMenuState extends GameState {
    private Image background;
    private Image twoPlayerIcon;

    private int mouseX, mouseY;

    private List<Button> buttons;

    public MainMenuState(GamePanel panel) {
        super(panel);

        initButtons();

        this.mouseAdapter = new MainMenuMouse(this);
        this.addEventListeners();
    }

    public void initButtons() {
        buttons = new ArrayList<Button>();

        buttons.add(
                new Button(GameFrame.WIDTH / 2, 320, 280, 50, "Singleplayer", twoPlayerIcon));
        buttons.add(
                new Button(GameFrame.WIDTH / 2, 400, 280, 50, "Two Players", twoPlayerIcon));
        buttons.add(
                new Button(GameFrame.WIDTH / 2, 480, 280, 50, "Multiplayer", twoPlayerIcon));
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);

        renderVersion(g);

        for (Button b : buttons)
            b.render(g);
    }

    public void renderVersion(Graphics2D g) {
        int fontSize = 16;
        int padding = 8;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

        g.drawString(String.format("%s %d.%d.%d", GameFrame.VERSION_NAME, GameFrame.VERSION_MAJOR,
                GameFrame.VERSION_MINOR, GameFrame.VERSION_PATCH), padding, GameFrame.HEIGHT - padding);
    }

    @Override
    public void update(double dt) {
        for (Button b : buttons)
            b.update(dt, mouseX, mouseY);
    }

    @Override
    public void loadResources() {
        background = ResourseHelper.loadImage("assets/main_page.png");

        twoPlayerIcon = ResourseHelper.loadImage("assets/twoplayers.png");
    }

    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
