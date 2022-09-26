package best.tigers.tatffdialogutility.util;

import java.util.Optional;
import javax.json.JsonString;
import javax.json.JsonValue;

public final class ParseUtils {
  private ParseUtils() {}
  public static Optional<String> getNullableTynkValue(JsonValue val) {
    if (val instanceof JsonString str) {
      return Optional.of(str.getString());
    }
    return Optional.empty();
  }
}
