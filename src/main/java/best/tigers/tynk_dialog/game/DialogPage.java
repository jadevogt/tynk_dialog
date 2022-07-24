package best.tigers.tynk_dialog.game;

import javax.json.JsonObject;
import javax.json.Json;

public class DialogPage {
  private String speaker;
  private String content;
  private String textStyle;
  private String blip;
  private boolean canSkip;

  public DialogPage(
          String speaker, String content, String textStyle, String blip, boolean canSkip) {
    this.content = content;
    this.speaker = speaker;
    this.textStyle = textStyle;
    this.blip = blip;
    this.canSkip = canSkip;
  }

  public DialogPage(String speaker) {
    this(speaker, "-", null, null, true);
  }

  public DialogPage() {
    this("Unnamed Character");
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

  public String toString() {
    return speaker + " SAYS: \n" + content + "\nWITH SOUND " + blip;
  }

  public JsonObject serialize() {
    var result =
        Json.createObjectBuilder()
            .add("textbox", textStyle)
            .add("txt", content)
            .add("speaker", speaker)
            .add("blip", blip)
            .add("canSkip", canSkip)
            .build();
    return result;
  }
}
