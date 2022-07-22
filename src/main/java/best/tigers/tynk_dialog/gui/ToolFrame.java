package best.tigers.tynk_dialog.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolFrame extends JFrame {
    public class TestAction extends AbstractAction {
        public TestAction(String name) {
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, "Test action");
        }

        public void actionPerformed(ActionEvent event) {
            System.out.println("clicked test action");
        }
    }

    public ToolFrame() {
        var g = new JButton(new TestAction("test!"));
        add(new DialogListComponent());
        add(g);
        pack();
    }
}
