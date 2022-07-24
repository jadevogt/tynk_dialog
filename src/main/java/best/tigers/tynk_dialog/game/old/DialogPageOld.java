package best.tigers.tynk_dialog.game.old;

import best.tigers.tynk_dialog.game.Blip;
import best.tigers.tynk_dialog.game.Character;
import best.tigers.tynk_dialog.game.FormattedText;
import best.tigers.tynk_dialog.game.Style;

import javax.json.Json;
import javax.json.JsonObject;

public class DialogPageOld {
  private Character speaker;
  private FormattedText content;
  private Style textStyle;
  private Blip blip;
  private boolean canSkip;

  public DialogPageOld(
          Character speaker, FormattedText content, Style textStyle, Blip blip, boolean canSkip) {
    this.content = content;
    this.speaker = speaker;
    this.textStyle = textStyle;
    this.blip = blip;
    this.canSkip = canSkip;
  }

  public DialogPageOld(String raw_speaker, String raw_content, String raw_textBoxStyle,
                       String raw_blip, boolean canSkip) {
    speaker = new Character(raw_speaker);
    content = new FormattedText(raw_content);
    textStyle = new Style(raw_textBoxStyle);
    blip = new Blip(raw_blip);
    this.canSkip = canSkip;
  }

  public DialogPageOld(Character speaker) {
    this(speaker, new FormattedText(), new Style(), new Blip(), true);
  }

  public DialogPageOld() {
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
  public Style getTextBoxStyle() {
    return textStyle;
  }

  public void setTextBoxStyle(String newStyle) {
    textStyle = new Style(newStyle);
  }

  public String toString() {
    return speaker.toString() + " SAYS: \n" + content.toString() + "\nWITH SOUND " + blip.toString();
  }

  public JsonObject serialize() {
    var result =
        Json.createObjectBuilder()
            .add("textbox", textStyle.serialize())
            .add("txt", content.toString())
            .add("speaker", speaker.toString())
            .add("blip", blip.serialize())
            .add("canSkip", canSkip)
            .build();
    return result;
  }
}
