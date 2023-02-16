package best.tigers.tynkdialog.gui.view;

import best.tigers.tynkdialog.gui.controller.DialogController;
import best.tigers.tynkdialog.gui.model.PrimaryListModel;
import best.tigers.tynkdialog.gui.view.components.DialogCellRenderer;
import best.tigers.tynkdialog.gui.view.components.MenuBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;

public class PrimaryListView implements TObserver {

  private static final Dimension PREFERRED_SIZE = new Dimension(1000, 600);
  private final JFrame frame;
  private final JSplitPane splitPane;
  private JComponent selectedView = null;
  private final JList<DialogController> dialogList;
  private final MenuBar menuBar;
  private final JToolBar toolBar;
  private final JTextField currentRoom;
  private final JPanel panel;
  private PrimaryListModel model;

  private PrimaryListView(PrimaryListModel model) {
    this.model = model;
    frame = new JFrame();
    menuBar = new MenuBar(frame);
    dialogList = new JList<>(this.model);
    toolBar = new JToolBar();
    currentRoom = new JTextField();
    panel = new JPanel();
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, new JPanel());
    subscribe(this.model);
  }

  public static PrimaryListView fromModel(PrimaryListModel model) {
    var view = new PrimaryListView(model);
    view.setupView();
    return view;
  }

  public Frame getFrame() {
    return frame;
  }

  private void setupView() {
    panel.setLayout(new BorderLayout());
    dialogList.setCellRenderer(new DialogCellRenderer());

    var listPanel = new JScrollPane(dialogList);
    listPanel.setMinimumSize(new Dimension(200, 200));
    panel.add(listPanel, BorderLayout.CENTER);
    setupSelectionListener();

    buildToolbar();
    panel.add(toolBar, BorderLayout.SOUTH);

    var roomSubPanel = buildRoomSubPanel();
    panel.add(roomSubPanel, BorderLayout.NORTH);

    splitPane.setEnabled(false);
    splitPane.revalidate();
    setupFrame();
  }

  private void setupFrame() {
    frame.setTitle("Tynk Dialog Editor - " + model.getPath());
    frame.add(splitPane);
    frame.setPreferredSize(PREFERRED_SIZE);
    frame.setMinimumSize(PREFERRED_SIZE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
  }

  private void buildToolbar() {
    toolBar.setFloatable(false);
    toolBar.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
  }

  private JPanel buildRoomSubPanel() {
    var roomSubPanel = new JPanel();
    roomSubPanel.setLayout(new BorderLayout());
    roomSubPanel.add(new JLabel(" Room:  "), BorderLayout.WEST);
    roomSubPanel.add(currentRoom, BorderLayout.CENTER);
    return roomSubPanel;
  }

  private void setupSelectionListener() {
    dialogList.addListSelectionListener(
        e -> update());
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
    var newItem = new JMenuItem();
    newItem.addActionListener(action);
    newItem.setText(shortText);
    newItem.setToolTipText(longText);
    menuBar.addItem(menu, newItem);
    if (menu == MenuBar.Menu.EDIT) {
      var toolButton = new JButton();
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
      if (selectedView != selected.getPanel()) {
        splitPane.setRightComponent(selected.getPanel());
        selectedView = selected.getPanel();
      }
    } else {
      splitPane.remove(splitPane.getRightComponent());
      splitPane.setRightComponent(new JPanel());
    }
    dialogList.updateUI();
    if (selectedView != null) {
      selectedView.updateUI();
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
