package best.tigers.tynkdialog.gui.view.components.cells.factories;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.AbstractDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.BranchDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.ChoiceDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.AbstractOverviewCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.BranchOverviewCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.ChoiceOverviewCellComponent;

public class BranchPageCellFactory extends AbstractPageCellFactory {

  @Override
  public AbstractDetailedCellComponent getDetailedComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new BranchDetailedCellComponent(pageModel, isSelected);
  }

  @Override
  public AbstractOverviewCellComponent getOverviewComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new BranchOverviewCellComponent(pageModel, isSelected);
  }
}
