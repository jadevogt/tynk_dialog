package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.cells.detailed.AbstractDetailedCellComponent;
import best.tigers.tynkdialog.gui.view.components.cells.factories.TalkPageCellFactory;
import best.tigers.tynkdialog.gui.view.components.cells.overview.AbstractOverviewCellComponent;
import best.tigers.tynkdialog.util.Log;

public abstract class AbstractPageCellFactory {

  public static AbstractPageCellFactory getPageCellFactory(AbstractPageModel pageModel) {
    AbstractPageCellFactory factory;
    var pageKind = pageModel.getPage().getPageKind();
    switch (pageKind) {
      case "talk" -> factory = new TalkPageCellFactory();
      case "flat" -> factory = new FlatPageCellFactory();
      default -> {
        factory = null;
        Log.error("Could not render table! Unknown pageKind: " + pageKind);
      }
    }
    return factory;
  }

  public abstract AbstractDetailedCellComponent getDetailedComponent(AbstractPageModel pageModel,
      boolean isSelected);

  public abstract AbstractOverviewCellComponent getOverviewComponent(AbstractPageModel pageModel,
      boolean isSelected);
}
