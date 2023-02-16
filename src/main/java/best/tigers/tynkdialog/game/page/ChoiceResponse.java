package best.tigers.tynkdialog.game.page;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.Getter;
import lombok.Setter;

public class ChoiceResponse {

  @Getter
  private final String content;
  @Getter
  private final String choiceResult;
  @Getter
  private final ResponseIcon icon;

  public ChoiceResponse(String content, String choiceResult, ResponseIcon icon) {
    this.content = content;
    this.choiceResult = choiceResult;
    this.icon = icon;
  }

  public ChoiceResponse(String content, String choiceResult, int icon) {
    this(content, choiceResult, correspondingSymbol(icon));
  }

  public ChoiceResponse() {
    this("", null, ResponseIcon.NEUTRAL);
  }

  static ResponseIcon correspondingSymbol(int id) {
    ResponseIcon responseIcon;
    switch (id) {
      case 0 -> responseIcon = ResponseIcon.CANCEL;
      case 2 -> responseIcon = ResponseIcon.ACT;
      case 3 -> responseIcon = ResponseIcon.SPEAK;
      case 4 -> responseIcon = ResponseIcon.GIFT;
      default -> responseIcon = ResponseIcon.NEUTRAL;
    }
    return responseIcon;
  }

  @Override
  public String toString() {
    return content;
  }

  public JsonObject serialize() {
    JsonObjectBuilder result = Json.createObjectBuilder();
    result.add("txt", content);
    if (choiceResult != null) {
      result.add("result", choiceResult);
    } else {
      result.add("result", -1);
    }
    int iconValue;
    switch (icon) {
      case CANCEL -> iconValue = 0;
      case ACT -> iconValue = 2;
      case SPEAK -> iconValue = 3;
      case GIFT -> iconValue = 4;
      default -> iconValue = 1;
    }
    result.add("symbol", iconValue);
    return result.build();
  }

  public enum ResponseIcon {
    CANCEL,
    NEUTRAL,
    ACT,
    SPEAK,
    GIFT
  }
}
