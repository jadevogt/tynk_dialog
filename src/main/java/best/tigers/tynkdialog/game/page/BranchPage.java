package best.tigers.tynkdialog.game.page;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

public class BranchPage extends AbstractPage {

  @Getter
  private final String pageKind = "branch";
  @Getter
  @Setter
  private Leaf leaf;
  @Getter
  @Setter
  private String branchResult;
  @Getter
  @Setter
  private ArrayList<BranchRequirement> requirements;

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
