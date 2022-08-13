package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.Integration;
import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.model.DialogPageTableModel;
import best.tigers.tynk_dialog.gui.view.components.AutoResizingTable;
import best.tigers.tynk_dialog.gui.view.components.DialogPageCellRenderer;
import best.tigers.tynk_dialog.gui.view.components.Misc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.security.acl.Group;

public class DialogEditorView implements DialogViewer, Observer {
  private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);
  private static final Dimension MAXIMUM_SIZE = new Dimension(500, Short.MAX_VALUE);

  private final JToolBar editorToolBar;
  //private final JList<DialogPageModel> pageList;
  private final AutoResizingTable pageList;
  private final JTextField titleField;
  private final DialogModel model;
  protected final JPanel panel;

  public DialogEditorView(DialogModel model) {
    Integration.runIntegrations();
    this.model = model;
    this.pageList = new AutoResizingTable();
    pageList.setModel(model.getDptm());
    panel = new JPanel();
    panel.setMinimumSize(new Dimension(0, 0));

    JLabel titleLabel = new JLabel("Title");
    titleField = new JTextField();
    titleField.setColumns(20);

/*    JPanel listPanel = new JPanel();
    listPanel.setMinimumSize(new Dimension(0, 0));
    listPanel.setBorder(Misc.getBorder("Manage Pages"));*/
    editorToolBar = new JToolBar("Editor Commands", SwingConstants.HORIZONTAL);
    editorToolBar.setFloatable(false);
    //pageList = new JList<>(this.model);
    //pageList.setVisibleRowCount(0);
    pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    pageList.setDragEnabled(true);
    pageList.setDropMode(DropMode.ON_OR_INSERT);
    //pageList.setCellRenderer(new DialogPageCellRenderer());
    //pageList.setLayoutOrientation(JList.VERTICAL);
    JPanel titleControls = new JPanel();
    JScrollPane pageScrollPane = new JScrollPane(pageList);
    pageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
/*
    GroupLayout listLayout = new GroupLayout(listPanel);
    listLayout.setAutoCreateGaps(true);
    listLayout.setAutoCreateContainerGaps(true);
    listLayout.setHorizontalGroup(
        listLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(editorToolBar, 0, Short.MAX_VALUE, Short.MAX_VALUE)
            .addComponent(pageScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE));
    listLayout.setVerticalGroup(
        listLayout.createSequentialGroup()
            .addComponent(editorToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(pageScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE));
    listPanel.setLayout(listLayout);

    GroupLayout layout = new GroupLayout(panel);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addGroup(layout.createSequentialGroup()
            .addComponent(titleLabel)
            .addComponent(titleField))
        .addComponent(listPanel, 0, Short.MAX_VALUE, Short.MAX_VALUE)
        );
    layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, -1, -1)
            .addComponent(titleField, GroupLayout.PREFERRED_SIZE, -1, -1))
        .addComponent(listPanel, 0, Short.MAX_VALUE, Short.MAX_VALUE)
        );
    panel.setLayout(layout);

 */
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
/*
  public JList<DialogPageModel> getList() {
    return pageList;
  }
*/
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

  public String getTitle() {
    return titleField.getText();
  }

  public void attachFocusListener(Runnable runner) {
    titleField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        SwingUtilities.invokeLater(runner);
        super.focusLost(e);
      }
    });
  }

  public DialogPageModel getSelectedPage() {
    //return pageList.getSelectedValue();
    return model.getDptm().getPageAt(pageList.getSelectedRow());
  }

  public void setTitle(String newTitle) {
    if (!newTitle.equals(titleField.getText())) {
      titleField.setText(newTitle);
    }
  }

  public void selectPage(int index) {
    //pageList.setSelectedIndex(index);
    try {
      pageList.setRowSelectionInterval(index, index);
    } catch (IllegalArgumentException e) {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
  }

  public DialogPageModel getSelectedModel() {
    //return pageList.getSelectedValue();
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

