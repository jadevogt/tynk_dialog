package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.List;

public class ChoicePage implements Page {

  private String speaker;
  private String content;
  private String blip;
  private ArrayList<String> gifts;
  private ArrayList<ChoiceResponse> responses;
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

  public String getSpeaker() {
    return speaker;
  }

  public void setSpeaker(String newCharacter) {
    speaker = newCharacter;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String newContent) {
    content = newContent;
  }

  public String getBlip() {
    return blip;
  }

  public void setBlip(String newBlip) {
    blip = newBlip;
  }

  public boolean isCanSkip() {
    return canSkip;
  }

  public void setCanSkip(boolean skippable) {
    canSkip = skippable;
  }

  public ArrayList<String> getGifts() {
    return gifts;
  }

  public void setGifts(List<String> newGifts) {
    gifts = new ArrayList<>(newGifts);
  }

  public ArrayList<ChoiceResponse> getResponses() {
    return responses;
  }

  public void setResponses(List<ChoiceResponse> newResponses) {
    responses = new ArrayList<>(newResponses);
  }

  @Override
  public String getPageKind() {
    return "choice";
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
}
