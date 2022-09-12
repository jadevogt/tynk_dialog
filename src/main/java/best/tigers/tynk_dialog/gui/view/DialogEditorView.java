package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.model.DialogPageTableModel;
import best.tigers.tynk_dialog.gui.view.components.AutoResizingTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Action;
import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DialogEditorView implements DialogViewer, TObserver {
  protected final JPanel panel;
  private final JToolBar editorToolBar;
  private final AutoResizingTable pageList;
  private final JTextField titleField;
  private final DialogModel model;

  public DialogEditorView(DialogModel model) {
    this.model = model;
    pageList = new AutoResizingTable();
    pageList.setModel(model.getDptm());
    panel = new JPanel();
    panel.setMinimumSize(new Dimension(0, 0));

    JLabel titleLabel = new JLabel("Title");
    titleField = new JTextField();
    titleField.setColumns(20);

    editorToolBar = new JToolBar("Editor Commands", SwingConstants.HORIZONTAL);
    editorToolBar.setFloatable(false);
    pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    pageList.setDragEnabled(true);
    pageList.setDropMode(DropMode.ON_OR_INSERT);
    JPanel titleControls = new JPanel();
    JScrollPane pageScrollPane = new JScrollPane(pageList);
    pageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    panel.setLayout(new BorderLayout());
    titleControls.add(titleLabel);
    titleControls.add(titleField);
    panel.add(titleControls, BorderLayout.NORTH);
    panel.add(editorToolBar, BorderLayout.SOUTH);
    panel.add(pageScrollPane, BorderLayout.CENTER);
  }

  public JPanel getPanel() {
    return panel;
  }

  public DialogEditorView init() {
    model.attachSubscriber(this);
    update();
    return this;
  }

  public JTable getList() {
    return pageList;
  }

  @Override
  public void update() {
    if (!model.getTitle().equals(titleField.getText())) {
      titleField.setText(model.getTitle());
    }
    pageList.setModel(model.getDptm());
    pageList.resizeColumnWidth();
    pageList.validate();
  }

  @Override
  public String getTitle() {
    return titleField.getText();
  }

  @Override
  public void setTitle(String newTitle) {
    if (!newTitle.equals(titleField.getText())) {
      titleField.setText(newTitle);
    }
  }

  public void attachFocusListener(Runnable runner) {
    titleField.addFocusListener(
        new FocusAdapter() {
          @Override
          public void focusLost(FocusEvent e) {
            SwingUtilities.invokeLater(runner);
            super.focusLost(e);
          }
        });
    titleField.addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseMoved(MouseEvent e) {
            SwingUtilities.invokeLater(runner);
            super.mouseMoved(e);
          }
        });
  }

  public DialogPageModel getSelectedPage() {
    return model.getDptm().getPageAt(pageList.getSelectedRow());
  }

  public void selectPage(int index) {
    try {
      pageList.setRowSelectionInterval(index, index);
    } catch (IllegalArgumentException e) {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
  }

  public DialogPageModel getSelectedModel() {
    return getSelectedPage();
  }

  public DialogPageTableModel getDptm() {
    return model.getDptm();
  }

  public DialogEditorView addEditorAction(Action action, String longName) {
    editorToolBar.add(action);
    return this;
  }
}
