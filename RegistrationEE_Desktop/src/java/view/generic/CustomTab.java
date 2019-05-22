package view.generic;

import javax.swing.*;
import java.awt.*;

public abstract class CustomTab extends JPanel{

    protected JPanel filler;

    public CustomTab() {
        filler = new JPanel();
    }

    public void setFillerSize(int width, int height) {
        filler.setPreferredSize(new Dimension(width, height));
    }
}
