package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;

public class DialogPageListView {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JButton saveButton;
    private JToolBar editorToolBar;
    private JList pageList;
    private JTextField titleField;

    private static Dimension PREFERRED_SIZE = new Dimension(300, 300);
    private static Dimension MAXIMUM_SIZE = new Dimension(500, Short.MAX_VALUE);

    private static TitledBorder getBorder(String title) {
        var tempBorder = new EtchedBorder();
        var border = new TitledBorder(tempBorder, title);
        border.setTitlePosition(TitledBorder.CENTER);
        return border;
    }

    public DialogPageListView(DialogModel model) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
        var panel = new JPanel();
        frame.setPreferredSize(PREFERRED_SIZE);
        frame.setMinimumSize(PREFERRED_SIZE);
        frame.setMaximumSize(MAXIMUM_SIZE);
        saveButton = new JButton("Save Changes");
        editorToolBar = new JToolBar("Editor Commands", SwingConstants.HORIZONTAL);
        editorToolBar.setFloatable(false);
        var titleLabel = new JLabel("Title");
        titleField = new JTextField();
        titleField.setColumns(20);

        pageList = new JList(model);
        pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pageList.setDragEnabled(true);
        pageList.setDropMode(DropMode.ON_OR_INSERT);
        pageList.setCellRenderer(new DialogPageCellRenderer());
        pageList.setLayoutOrientation(JList.VERTICAL);
        pageList.setVisibleRowCount(2);

        var listPanel = new JPanel();
        listPanel.setBorder(getBorder("Manage Pages"));
        var listLayout = new GroupLayout(listPanel);
        listLayout.setAutoCreateGaps(true);
        listLayout.setAutoCreateContainerGaps(true);
        listLayout.setHorizontalGroup(
                listLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(editorToolBar, 0, Short.MAX_VALUE, Short.MAX_VALUE)
                        .addComponent(pageList, 0, Short.MAX_VALUE, Short.MAX_VALUE));
        listLayout.setVerticalGroup(
                listLayout.createSequentialGroup()
                        .addComponent(editorToolBar)
                        .addComponent(pageList, 0, Short.MAX_VALUE, Short.MAX_VALUE));
        listPanel.setLayout(listLayout);

        var layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(titleLabel)
                    .addComponent(titleField))
                .addComponent(listPanel, 0, Short.MAX_VALUE, Short.MAX_VALUE)
                .addComponent(saveButton));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, -1, -1)
                        .addComponent(titleField, GroupLayout.PREFERRED_SIZE, -1, -1))
                .addComponent(listPanel, 0, Short.MAX_VALUE, Short.MAX_VALUE)
                .addComponent(saveButton));

        panel.setLayout(layout);
        frame.add(panel);
    }

    public void init() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public DialogPageModel getSelectedModel() {
        return (DialogPageModel) pageList.getSelectedValue();
    }

    public void addEditorAction(Action action, String longName) {
        editorToolBar.add(action);
        var menuItem = new JMenuItem();
        menuItem.setText(longName);
        menuItem.addActionListener(action);
        editMenu.add(menuItem);
    }
}

class DialogPageCellRenderer extends JLabel implements ListCellRenderer<DialogPageModel> {

    @Override
    public Component getListCellRendererComponent(JList<? extends DialogPageModel> list, DialogPageModel value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getSpeaker() + " SAYS " + value.getContent());

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        setOpaque(true);
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.decode("#39698a");
            foreground = Color.WHITE;

            // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        };
        setBackground(background);
        setForeground(foreground);

        return this;
    }
}