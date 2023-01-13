package best.tigers.tynkdialog.util;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.Optional;

public class ParseUtils {

  public static Optional<String> getNullableTynkString(JsonValue val) {
    if (val instanceof JsonString jsonString) {
      return Optional.of(jsonString.getString());
    }
    return Optional.empty();
  }

  public static Optional<Integer> getNullableTynkInteger(JsonValue val) {
    if (val instanceof JsonNumber jsonNumber && jsonNumber.intValue() != -1) {
      return Optional.of(jsonNumber.intValue());
    }
    return Optional.empty();
  }
}
