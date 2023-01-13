package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.game.page.Page;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PageCellRenderer implements TableCellRenderer {

  public static Font font = Assets.getInstance().getFont();
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component comp = null;
    if (value instanceof AbstractPageModel pageModel) {
      switch(pageModel.getPage().getPageKind()) {
        case "flat" -> {
          var field = new JLabel();
          var panel = new JPanel();
          field.setText("Flat: " + ((FlatPageModel) pageModel).getFlat());
          field.setPreferredSize(new Dimension(999, 50));
          field.setFont(font.deriveFont(Font.BOLD, 24));
          field.setHorizontalAlignment(SwingConstants.CENTER);
          if (isSelected) {
            field.setFont(font.deriveFont(Font.ITALIC | Font.BOLD, 24));
            panel.setBackground(Color.decode("#297697"));
          }
          panel.add(field);
          comp = panel;
        }
        default -> {
          var scrollPane = new JScrollPane();
          var field = new TalkPageCell((TalkPageModel) value, isSelected);
          scrollPane.setViewportView(field);
          scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
          scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
          comp = scrollPane;
        }
      }
    }
    table.setRowHeight(row, comp.getPreferredSize().height);
    return comp;
  }
}
