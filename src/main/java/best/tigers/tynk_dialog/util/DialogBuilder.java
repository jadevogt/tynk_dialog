package best.tigers.tynk_dialog.util;

import best.tigers.tynk_dialog.exceptions.DialogParseException;
import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.game.DialogPage;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;

public class DialogBuilder {
  private String title;
  private ArrayList<DialogPage> contents;

  public DialogBuilder() {
  }

  void ParseJSON(JsonObject dialogData) throws DialogParseException {
    title = dialogData.getString("title");
    contents = new ArrayList<DialogPage>();
    for (JsonValue currentPage : dialogData.getJsonArray("contents")) {
      DialogPageBuilder pageBuilder = new DialogPageBuilder();
      if (currentPage.getValueType() != JsonValue.ValueType.OBJECT) {
        throw new DialogParseException("Received an unexpected type while processing "
                + "dialog \"" + title + "\": " + currentPage.getValueType());
      }
      pageBuilder.ParseJSON(currentPage.asJsonObject());
      contents.add(pageBuilder.build());
    }
  }

  public Dialog build() {
    return new Dialog(title, contents);
  }
}

class DialogPageBuilder {
  private String content;
  private String speaker;
  private String textBoxStyle;
  private String blip;
  private boolean canSkip;

  public DialogPageBuilder() {
  }

  public boolean verify() {
    return content != null && speaker != null;
  }

  public void ParseJSON(JsonObject dialogPageData) throws DialogParseException {
    try {
      content = dialogPageData.getString("txt");
      speaker = dialogPageData.getString("speaker");
      canSkip = dialogPageData.getBoolean("canSkip");
    } catch (ClassCastException cce) {
      throw new DialogParseException("Invalid DialogPage data: " + dialogPageData);
    }
    textBoxStyle = ParseUtils.getNullableTynkValue(dialogPageData.get("textbox")).orElse(null);
    blip = ParseUtils.getNullableTynkValue(dialogPageData.get("blip")).orElse(null);
  }

  public DialogPage build() {
    return new DialogPage(speaker, content, textBoxStyle, blip, canSkip);
  }
}
