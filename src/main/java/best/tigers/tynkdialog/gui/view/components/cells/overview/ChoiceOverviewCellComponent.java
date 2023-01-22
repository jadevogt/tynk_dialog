package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import lombok.Getter;

public class ChoiceOverviewCellComponent extends AbstractOverviewCellComponent {

  @Getter
  private ChoicePageModel pageModel;

  public ChoiceOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    pageModel = (ChoicePageModel) pageModel;
  }

}
