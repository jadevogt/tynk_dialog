package best.tigers.tynk_dialog.game;

import java.nio.file.Path;
import javax.json.Json;
import javax.json.JsonValue;

public class Blip {
  private String name;

  public Blip(String name) {
    this.name = name;
  }

  public Blip() {
    this.name = null;
  }

  @Override
  public String toString() {
    String result = java.util.Objects.nonNull(name) ? name : "unnamed";
    return result;
  }

  public JsonValue serialize() {
    if (java.util.Objects.nonNull(name)) {
      return Json.createValue(this.name);
    }
    return Json.createValue(-1);
  }
}
