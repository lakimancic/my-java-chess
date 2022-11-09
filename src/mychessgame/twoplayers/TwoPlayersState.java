package mychessgame.twoplayers;

import java.awt.*;

import mychessgame.*;
import mychessgame.Figures.*;
import mychessgame.utils.*;

public class TwoPlayersState extends GameState {
    private Board board;
    private Image background;
    private Image[][] figureImages;

    private int mouseX, mouseY;

    public TwoPlayersState(GamePanel panel) {
        super(panel);

        this.board = new Board();
        this.board.setResources(background, figureImages);

        this.mouseAdapter = new TwoPlayersMouse(this);
        this.addEventListeners();
    }

    @Override
    public void render(Graphics2D g) {
        board.render(g, GameFrame.WIDTH / 8, mouseX, mouseY);
    }

    @Override
    public void update(double dt) {
        board.update(dt, GameFrame.WIDTH / 8, mouseX, mouseY);
    }

    @Override
    public void loadResources() {
        background = ResourseHelper.loadImage("assets/board.jpg");
        figureImages = Figure.loadFigureImages();
    }

    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public Board getBoard() {
        return board;
    }
}
