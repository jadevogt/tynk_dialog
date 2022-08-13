package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.model.DialogPageTableModel;
import best.tigers.tynk_dialog.gui.view.DialogEditorView;

import javax.swing.*;
import java.awt.event.*;

public class DialogController {
  private final DialogEditorView view;
  private final DialogModel model;
  private final Runnable runner;

  public DialogController(DialogModel model) {
    this.model = model;
    runner = new Runnable() {
      public void run() {
        saveTitle();
      }
    };
    view = new DialogEditorView(model)
        .addEditorAction(new EditAction(), "Edit page...")
        .addEditorAction(new AddAction(), "Add page...")
        .addEditorAction(new DeleteAction(), "Delete page")
        .addEditorAction(new SwapUpAction(), "Move up")
        .addEditorAction(new SwapDownAction(), "Move down")
        .init();
    view.attachFocusListener(runner);
    //JList<DialogPageModel> list = view.getList();
    DialogPageTableModel list = view.getDptm();
    JTable dpt = view.getList();
    MouseListener doubleClickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          //int index = list.locationToIndex(e.getPoint());
          int index = dpt.rowAtPoint(e.getPoint());
          System.out.println(index);
          if (index < 0) {
            addPage();
          } else {
            new DialogPageController(model.getElementAt(index));
          }
        }
      }
    };
    dpt.addMouseListener(doubleClickAdapter);
    dpt.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
    dpt.getActionMap().put("Enter", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        editPage();
      }
    });
    dpt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    dpt.getColumnModel().getColumn(0).setPreferredWidth(20);
    dpt.getTableHeader().setResizingAllowed(true);
    dpt.getTableHeader().setReorderingAllowed(false);
  }

  public JPanel getPanel() {
    return view.getPanel();
  }

  public DialogModel getModel() {
    return model;
  }

  public void swapUp() {
    DialogPageModel page = view.getSelectedPage();
    int pageIndex = model.getPageIndex(page);
    if (pageIndex > 0) {
      model.swapListItems(pageIndex, pageIndex - 1);
    }
    view.selectPage(pageIndex - 1);
  }

  public void swapDown() {
    DialogPageModel page = view.getSelectedPage();
    int pageIndex = model.getPageIndex(page);
    if (pageIndex < (model.getPageCount() - 1)) {
      model.swapListItems(pageIndex, pageIndex + 1);
    }
    view.selectPage(pageIndex + 1);
  }

  public void saveTitle() {
    String newTitle = view.getTitle();
    model.setTitle(newTitle);
  }


  public void addPage() {
    DialogPageModel newModel = new DialogPageModel();
    model.addPage(newModel);
    new DialogPageController(newModel, true);
    //view.getList().ensureIndexIsVisible(model.getSize() - 1);
    //view.getList().setVisibleRowCount(model.getSize()-1);
    view.getList().revalidate();
  }

  public void editPage() {
    DialogPageModel selectedModel = view.getSelectedModel();
    if (selectedModel != null) {
      new DialogPageController(view.getSelectedModel());
    } else {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
  }

  public void deletePage() {
    if (view.getSelectedModel() != null) {
      model.deletePage(view.getSelectedModel());
    } else {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
    //view.getList().setVisibleRowCount(model.getSize()-1);
    view.getList().revalidate();
  }

  class SaveAction extends AbstractAction {
    public SaveAction() {
      putValue(Action.NAME, "Save Changes");
      putValue(Action.SHORT_DESCRIPTION, "Save the changes made to this dialog file's title");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      saveTitle();
    }
  }

  class AddAction extends AbstractAction {
    public AddAction() {
      putValue(Action.NAME, "Add");
      putValue(Action.SHORT_DESCRIPTION, "Create a new page and open it in the editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      addPage();
    }
  }

  class EditAction extends AbstractAction {
    public EditAction() {
      putValue(Action.NAME, "Edit");
      putValue(Action.SHORT_DESCRIPTION, "Open this page in the editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      editPage();
    }
  }

  class DeleteAction extends AbstractAction {
    public DeleteAction() {
      putValue(Action.NAME, "Remove");
      putValue(Action.SHORT_DESCRIPTION, "Delete the selected page");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      deletePage();
    }
  }

  class SwapUpAction extends AbstractAction {
    public SwapUpAction() {
      putValue(Action.NAME, "Up");
      putValue(Action.SHORT_DESCRIPTION, "Move the selected page up one spot");
    }

    public void actionPerformed(ActionEvent e) {swapUp();}
  }

  class SwapDownAction extends AbstractAction {
    public SwapDownAction() {
      putValue(Action.NAME, "Down");
      putValue(Action.SHORT_DESCRIPTION, "Move the selected page down one spot");
    }

    public void actionPerformed(ActionEvent e) {swapDown();}
  }

  public String toString() {
    return this.model.getTitle();
  }
}
