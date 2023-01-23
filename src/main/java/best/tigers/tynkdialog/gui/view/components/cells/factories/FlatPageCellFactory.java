package best.tigers.tynkdialog.gui.view.components.cells.factories;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.AbstractDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.FlatDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.AbstractOverviewCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.FlatOverviewCellComponent;

public class FlatPageCellFactory extends AbstractPageCellFactory {

  @Override
  public AbstractDetailedCellComponent getDetailedComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new FlatDetailedCellComponent(pageModel, isSelected);
  }

  @Override
  public AbstractOverviewCellComponent getOverviewComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new FlatOverviewCellComponent(pageModel, isSelected);
  }
}
