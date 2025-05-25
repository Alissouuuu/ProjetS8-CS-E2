package view;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Dégradé vertical bleu clair → blanc
        Color topColor = new Color(173, 216, 230); // Bleu clair
        Color bottomColor = Color.WHITE;

        GradientPaint gp = new GradientPaint(0, 0, topColor, 0, height, bottomColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
