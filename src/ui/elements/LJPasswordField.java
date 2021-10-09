package ui.elements;

import javax.swing.*;
import java.awt.*;

public class LJPasswordField extends JPasswordField {
    private int columns = 16;
    public LJPasswordField() {
        super();
        this.setSize(16, 24);
        this.setBounds( 100, 100, 150, 36 );
        this.setPreferredSize(new Dimension(150, 21));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    public LJPasswordField(Integer columns) {
        this.setColumns(columns);
    }

}
