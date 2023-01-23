package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.components.SuperTextEditorPane;
import javax.swing.JLabel;

public class ChoiceDetailedCellComponent extends AbstractDetailedCellComponent {

  public ChoiceDetailedCellComponent(
      AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    var page = (ChoicePageModel) pageModel;
    var pane = new SuperTextEditorPane(1);
    pane.setText(page.getContent());
    add(pane);
  }
}
