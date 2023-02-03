package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.AbstractPage;
import jakarta.json.JsonObject;

public interface PageBuilder {

  void parseJson(JsonObject pageData) throws PageParseException;

  boolean verify();

  AbstractPage build();
}
