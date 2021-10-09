package ui.elements;

import javax.swing.*;
import java.awt.*;

public class LJTextField extends JTextField {
    private int columns = 16;
    public LJTextField() {
        super();
        this.setSize(16, 24);
        this.setBounds( 100, 100, 150, 36 );
        this.setPreferredSize(new Dimension(150, 21));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    public LJTextField(Integer columns) {
        this.setColumns(columns);
    }
}
