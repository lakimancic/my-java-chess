package mychessgame.Figures;

import java.util.List;

public class Position {
    int x, y;

    public Position(int x, int y) {
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTileHovered(int tileSize, int mouseX, int mouseY) {
        return mouseX >= tileSize * x && mouseX < tileSize * (x + 1) && mouseY >= tileSize * y && mouseY < tileSize * (y + 1);
    }

    public boolean isInList(List<Position> list) {
        for(Position p : list) {
            if(p.x == x && p.y == y) return true;
        }
        return false;
    }
}
