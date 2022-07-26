package best.tigers.tynk_dialog.util;

import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.Optional;

public class ParseUtils {
  public static Optional<String> getNullableTynkValue(JsonValue val) {
    if (val instanceof JsonString) {
      JsonString str = (JsonString) val;
      return Optional.of(str.getString());
    }
    return Optional.empty();
  }

}
