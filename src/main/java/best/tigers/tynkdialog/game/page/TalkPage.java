package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

public class TalkPage extends AbstractPage {

  @Getter
  private final String pageKind = "talk";
  @Getter
  @Setter
  private String speaker;
  @Getter
  @Setter
  private String content;
  @Getter
  @Setter
  private String textStyle;
  @Getter
  @Setter
  private String blip;
  @Getter
  @Setter
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

  @Override
  public String toString() {
    return speaker + " SAYS: \n" + content + "\nWITH SOUND " + blip;
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
