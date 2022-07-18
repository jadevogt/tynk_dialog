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

  public DialogPage(Character speaker) {
    this(speaker, new FormattedText(), new BoxStyle(), new Blip(), true);
  }

  public DialogPage() {
    this(new Character());
  }

  public String toString() {
    String result =
        speaker.toString() + " SAYS: \n" + content.toString() + "\nWITH SOUND " + blip.toString();
    return result;
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
