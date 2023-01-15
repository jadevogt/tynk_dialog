package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class OverviewCellRenderer implements TableCellRenderer {

  public static Font font = Assets.getInstance().getFont();

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    JComponent component = null;
    if (value instanceof AbstractPageModel pageModel) {
      var factory = AbstractPageCellFactory.getPageCellFactory(pageModel);
      component = factory.getOverviewComponent(pageModel, isSelected);
    }
    return component;
  }
}
