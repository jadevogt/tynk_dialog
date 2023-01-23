package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.factories.AbstractPageCellFactory;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DetailedCellRenderer implements TableCellRenderer {

  public static Font font = Assets.getInstance().getTerminus();

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    Component comp = null;
    if (value instanceof AbstractPageModel pageModel) {
      var factory = AbstractPageCellFactory.getPageCellFactory(pageModel);
      comp = factory.getDetailedComponent(pageModel, isSelected);
      if (table.getRowHeight(row) < comp.getPreferredSize().height) {
        table.setRowHeight(row, comp.getPreferredSize().height);
      }
    }
    return comp;
  }
}
