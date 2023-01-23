package best.tigers.tynkdialog.game.page;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import lombok.Getter;
import lombok.Setter;

public class ChoicePage extends AbstractPage {

  @Getter
  private final String pageKind = "choice";
  @Getter
  @Setter
  private String speaker;
  @Getter
  @Setter
  private String content;
  @Getter
  @Setter
  private String blip;
  @Getter
  @Setter
  private ArrayList<String> gifts;
  @Getter
  private ArrayList<ChoiceResponse> responses;
  @Getter
  @Setter
  private boolean canSkip;

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

  public void setResponses(List<ChoiceResponse> newResponses) {
    this.responses = new ArrayList<>(newResponses);
  }
}
