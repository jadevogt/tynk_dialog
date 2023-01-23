package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.BranchRequirement.Category;
import best.tigers.tynkdialog.game.page.BranchRequirement.Comparison;
import best.tigers.tynkdialog.game.page.BranchRequirement.ValueType;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.ChoiceResponse.ResponseIcon;
import best.tigers.tynkdialog.gui.model.AbstractModel;

public class BranchRequirementModel extends AbstractModel {

  private final BranchRequirement requirement;

  public BranchRequirementModel(BranchRequirement requirement) {
    this.requirement = requirement;
  }

  public String getFlag() {
    return requirement.getFlag();
  }

  public void setFlag(String flag) {
    requirement.setFlag(flag);
  }

  public Category getCategory() {
    return requirement.getCategory();
  }

  public void setCategory(Category category) {
    requirement.setCategory(category);
  }

  public String getValue() {
    return requirement.getValue();
  }

  public void setValue(String value) {
    requirement.setValue(value);
  }

  public ValueType getValueType(){
    return requirement.getValueType();
  }

  public void setValueType(ValueType valueType) {
    requirement.setValueType(valueType);
  }

  public Comparison getComparison() {
    return requirement.getComparison();
  }

  public void setComparison(Comparison comparison) {
    requirement.setComparison(comparison);
  }


  public BranchRequirement getRequirement() {
    return requirement;
  }

  @Override
  public String toString() {
    var str = new StringBuilder();
    str.append(requirement.getFlag());
    str.append(" (");
    str.append(BranchRequirement.valueTypeCanonical(requirement.getValueType()));
    str.append(") ");
    str.append(BranchRequirement.comparisonCanonical(requirement.getComparison()));
    str.append(" ");
    str.append(requirement.getValue());
    return str.toString();
  }

  public BranchRequirementModel clone() {
    return new BranchRequirementModel(requirement.clone());
  }
}
