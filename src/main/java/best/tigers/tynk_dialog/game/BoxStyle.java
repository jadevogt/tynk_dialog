package best.tigers.tynk_dialog.game;

import java.nio.file.Path;
import javax.json.Json;
import javax.json.JsonValue;

public class BoxStyle {
  // private Path path;
  private String name;

  public BoxStyle(String name) {
    this.name = name;
  }

  public BoxStyle() {
    this.name = null;
  }

  @Override
  public String toString() {
    return name;
  }

  public JsonValue serialize() {
    if (java.util.Objects.nonNull(name)) {
      return Json.createValue(name);
    }
    return Json.createValue(-1);
  }
}
