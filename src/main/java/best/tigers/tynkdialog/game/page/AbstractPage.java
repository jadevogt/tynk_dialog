package best.tigers.tynkdialog.game.page;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public abstract class AbstractPage {

  public abstract String getPageKind();

  public abstract JsonObject serialize();

  public JsonObject asPage() {
    var builder = Json.createObjectBuilder(serialize());
    builder.add("pageKind", getPageKind());
    return builder.build();
  }
}
