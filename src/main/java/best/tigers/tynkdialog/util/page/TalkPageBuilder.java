package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.util.ParseUtils;

import javax.json.JsonObject;

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
      canSkip = pageData.getBoolean("canSkip");
    } catch (ClassCastException cce) {
      throw new PageParseException("Invalid page data: " + pageData);
    }
    textBoxStyle = ParseUtils.getNullableTynkString(pageData.get("textbox")).orElse(null);
    blip = ParseUtils.getNullableTynkString(pageData.get("blip")).orElse(null);
  }

  public TalkPage build() {
    return new TalkPage(speaker, content, textBoxStyle, blip, canSkip);
  }
}
