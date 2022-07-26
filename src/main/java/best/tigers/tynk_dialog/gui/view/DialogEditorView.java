package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
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

public class DialogEditorView implements DialogViewer, Observer {
  private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);
  private static final Dimension MAXIMUM_SIZE = new Dimension(500, Short.MAX_VALUE);

  private final JButton saveButton;
  private final JToolBar editorToolBar;
  private final JList<DialogPageModel> pageList;
  private final JTextField titleField;
  private final DialogModel model;
  protected final JPanel panel;

  public DialogEditorView(DialogModel model) {
    this.model = model;
    panel = new JPanel();
    panel.setMinimumSize(new Dimension(0, 0));

    JLabel titleLabel = new JLabel("Title");
    titleField = new JTextField();
    titleField.setColumns(20);

    JPanel listPanel = new JPanel();
    listPanel.setMinimumSize(new Dimension(0, 0));
    listPanel.setBorder(Misc.getBorder("Manage Pages"));
    editorToolBar = new JToolBar("Editor Commands", SwingConstants.HORIZONTAL);
    editorToolBar.setFloatable(false);
    pageList = new JList<>(this.model);
    pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    pageList.setDragEnabled(true);
    pageList.setDropMode(DropMode.ON_OR_INSERT);
    pageList.setCellRenderer(new DialogPageCellRenderer());
    pageList.setLayoutOrientation(JList.VERTICAL);

    GroupLayout listLayout = new GroupLayout(listPanel);
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

    saveButton = new JButton("Save Changes");

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
  }

  public JPanel getPanel() {
    return panel;
  }

  public DialogEditorView init() {
    model.attachSubscriber(this);
    update();
    return this;
  }

  @Override
  public void update() {
    if (!model.getTitle().equals(titleField.getText())) {
      titleField.setText(model.getTitle());
    }
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
    return pageList.getSelectedValue();
  }

  public void setTitle(String newTitle) {
    if (!newTitle.equals(titleField.getText())) {
      titleField.setText(newTitle);
    }
  }

  public void selectPage(int index) {
    pageList.setSelectedIndex(index);
  }

  public DialogPageModel getSelectedModel() {
    return pageList.getSelectedValue();
  }

  public DialogEditorView addEditorAction(Action action, String longName) {
    editorToolBar.add(action);
    return this;
  }

  public DialogEditorView attachSaveAction(Action action, String longName) {
    saveButton.addActionListener(action);
    return this;
  }

}

