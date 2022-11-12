package mychessgame.multiplayer;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import mychessgame.*;
import mychessgame.components.Button;
import mychessgame.utils.*;

public class MultiplayerState extends GameState {
    private Image background;

    private int mouseX, mouseY;

    public MultiplayerState(GamePanel panel) {
        super(panel);

        // this.mouseAdapter = new MainMenuMouse(this);
        this.addEventListeners();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void loadResources() {
        background = ResourseHelper.loadImage("assets/main_page.png");
    }

    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
