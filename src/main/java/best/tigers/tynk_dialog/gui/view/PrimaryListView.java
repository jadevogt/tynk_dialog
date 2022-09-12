package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.Assets;
import best.tigers.tynk_dialog.gui.controller.DialogController;
import best.tigers.tynk_dialog.gui.model.PrimaryListModel;
import best.tigers.tynk_dialog.gui.view.components.DialogCellRenderer;
import best.tigers.tynk_dialog.gui.view.components.MenuBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PrimaryListView implements TObserver {
  public static final Dimension PREFERRED_SIZE = new Dimension(600, 400);
  private final JFrame frame;
  private final JSplitPane splitPane;
  private final JList<DialogController> dialogList;
  private final MenuBar menuBar;
  private final JToolBar toolBar;
  private final JTextField currentRoom;
  private PrimaryListModel model;

  public PrimaryListView(PrimaryListModel model) {
    Assets.runIntegrations();
    frame = new JFrame();
    frame.setTitle("Tynk Dialog Editor - " + model.getPath());
    this.model = model;
    subscribe(this.model);
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    menuBar = new MenuBar(frame);
    dialogList = new JList<>(this.model);
    JScrollPane listPanel = new JScrollPane(dialogList);
    dialogList.setCellRenderer(new DialogCellRenderer());
    listPanel.setMinimumSize(new Dimension(200, 200));
    toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
    panel.add(listPanel, BorderLayout.CENTER);

    var roomSubPanel = new JPanel();
    roomSubPanel.setLayout(new BorderLayout());
    roomSubPanel.add(new JLabel(" Room:  "), BorderLayout.WEST);
    currentRoom = new JTextField();
    roomSubPanel.add(currentRoom, BorderLayout.CENTER);
    panel.add(roomSubPanel, BorderLayout.NORTH);
    panel.add(toolBar, BorderLayout.SOUTH);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, new JPanel());
    splitPane.setEnabled(false);
    frame.add(splitPane);
    frame.setPreferredSize(PREFERRED_SIZE);
    frame.setMinimumSize(PREFERRED_SIZE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    splitPane.revalidate();

    dialogList.addListSelectionListener(
        new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
            update();
          }
        });
  }

  public void swapModel(PrimaryListModel model) {
    unsubscribe(this.model);
    this.model = model;
    dialogList.setModel(this.model);
    splitPane.setRightComponent(new JPanel());
    subscribe(this.model);
  }

  public DialogController currentSelection() {
    return dialogList.getSelectedValue();
  }

  public void addMenuItem(
      ActionListener action, String shortText, String longText, MenuBar.Menu menu) {
    JMenuItem newItem = new JMenuItem();
    newItem.addActionListener(action);
    newItem.setText(shortText);
    newItem.setToolTipText(longText);
    menuBar.addItem(menu, newItem);
    if (menu == MenuBar.Menu.EDIT) {
      JButton toolButton = new JButton();
      toolButton.addActionListener(action);
      toolButton.setText(shortText.split(" ")[0]);
      toolBar.add(toolButton);
    }
  }

  @Override
  public void update() {
    String modified = model.isModified() ? " - Not Saved" : "";
    frame.setTitle("Tynk Dialog Editor - " + model.getPath() + modified);
    DialogController selected = dialogList.getSelectedValue();
    if (selected != null) {
      splitPane.setRightComponent(selected.getPanel());
    } else {
      splitPane.remove(splitPane.getRightComponent());
      splitPane.setRightComponent(new JPanel());
    }
  }

  public String getCurrentRoom() {
    return currentRoom.getText();
  }

  public JList<DialogController> getDialogList() {
    return dialogList;
  }

  public void attachWindowEvent(WindowAdapter adapter) {
    frame.addWindowListener(adapter);
  }

  public int prompt() {
    if (model.isModified()) {
      return JOptionPane.showConfirmDialog(
          null,
          "You haven't saved this file since the last changes were made. Would you like to save before continuing?",
          "Hold on--",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.WARNING_MESSAGE);
    } else {
      return 1;
    }
  }
}
