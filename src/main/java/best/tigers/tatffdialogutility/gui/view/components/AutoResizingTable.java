package best.tigers.tatffdialogutility.gui.view.components;

import best.tigers.tatffdialogutility.gui.model.DialogModel;
import best.tigers.tatffdialogutility.gui.view.ShortcutSupport;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class AutoResizingTable extends JTable implements ShortcutSupport {
  public AutoResizingTable(TableModel model) {
    super();
    setModel(model);
    addHierarchyBoundsListener(
            new HierarchyBoundsAdapter() {
              @Override
              public void ancestorResized(HierarchyEvent e) {
                super.ancestorResized(e);
                if (getParent() != null) {
                  resizeColumnWidth();
                }
              }
            });
  }

  public static AutoResizingTable fromDialogPageModel(DialogModel model) {
    var table = new AutoResizingTable(model.getDptm());
    table.setupView();
    return table;
  }

  private void setupView() {
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    setDragEnabled(true);
    setDropMode(DropMode.ON_OR_INSERT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    var columnModel = getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(20);
    var tableHeader = getTableHeader();
    tableHeader.setResizingAllowed(true);
    tableHeader.setReorderingAllowed(false);
  }

  public void resizeColumnWidth() {
    int cumulativeActual = 0;
    int padding = 15;
    for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
      int width = 50; // Min width
      TableColumn column = columnModel.getColumn(columnIndex);
      for (int row = 0; row < getRowCount(); row++) {
        TableCellRenderer renderer = getCellRenderer(row, columnIndex);
        Component comp = prepareRenderer(renderer, row, columnIndex);
        width = Math.max(comp.getPreferredSize().width + padding, width);
      }
      if (columnIndex < getColumnCount() - 1) {
        column.setPreferredWidth(width);
        cumulativeActual += column.getWidth();
      } else { // LAST COLUMN
        // Use the parent's (viewPort) width and subtract the previous columbs actual widths.
        column.setPreferredWidth((int) getParent().getSize().getWidth() - cumulativeActual);
      }
    }
  }

  @Override
  public void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, Runnable action) {
    var inputMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = getActionMap();
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    actionMap.put(actionMapKey, actionInstance);
  }


  @Override
  public void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, AbstractAction action) {
    var inputMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = getActionMap();
    actionMap.put(actionMapKey, action);
  }
}
