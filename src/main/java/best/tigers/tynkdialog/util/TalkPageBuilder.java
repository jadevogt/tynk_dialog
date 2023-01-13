package best.tigers.tynkdialog.util;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.TalkPage;
import javax.json.JsonObject;

class TalkPageBuilder implements PageBuilder {

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

  public void parseJson(JsonObject dialogPageData) throws PageParseException {
    try {
      content = dialogPageData.getString("txt");
      speaker = dialogPageData.getString("speaker");
      canSkip = dialogPageData.getBoolean("canSkip");
    } catch (ClassCastException cce) {
      throw new PageParseException("Invalid DialogPage data: " + dialogPageData);
    }
    textBoxStyle = ParseUtils.getNullableTynkValue(dialogPageData.get("textbox")).orElse(null);
    blip = ParseUtils.getNullableTynkValue(dialogPageData.get("blip")).orElse(null);
  }

  public TalkPage build() {
    return new TalkPage(speaker, content, textBoxStyle, blip, canSkip);
  }
}
