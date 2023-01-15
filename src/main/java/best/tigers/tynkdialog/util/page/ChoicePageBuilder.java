package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.util.ParseUtils;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.json.JsonString;

public class ChoicePageBuilder implements PageBuilder {

  private String content;
  private String speaker;
  private String textBoxStyle;
  private String blip;
  private boolean canSkip;
  private List<ChoiceResponse> responses;
  private List<String> gifts;

  public ChoicePageBuilder() {
  }

  public static ChoiceResponse parseResponseJson(JsonObject responseData)
      throws PageParseException {
    String responseContent;
    String symbol;
    try {
      responseContent = responseData.getString("txt");
    } catch (ClassCastException cce) {
      throw new PageParseException("Invalid choice response data: " + responseData);
    }
    var iconValue = ParseUtils.getNullableTynkInteger(responseData.get("symbol")).orElse(1);
    var resultValue = ParseUtils.getNullableTynkString(responseData.get("result")).orElse(null);
    return new ChoiceResponse(responseContent, resultValue, iconValue);
  }

  public boolean verify() {
    return content != null && speaker != null;
  }

  public void parseJson(JsonObject json) throws PageParseException {
    try {
      content = json.getString("txt");
      speaker = json.getString("speaker");
      canSkip = json.getBoolean("canSkip");
    } catch (ClassCastException cce) {
      throw new PageParseException("Invalid page data: " + json);
    }
    blip = ParseUtils.getNullableTynkString(json.get("blip")).orElse(null);
    gifts = !json.containsKey("gifts") ? new ArrayList<>() : json.getJsonArray("gifts")
        .stream()
        .filter(JsonString.class::isInstance)
        .map(JsonString.class::cast)
        .map(JsonString::getString)
        .toList();
    responses = !json.containsKey("responses") ? new ArrayList<>() : json.getJsonArray("responses")
        .stream()
        .filter(JsonObject.class::isInstance)
        .map(JsonObject.class::cast)
        .map(ChoicePageBuilder::parseResponseJson)
        .toList();
  }

  public ChoicePage build() {
    return new ChoicePage(speaker, content, blip, canSkip, gifts, responses);
  }
}
