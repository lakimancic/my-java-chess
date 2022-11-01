package mychessgame.mainmenu;

import java.awt.*;

import mychessgame.utils.ColorHelper;

public class Button {
    private int x, y;
    private int width, height;
    private String text;
    private Image icon;

    private final static Color COLOR = new Color(79, 128, 169);
    private final static Color BACK_COLOR = new Color(79, 128, 169, 0);
    private final static Color WHITE = new Color(255, 255, 255);
    private Color foreColor = COLOR;
    private Color backColor = BACK_COLOR;

    private double colorIndex = 0;

    public Button(int x, int y, int width, int height, String text, Image icon) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.icon = icon;
    }

    public void render(Graphics2D g) {
        int fontSize = 24;
        int iconSize = (int)(height * 0.6);
        int iconPadding = (int)(height * 0.2);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);

        g.setColor(backColor);
        g.fillRoundRect(x - width / 2, y - height / 2, width, height, height, height);
        g.setColor(COLOR);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x - width / 2, y - height / 2, width, height, height, height);

        g.setColor(foreColor);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        g.drawString(
            text, x - (metrics.stringWidth(text) - iconPadding - iconSize) / 2, y + metrics.getHeight() / 4
        );

        renderIcon(g, x, metrics);
    }

    private void renderIcon(Graphics2D g, int x, FontMetrics metrics) {
        int iconSize = (int)(height * 0.6);
        int iconPadding = (int)(height * 0.2);

        g.drawImage(ColorHelper.getColorizedImage(icon, foreColor, iconSize, iconSize), x - (iconPadding + iconSize + metrics.stringWidth(text)) / 2, y - iconSize / 2, iconSize, iconSize, null);
    }

    public void update(double dt, int mouseX, int mouseY) {
        if(isHovered(mouseX, mouseY)) {
            colorIndex = Math.min(colorIndex + dt * 5, 1.0);
        } else {
            colorIndex = Math.max(colorIndex - dt * 5, 0.0);
        }

        backColor = ColorHelper.getColorTransition(BACK_COLOR, COLOR, colorIndex);
        foreColor = ColorHelper.getColorTransition(COLOR, WHITE, colorIndex);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        if(
            mouseX >= x - width / 2 && mouseX <= x + width / 2 &&
            mouseY >= y - height / 2 && mouseY <= y + height / 2
        ) {
            if(mouseX >= x - width / 2 + height / 2 && mouseX <= x + width / 2 - height / 2) return true;

            if(mouseX < x) {
                double r = Math.sqrt(Math.pow(x - width / 2 + height / 2 - mouseX, 2) + Math.pow(mouseY - y, 2));
                return r <= height / 2;
            }
            if(mouseX > x) {
                double r = Math.sqrt(Math.pow(x + width / 2 - height / 2 - mouseX, 2) + Math.pow(mouseY - y, 2));
                return r <= height / 2;
            }
        }

        return false;
    }
}
