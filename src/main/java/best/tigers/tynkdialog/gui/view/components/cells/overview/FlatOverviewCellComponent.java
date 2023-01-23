package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import java.awt.Component;

public class FlatOverviewCellComponent extends AbstractOverviewCellComponent {

  private final FlatPageModel pageModel;

  public FlatOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    this.pageModel = (FlatPageModel) pageModel;
    add(new QuickPair("Flat", this.pageModel.getFlat()), Component.TOP_ALIGNMENT);
  }

  @Override
  public FlatPageModel getPageModel() {
    return pageModel;
  }
}
