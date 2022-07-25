package best.tigers.tynk_dialog.game;

import javax.json.Json;
import javax.json.JsonValue;

public class Style {
  private final String name;

  public Style(String name) {
    this.name = name;
  }

  public Style() {
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
