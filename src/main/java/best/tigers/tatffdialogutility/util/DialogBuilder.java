package best.tigers.tatffdialogutility.util;

import best.tigers.tatffdialogutility.exceptions.DialogParseException;
import best.tigers.tatffdialogutility.game.Dialog;
import best.tigers.tatffdialogutility.game.DialogPage;
import java.util.ArrayList;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class DialogBuilder {
  private String title;
  private ArrayList<DialogPage> contents;

  public DialogBuilder() {}

  void ParseJSON(JsonObject dialogData) throws DialogParseException {
    title = dialogData.getString("title");
    contents = new ArrayList<>();
    for (JsonValue currentPage : dialogData.getJsonArray("contents")) {
      DialogPageBuilder pageBuilder = new DialogPageBuilder();
      if (currentPage.getValueType() != JsonValue.ValueType.OBJECT) {
        throw new DialogParseException(
            "Received an unexpected type while processing "
                + "dialog \""
                + title
                + "\": "
                + currentPage.getValueType());
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

  DialogPageBuilder() {}

  public boolean verify() {
    return content != null && speaker != null;
  }

  public void ParseJSON(JsonObject dialogPageData) throws DialogParseException {
    content = dialogPageData.getString("txt");
    speaker = dialogPageData.getString("speaker");
    canSkip = dialogPageData.getBoolean("canSkip");
    textBoxStyle = ParseUtils.getNullableTynkValue(dialogPageData.get("textbox")).orElse(null);
    blip = ParseUtils.getNullableTynkValue(dialogPageData.get("blip")).orElse(null);
  }

  public DialogPage build() {
    return new DialogPage(speaker, content, textBoxStyle, blip, canSkip);
  }
}
