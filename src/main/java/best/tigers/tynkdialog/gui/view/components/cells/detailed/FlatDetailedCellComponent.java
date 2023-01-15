package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FlatDetailedCellComponent extends AbstractDetailedCellComponent {

  public FlatDetailedCellComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    super(pageModel, isSelected);
    var field = new JLabel();
    field.setText("Flat: " + ((FlatPageModel) pageModel).getFlat());
    field.setPreferredSize(new Dimension(999, 50));
    field.setFont(getFont().deriveFont(Font.BOLD, 24));
    field.setHorizontalAlignment(SwingConstants.CENTER);
    if (isSelected) {
      field.setFont(getFont().deriveFont(Font.ITALIC | Font.BOLD, 24));
      setBackground(Color.decode("#297697"));
    }
    add(field);
  }
}
