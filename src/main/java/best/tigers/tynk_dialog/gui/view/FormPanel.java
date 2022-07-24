package best.tigers.tynk_dialog.gui.view;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    private BoxLayout layout;
    public FormPanel() {
        super();
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }

    public void autoAdd(Component comp) {
        if (comp instanceof JLabel) {
            add(comp);
        }
        else {
            add(comp);
        }
    }
}
