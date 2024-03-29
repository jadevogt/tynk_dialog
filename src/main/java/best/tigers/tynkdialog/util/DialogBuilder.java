package best.tigers.tynkdialog.util;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.Dialog;
import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.util.page.BranchPageBuilder;
import best.tigers.tynkdialog.util.page.ChoicePageBuilder;
import best.tigers.tynkdialog.util.page.FlatPageBuilder;
import best.tigers.tynkdialog.util.page.PageBuilder;
import best.tigers.tynkdialog.util.page.TalkPageBuilder;
import java.util.ArrayList;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class DialogBuilder {

  private String title;
  private ArrayList<AbstractPage> contents;

  public DialogBuilder() {
  }

  void ParseJSON(JsonObject dialogData) throws PageParseException {
    title = dialogData.getString("title");
    contents = new ArrayList<>();
    for (JsonValue currentPage : dialogData.getJsonArray("contents")) {
      PageBuilder builder;
      String kind;
      try {
        kind = currentPage.asJsonObject().getString("pageKind");
      } catch (NullPointerException e) {
        kind = "talk";
      }
      switch (kind) {
        case "talk" -> builder = new TalkPageBuilder();
        case "flat" -> builder = new FlatPageBuilder();
        case "choice" -> builder = new ChoicePageBuilder();
        case "branch" -> builder = new BranchPageBuilder();
        default -> throw new PageParseException("Page kind " + kind + " is not yet supported!");
      }
      if (currentPage.getValueType() != JsonValue.ValueType.OBJECT) {
        throw new PageParseException(
            "Received an unexpected type while processing "
                + "dialog \""
                + title
                + "\": "
                + currentPage.getValueType());
      }
      builder.parseJson(currentPage.asJsonObject());
      contents.add(builder.build());
    }
  }

  public Dialog build() {
    return new Dialog(title, contents);
  }
}

