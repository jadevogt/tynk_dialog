package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import javax.swing.JLabel;

public class ChoiceDetailedCellComponent extends AbstractDetailedCellComponent {

  public ChoiceDetailedCellComponent(
      AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    add(new JLabel("UNDER CONSTRUCTION"));
  }
}
