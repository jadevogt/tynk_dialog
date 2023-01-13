package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;

public class TalkPage implements Page {

  private String speaker;
  private String content;
  private String textStyle;
  private String blip;
  private boolean canSkip;

  public TalkPage(
      String speaker, String content, String textStyle, String blip, boolean canSkip) {
    this.content = content;
    this.speaker = speaker;
    this.textStyle = textStyle;
    this.blip = blip;
    this.canSkip = canSkip;
  }

  public TalkPage(String speaker) {
    this(speaker, "", null, null, true);
  }

  public TalkPage() {
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

  public String getTextBoxStyle() {
    return textStyle;
  }

  public void setTextBoxStyle(String newStyle) {
    textStyle = newStyle;
  }

  @Override
  public String toString() {
    return speaker + " SAYS: \n" + content + "\nWITH SOUND " + blip;
  }

  public boolean isCanSkip() {
    return canSkip;
  }

  public void setCanSkip(boolean skippable) {
    canSkip = skippable;
  }

  @Override
  public String getPageKind() {
    return "talk";
  }

  public JsonObject serialize() {
    javax.json.JsonObjectBuilder result =
        Json.createObjectBuilder()
            .add("txt", content)
            .add("canSkip", canSkip)
            .add("speaker", speaker);
    if (blip != null) {
      result.add("blip", blip);
    } else {
      result.add("blip", -1);
    }
    if (textStyle != null) {
      result.add("textbox", textStyle);
    } else {
      result.add("textbox", -1);
    }
    return result.build();
  }
}