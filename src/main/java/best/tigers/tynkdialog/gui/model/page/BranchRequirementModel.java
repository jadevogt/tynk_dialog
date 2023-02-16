package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.BranchRequirement.Category;
import best.tigers.tynkdialog.game.page.BranchRequirement.Comparison;
import best.tigers.tynkdialog.game.page.BranchRequirement.ValueType;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.ChoiceResponse.ResponseIcon;
import best.tigers.tynkdialog.gui.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

public class BranchRequirementModel extends AbstractModel {

  @Getter
  private String flag;
  @Getter
  private Category category;
  @Getter
  private String value;
  @Getter
  private ValueType valueType;
  @Getter
  private Comparison comparison;

  public BranchRequirementModel(BranchRequirement requirement) {
    super();
    setRequirement(requirement);
  }

  public void setRequirement(BranchRequirement requirement) {
    flag = requirement.getFlag();
    category = requirement.getCategory();
    value = requirement.getValue();
    valueType = requirement.getValueType();
    comparison = requirement.getComparison();
    notifySubscribers();
  }

  @Override
  public String toString() {
    return getRequirement().toString();
  }

  public BranchRequirement getRequirement() {
    return new BranchRequirement(flag, category, value, valueType, comparison);
  }

  public BranchRequirementModel clone() {
    return new BranchRequirementModel(getRequirement());
  }

  public void setFlag(String flag) {
    this.flag = flag;
    notifySubscribers();
  }

  public void setCategory(Category category) {
    this.category = category;
    notifySubscribers();
  }

  public void setValue(String value) {
    this.value = value;
    notifySubscribers();
  }

  public void setValueType(ValueType valueType) {
    this.valueType = valueType;
    notifySubscribers();
  }

  public void setComparison(Comparison comparison) {
    this.comparison = comparison;
    notifySubscribers();
  }
}
