package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import java.awt.Label;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class BranchDetailedCellComponent extends AbstractDetailedCellComponent {

  public BranchDetailedCellComponent(
      AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    var model = (BranchPageModel) pageModel;
    var layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    setBorder(new EmptyBorder(10, 10, 10, 10));
    setLayout(layout);
    model.getRequirementListModel().getContent().forEach(r -> add(new JLabel(r.toString())));
  }
}
