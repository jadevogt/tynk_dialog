package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import lombok.Getter;

public class BranchOverviewCellComponent extends AbstractOverviewCellComponent {

  @Getter
  private BranchPageModel pageModel;

  public BranchOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    var model = (BranchPageModel) pageModel;
    add(new QuickPair("Result", model.getBranchResult()));
    add(new QuickPair("Requirements", Integer.toString(model.getRequirementListModel().getContent().size())));
  }

}
