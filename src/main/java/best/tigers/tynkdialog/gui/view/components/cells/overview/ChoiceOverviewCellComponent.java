package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import best.tigers.tynkdialog.gui.view.components.SuperTextEditorPane;
import lombok.Getter;

public class ChoiceOverviewCellComponent extends AbstractOverviewCellComponent {

  @Getter
  private ChoicePageModel pageModel;

  public ChoiceOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    super(pageModel, isSelected);
    var page = (ChoicePageModel) pageModel;

    add(new QuickPair("Choices", Integer.toString(page.getResponseListModel().getSize())));
  }

}
