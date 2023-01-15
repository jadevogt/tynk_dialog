package best.tigers.tynkdialog.gui.view.components.cells.factories;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.AbstractPageCellFactory;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.AbstractDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.ChoiceDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.AbstractOverviewCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.ChoiceOverviewCellComponent;

public class ChoicePageCellFactory extends AbstractPageCellFactory {

  @Override
  public AbstractDetailedCellComponent getDetailedComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new ChoiceDetailedCellComponent(pageModel, isSelected);
  }

  @Override
  public AbstractOverviewCellComponent getOverviewComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new ChoiceOverviewCellComponent(pageModel, isSelected);
  }
}
