package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;

public class FlatPage implements Page {

  private String flat;

  public FlatPage(String flat) {
    this.flat = flat;
  }

  public String getFlat() {
    return flat;
  }

  public void setFlat(String flat) {
    this.flat = flat;
  }

  @Override
  public String getPageKind() {
    return "flat";
  }

  @Override
  public JsonObject serialize() {
    var result = Json.createObjectBuilder();
    result.add("flat", flat);
    return result.build();
  }
}
