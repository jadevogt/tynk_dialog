package best.tigers.tynkdialog.game.page;

import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

public class BranchPage extends AbstractPage {

  @Getter
  private final String pageKind = "branch";
  @Getter
  private final Leaf leaf;
  @Getter
  private final String branchResult;
  private final ArrayList<BranchRequirement> requirements;

  public BranchPage(Leaf leaf, String branchResult, List<BranchRequirement> requirements) {
    this.leaf = leaf;
    this.branchResult = branchResult;
    this.requirements = new ArrayList<>(requirements);
  }

  public List<BranchRequirement> getRequirements() {
    return Collections.unmodifiableList(requirements);
  }

  public BranchPage() {
    this.leaf = Leaf.JUMP;
    this.branchResult = "";
    this.requirements = new ArrayList<>();
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
