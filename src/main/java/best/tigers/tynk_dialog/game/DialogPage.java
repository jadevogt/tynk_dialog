package best.tigers.tynk_dialog.game;

import javax.json.JsonObject;
import javax.json.Json;

public class DialogPage {
  private Character speaker;
  private FormattedText content;
  private BoxStyle textBoxStyle;
  private Blip blip;
  private boolean canSkip;

  public DialogPage(
      Character speaker, FormattedText content, BoxStyle textBoxStyle, Blip blip, boolean canSkip) {
    this.content = content;
    this.speaker = speaker;
    this.textBoxStyle = textBoxStyle;
    this.blip = blip;
    this.canSkip = canSkip;
  }

  public DialogPage(String raw_speaker, String raw_content, String raw_textBoxStyle,
                    String raw_blip, boolean canSkip) {
    speaker = new Character(raw_speaker);
    content = new FormattedText(raw_content);
    textBoxStyle = new BoxStyle(raw_textBoxStyle);
    blip = new Blip(raw_blip);
    this.canSkip = canSkip;
  }

  public DialogPage(Character speaker) {
    this(speaker, new FormattedText(), new BoxStyle(), new Blip(), true);
  }

  public DialogPage() {
    this(new Character());
  }

  public Character getSpeaker() {
    return speaker;
  }

  public void setSpeaker(String newCharacter) {
    speaker = new Character(newCharacter);
  }

  public FormattedText getContent() {
    return content;
  }

  public void setContent(String newContent) {
    content = new FormattedText(newContent);
  }

  public Blip getBlip() {
    return blip;
  }

  public void setBlip(String newBlip) {
    blip = new Blip(newBlip);
  }
  public BoxStyle getTextBoxStyle() {
    return textBoxStyle;
  }

  public void setTextBoxStyle(String newStyle) {
    textBoxStyle = new BoxStyle(newStyle);
  }

  public String toString() {
    return speaker.toString() + " SAYS: \n" + content.toString() + "\nWITH SOUND " + blip.toString();
  }

  public JsonObject serialize() {
    var result =
        Json.createObjectBuilder()
            .add("textbox", textBoxStyle.serialize())
            .add("txt", content.toString())
            .add("speaker", speaker.toString())
            .add("blip", blip.serialize())
            .add("canSkip", canSkip)
            .build();
    return result;
  }
}
