/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author User
 */
public class ButtonImageIcon {
    
    public static void fitImageToButton(JButton button, BufferedImage image, int padding) {
        // Use the button's current size, or fall back to its preferred size
        int btnWidth  = button.getWidth()  > 0 ? button.getWidth()  : button.getPreferredSize().width;
        int btnHeight = button.getHeight() > 0 ? button.getHeight() : button.getPreferredSize().height;

        int iconWidth  = btnWidth  - padding * 2;
        int iconHeight = btnHeight - padding * 2;

        if (iconWidth <= 0 || iconHeight <= 0) {
            throw new IllegalArgumentException("Button is too small or padding is too large.");
        }

        // Maintain aspect ratio
        double imgRatio = (double) image.getWidth() / image.getHeight();
        double btnRatio = (double) iconWidth / iconHeight;

        int finalWidth, finalHeight;
        if (imgRatio > btnRatio) {
            finalWidth  = iconWidth;
            finalHeight = (int) (iconWidth / imgRatio);
        } else {
            finalHeight = iconHeight;
            finalWidth  = (int) (iconHeight * imgRatio);
        }

        // Scale the image with high quality
        BufferedImage scaled = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, finalWidth, finalHeight, null);
        g2d.dispose();

        button.setIcon(new ImageIcon(scaled));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Convenience overload: loads an image from a file path.
     */
    public static void fitImageToButton(JButton button, String imagePath, int padding) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        fitImageToButton(button, image, padding);
    }
    
    public static void stretchImageToButton(JButton button, BufferedImage image, int padding) {
        int btnWidth  = button.getWidth()  > 0 ? button.getWidth()  : button.getPreferredSize().width;
        int btnHeight = button.getHeight() > 0 ? button.getHeight() : button.getPreferredSize().height;

        int iconWidth  = btnWidth  - padding * 2;
        int iconHeight = btnHeight - padding * 2;

        if (iconWidth <= 0 || iconHeight <= 0) {
            throw new IllegalArgumentException("Button is too small or padding is too large.");
        }

        // Stretch to fill the full icon area — no aspect ratio preserved
        BufferedImage stretched = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = stretched.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, iconWidth, iconHeight, null);
        g2d.dispose();

        button.setIcon(new ImageIcon(stretched));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Convenience overload: loads an image from a file path.
     */
    public static void stretchImageToButton(JButton button, String imagePath, int padding) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        stretchImageToButton(button, image, padding);
    }
    
}
