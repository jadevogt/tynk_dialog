package best.tigers.tynk_dialog.game;

import javax.json.Json;
import javax.json.JsonValue;

public class Blip {
  private final String name;

  public Blip(String name) {
    this.name = name;
  }

  public Blip() {
    this.name = null;
  }

  @Override
  public String toString() {
    return java.util.Objects.nonNull(name) ? name : "unnamed";
  }

  public JsonValue serialize() {
    if (java.util.Objects.nonNull(name)) {
      return Json.createValue(this.name);
    }
    return Json.createValue(-1);
  }
}
