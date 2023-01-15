package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;

public abstract class AbstractPage {

  public abstract String getPageKind();

  public abstract JsonObject serialize();

  public JsonObject asPage() {
    var builder = Json.createObjectBuilder(serialize());
    builder.add("pageKind", getPageKind());
    return builder.build();
  }
}
