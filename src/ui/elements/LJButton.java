package ui.elements;

import javax.swing.*;
import java.awt.*;

public class LJButton extends JButton {
    private int width = 150;
    private int height = 28;
    public LJButton(String text) {
        super(text);
        this.setBackground(Color.BLUE);
        this.setForeground(Color.WHITE);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    public void setHeight(int height) {
        this.setPreferredSize(new Dimension(width, height));
    }
    public void setWidth(int width) {
        this.setPreferredSize(new Dimension(width, height));
    }
}
