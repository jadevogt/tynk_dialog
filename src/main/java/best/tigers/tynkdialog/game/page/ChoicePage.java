package best.tigers.tynkdialog.game.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.Getter;

public class ChoicePage extends AbstractPage {

  @Getter
  private final String pageKind = "choice";
  @Getter
  private final String speaker;
  @Getter
  private final String content;
  @Getter
  private final String blip;
  private final ArrayList<String> gifts;
  private final ArrayList<ChoiceResponse> responses;
  @Getter
  private final boolean canSkip;

  public ChoicePage(
      String speaker, String content, String blip, boolean canSkip, List<String> gifts,
      List<ChoiceResponse> responses) {
    this.content = content;
    this.speaker = speaker;
    this.blip = blip;
    this.canSkip = canSkip;
    this.gifts = new ArrayList<>(gifts);
    this.responses = new ArrayList<>(responses);
  }

  public ChoicePage(String speaker) {
    this(speaker, "", null, true, new ArrayList<>(), new ArrayList<>());
  }

  public ChoicePage() {
    this("");
  }

  public JsonObject serialize() {
    JsonObjectBuilder result = Json.createObjectBuilder();
    result.add("txt", content);
    result.add("canSkip", canSkip);
    result.add("speaker", speaker);
    if (blip != null) {
      result.add("blip", blip);
    } else {
      result.add("blip", -1);
    }

    var giftsArray = Json.createArrayBuilder();
    gifts.forEach(giftsArray::add);
    result.add("gifts", giftsArray);

    var responseArray = Json.createArrayBuilder();
    responses.stream().map(ChoiceResponse::serialize).forEachOrdered(responseArray::add);
    result.add("responses", responseArray);

    return result.build();
  }

  public List<String> getGifts() {
    return Collections.unmodifiableList(gifts);
  }

  public List<ChoiceResponse> getResponses() {
    return Collections.unmodifiableList(responses);
  }
}
