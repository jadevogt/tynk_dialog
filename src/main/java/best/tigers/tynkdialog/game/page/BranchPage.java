package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;

public class BranchPage extends AbstractPage {

  private Leaf leaf;
  private String branchResult;
  private ArrayList<BranchRequirement> requirements;

  public Leaf getLeaf() {
    return leaf;
  }

  public void setLeaf(Leaf leaf) {
    this.leaf = leaf;
  }

  public String getBranchResult() {
    return branchResult;
  }

  public void setBranchResult(String branchResult) {
    this.branchResult = branchResult;
  }

  public ArrayList<BranchRequirement> getRequirements() {
    return requirements;
  }

  public void setRequirements(
          ArrayList<BranchRequirement> requirements) {
    this.requirements = requirements;
  }

  @Override
  public String getPageKind() {
    return "branch";
  }

  @Override
  public JsonObject serialize() {
    var result = Json.createObjectBuilder();
    String leafValue;
    switch (leaf) {
      case ADD -> leafValue = "add";
      default -> leafValue = "jump";
    }
    result.add("leaf", leafValue);
    if (branchResult != null) {
      result.add("result", branchResult);
    } else {
      result.add("result", -1);
    }
    var requirementsArray = Json.createArrayBuilder();
    requirements.forEach(r -> requirementsArray.add(r.serialize()));
    result.add("req", requirementsArray);
    return result.build();
  }

  public enum Leaf {
    JUMP,
    ADD,
  }
}
