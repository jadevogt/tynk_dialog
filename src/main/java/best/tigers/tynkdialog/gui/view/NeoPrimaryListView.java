package best.tigers.tynkdialog.gui.view;

import javax.swing.*;

public class NeoPrimaryListView {
    private JTable pageTable;
    private JTextField titleField;
    private JTree roomTree;
    private JTextField roomField;
    private JToolBar pageTableToolbar;
    private JToolBar roomTreeToolbar;
    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("NeoPrimaryListView");
        frame.setContentPane(new NeoPrimaryListView().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public NeoPrimaryListView() {
    }
}
