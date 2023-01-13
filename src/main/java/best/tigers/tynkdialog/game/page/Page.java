package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;

public interface Page {

  String getPageKind();

  JsonObject serialize();

  default JsonObject asPage() {
    var builder = Json.createObjectBuilder(serialize());
    builder.add("pageKind", getPageKind());
    return builder.build();
  }
}
