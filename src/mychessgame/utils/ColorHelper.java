package mychessgame.utils;

import java.awt.*;
import java.awt.image.*;

public class ColorHelper {
    public static BufferedImage getColorizedImage(Image image, Color color, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                int pixel = newImage.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                Color tempColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (color.getAlpha() * alpha / 255));
        
                if (alpha != 0) {
                    newImage.setRGB(x, y, tempColor.getRGB());
                }
            }
        }

        return newImage;
    }

    public static Color getColorTransition(Color from, Color to, double index) {
        return new Color(
            (int)(from.getRed() + (to.getRed() - from.getRed()) * index),
            (int)(from.getGreen() + (to.getGreen() - from.getGreen()) * index),
            (int)(from.getBlue() + (to.getBlue() - from.getBlue()) * index),
            (int)(from.getAlpha() + (to.getAlpha() - from.getAlpha()) * index)
        );
    }
}
