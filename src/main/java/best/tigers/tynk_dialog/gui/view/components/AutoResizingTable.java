package best.tigers.tynk_dialog.gui.view.components;

import java.awt.Component;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AutoResizingTable extends JTable {
  public AutoResizingTable() {
    super();
    addHierarchyBoundsListener(
        new HierarchyBoundsAdapter() {
          @Override
          public void ancestorResized(HierarchyEvent e) {
            super.ancestorResized(e);
            resizeColumnWidth();
          }
        });
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
}
