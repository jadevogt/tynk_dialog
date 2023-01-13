package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PageDetailsCellRenderer implements TableCellRenderer {
  public static Font font = Assets.getInstance().getFont();

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    var detailsPanel = new JPanel();
    detailsPanel.setBackground(null);
    var layout = new BoxLayout(detailsPanel, BoxLayout.Y_AXIS);
    detailsPanel.setLayout(layout);
    if (value instanceof AbstractPageModel pageModel) {
      detailsPanel.add(new QuickPair("Kind", pageModel.getPage().getPageKind()), Component.TOP_ALIGNMENT);
      switch (pageModel.getPage().getPageKind()) {
        case "flat" -> {
          FlatPageModel flatPageModel = (FlatPageModel) value;
          detailsPanel.add(new QuickPair("Flat", flatPageModel.getFlat()), Component.TOP_ALIGNMENT);
        }
        default -> {
          TalkPageModel talkPageModel = (TalkPageModel) value;
          detailsPanel.add(new QuickPair("Char", talkPageModel.getSpeaker()), Component.TOP_ALIGNMENT);
          detailsPanel.add(new QuickPair("Blip", talkPageModel.getBlip()), Component.TOP_ALIGNMENT);
          detailsPanel.add(new QuickPair("Style", talkPageModel.getTextBoxStyle()), Component.TOP_ALIGNMENT);
        }
      }
    }
    if (isSelected) {
      detailsPanel.setBackground(Color.decode("#297697"));
    }
    return detailsPanel;
  }

  class QuickPair extends JPanel {
    public QuickPair(String label, String value) {
      super();
      var layout = new BoxLayout(this, BoxLayout.X_AXIS);
      setLayout(layout);
      var boldLabel = new JLabel(label + ": ");
      boldLabel.setFont(font.deriveFont(Font.BOLD, 16));
      add(boldLabel, LEFT_ALIGNMENT);
      var strValue = value == null ? "None" : value;
      var valueLabel = new JLabel(strValue);
      valueLabel.setFont(font.deriveFont(Font.PLAIN, 16));
      add(valueLabel, LEFT_ALIGNMENT);
      setMaximumSize(new Dimension(999, 18));
      setBackground(null);
      boldLabel.setBackground(null);
      valueLabel.setBackground(null);
    }
  }
}
