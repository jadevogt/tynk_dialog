package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

public class BranchPageModel extends AbstractPageModel {
  @Getter @Setter
  private GenericListModel<BranchRequirementModel> requirementListModel;
  @Delegate
  private BranchPage model;

  public BranchPageModel(BranchPage model) {
    this.model = model;
    this.requirementListModel = new GenericListModel<>(model.getRequirements().stream().map(
        BranchRequirementModel::new).toList());
  }

  public BranchPageModel() {
    this.model = new BranchPage();
  }

  @Override
  public BranchPage getPage() {
    return model;
  }

  @Override
  public void setPage(AbstractPage page) {
    this.model = (BranchPage) page;
    this.requirementListModel = new GenericListModel<>(model.getRequirements().stream().map(
        BranchRequirementModel::new).toList());
  }

  @Override
  public AbstractPageModel continuationModel() {
    return null;
  }

  public GenericListModel<BranchRequirementModel> cloneRequirements() {
    return new GenericListModel<>(this.requirementListModel.getContent().stream().map(m -> new BranchRequirementModel(m.getRequirement().clone())).toList());
  }
}
