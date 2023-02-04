package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.util.ParseUtils;
import best.tigers.tynkdialog.util.PredictiveTextService;
import jakarta.json.JsonObject;

public class TalkPageBuilder implements PageBuilder {

  private String content;
  private String speaker;
  private String textBoxStyle;
  private String blip;
  private boolean canSkip;

  public TalkPageBuilder() {
  }

  public boolean verify() {
    return content != null && speaker != null;
  }

  public void parseJson(JsonObject pageData) throws PageParseException {
    try {
      content = pageData.getString("txt");
      speaker = pageData.getString("speaker");
      PredictiveTextService.getInstance().incrementTerm("characters", speaker);
      canSkip = pageData.getBoolean("canSkip");
    } catch (ClassCastException cce) {
      throw new PageParseException("Invalid page data: " + pageData);
    }
    textBoxStyle = ParseUtils.getNullableTynkString(pageData.get("textbox")).orElse(null);
    if (textBoxStyle != null) {
      PredictiveTextService.getInstance().incrementTerm("textboxes", textBoxStyle);
    }
    blip = ParseUtils.getNullableTynkString(pageData.get("blip")).orElse(null);
    if (blip != null) {
      PredictiveTextService.getInstance().incrementTerm("blips", blip);
    }
  }

  public TalkPage build() {
    return new TalkPage(speaker, content, textBoxStyle, blip, canSkip);
  }
}
