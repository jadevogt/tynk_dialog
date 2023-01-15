package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import java.awt.Component;

public class TalkOverviewCellComponent extends AbstractOverviewCellComponent {

  private final TalkPageModel pageModel;

  public TalkOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    this.pageModel = (TalkPageModel) pageModel;
    add(new QuickPair("Char", this.pageModel.getSpeaker()), Component.TOP_ALIGNMENT);
    add(new QuickPair("Blip", this.pageModel.getBlip()), Component.TOP_ALIGNMENT);
    add(new QuickPair("Style", this.pageModel.getTextBoxStyle()), Component.TOP_ALIGNMENT);
    add(new QuickPair("Skippable", Boolean.toString(this.pageModel.isCanSkip())),
        Component.TOP_ALIGNMENT);
  }

  @Override
  public TalkPageModel getPageModel() {
    return pageModel;
  }
}
