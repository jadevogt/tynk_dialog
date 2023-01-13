package best.tigers.tynkdialog.gui.view;

import best.tigers.tynkdialog.game.page.Page;
import best.tigers.tynkdialog.gui.model.DialogModel;
import best.tigers.tynkdialog.gui.model.PageTableModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.components.AutoResizingTable;
import best.tigers.tynkdialog.gui.view.components.cells.PageCellRenderer;
import best.tigers.tynkdialog.gui.view.components.cells.PageDetailsCellRenderer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DialogEditorView implements ShortcutSupport, DialogViewer, TObserver {

  private final JPanel panel;
  private final JToolBar editorToolBar;
  private final AutoResizingTable pageList;
  private final JTextField titleField;
  private final DialogModel model;

  private DialogEditorView(DialogModel model) {
    this.model = model;
    pageList = AutoResizingTable.fromDialogPageModel(model);
    panel = new JPanel();
    titleField = new JTextField();
    editorToolBar = new JToolBar();
  }

  public static DialogEditorView fromModel(DialogModel model) {
    var view = new DialogEditorView(model);
    view.buildView();
    view.subscribeToModel();
    return view;
  }

  private void buildView() {
    setupToolBar();
    var titleControls = setupTitlePanel();
    JScrollPane pageScrollPane = setupScrollPane();

    panel.setLayout(new BorderLayout());
    panel.add(titleControls, BorderLayout.NORTH);
    panel.add(editorToolBar, BorderLayout.SOUTH);
    panel.add(pageScrollPane, BorderLayout.CENTER);
  }

  private JScrollPane setupScrollPane() {
    var scrollPane = new JScrollPane(pageList);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    return scrollPane;
  }

  private void setupToolBar() {
    editorToolBar.setName("Editor Commands");
    editorToolBar.setOrientation(SwingConstants.HORIZONTAL);
    editorToolBar.setFloatable(false);
  }

  private JPanel setupTitlePanel() {
    var titleControls = new JPanel();
    var titleLabel = new JLabel("Title");
    titleField.setColumns(20);
    titleControls.add(titleLabel);
    titleControls.add(titleField);
    return titleControls;
  }

  public JPanel getPanel() {
    return panel;
  }

  public void subscribeToModel() {
    model.attachSubscriber(this);
    update();
  }

  public AutoResizingTable getTable() {
    return pageList;
  }

  @Override
  public void update() {
    if (!model.getTitle().equals(titleField.getText())) {
      titleField.setText(model.getTitle());
    }
    pageList.setModel(model.getDptm());
    pageList.setDefaultRenderer(AbstractPageModel.class, new PageCellRenderer());
    pageList.setDefaultRenderer(TalkPageModel.class, new PageCellRenderer());
    var columnModel = pageList.getColumnModel();
    var column = columnModel.getColumn(0);
    column.setCellRenderer(new PageDetailsCellRenderer());
    pageList.setupView();
    //pageList.resizeColumnWidth();
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
  }

  public AbstractPageModel getSelectedPage() {
    return model.getDptm().getPageAt(pageList.getSelectedRow());
  }

  public void selectPage(int index) {
    try {
      pageList.setRowSelectionInterval(index, index);
    } catch (IllegalArgumentException e) {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
  }

  public AbstractPageModel getSelectedModel() {
    return getSelectedPage();
  }

  public PageTableModel getDptm() {
    return model.getDptm();
  }

  public void addEditorActions(Action... actions) {
    Arrays.stream(actions).forEach(editorToolBar::add);
  }

  @Override
  public void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      Runnable action) {
    var inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = panel.getActionMap();
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    actionMap.put(actionMapKey, actionInstance);
  }

  @Override
  public void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      AbstractAction action) {
    var inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = panel.getActionMap();
    actionMap.put(actionMapKey, action);
  }
}
