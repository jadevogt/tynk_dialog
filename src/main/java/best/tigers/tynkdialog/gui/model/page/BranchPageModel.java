package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import lombok.Getter;

public class BranchPageModel extends AbstractPageModel {
  @Getter
  private Leaf leaf;
  @Getter
  private String branchResult;
  @Getter
  private GenericListModel<BranchRequirement> requirementListModel;

  public BranchPageModel(BranchPage page) {
    super();
    setPage(page);
  }

  @Override
  public BranchPage getPage() {
    return new BranchPage(leaf, branchResult, requirementListModel.getContent());
  }

  @Override
  public void setPage(AbstractPage page) {
    var branchPage = (BranchPage) page;
    this.leaf = branchPage.getLeaf();
    this.branchResult = branchPage.getBranchResult();
    this.requirementListModel = new GenericListModel<>(branchPage.getRequirements());
    notifySubscribers();
  }

  @Override
  public AbstractPageModel continuationModel() {
    return new BranchPageModel(getPage());
  }

  public GenericListModel<BranchRequirement> cloneRequirements() {
    return new GenericListModel<>(requirementListModel.getContent());
  }

  public void setLeaf(Leaf leaf) {
    this.leaf = leaf;
    notifySubscribers();
  }

  public void setBranchResult(String branchResult) {
    this.branchResult = branchResult;
    notifySubscribers();
  }

  public void setRequirementListModel(
      GenericListModel<BranchRequirement> requirementListModel) {
    this.requirementListModel = requirementListModel;
    notifySubscribers();
  }
}
