package best.tigers.tynkdialog.game.page;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

public class FlatPage extends AbstractPage {

  @Getter
  private final String pageKind = "flat";
  @Getter
  private final String flat;

  public FlatPage(String flat) {
    this.flat = flat;
  }

  @Override
  public JsonObject serialize() {
    var result = Json.createObjectBuilder();
    result.add("flat", flat);
    return result.build();
  }
}
