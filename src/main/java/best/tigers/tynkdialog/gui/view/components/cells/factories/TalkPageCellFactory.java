package best.tigers.tynkdialog.gui.view.components.cells.factories;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.AbstractPageCellFactory;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.AbstractDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.TalkDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.AbstractOverviewCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.overview.TalkOverviewCellComponent;

public class TalkPageCellFactory extends
    AbstractPageCellFactory {

  @Override
  public AbstractDetailedCellComponent getDetailedComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new TalkDetailedCellComponent(pageModel, isSelected);
  }

  @Override
  public AbstractOverviewCellComponent getOverviewComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    return new TalkOverviewCellComponent(pageModel, isSelected);
  }
}
