package best.tigers.tynkdialog.util;

import java.util.Optional;
import javax.json.JsonString;
import javax.json.JsonValue;

public class ParseUtils {

  public static Optional<String> getNullableTynkValue(JsonValue val) {
    if (val instanceof JsonString jsonString) {
      return Optional.of(jsonString.getString());
    }
    return Optional.empty();
  }
}
