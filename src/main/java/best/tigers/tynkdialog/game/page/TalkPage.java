package best.tigers.tynkdialog.game.page;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Getter;

public class TalkPage extends AbstractPage {

  @Getter
  private final String pageKind = "talk";
  @Getter
  private final String speaker;
  @Getter
  private final String content;
  @Getter
  private final String textStyle;
  @Getter
  private final String blip;
  @Getter
  private final boolean canSkip;

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

  @Override
  public String toString() {
    return speaker + " SAYS: \n" + content + "\nWITH SOUND " + blip;
  }

  public JsonObject serialize() {
    jakarta.json.JsonObjectBuilder result =
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
