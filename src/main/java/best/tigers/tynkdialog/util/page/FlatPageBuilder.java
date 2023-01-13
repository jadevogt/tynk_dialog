package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.FlatPage;
import best.tigers.tynkdialog.game.page.Page;

import javax.json.JsonObject;

public class FlatPageBuilder implements PageBuilder{
  private String flat;
  @Override
  public void parseJson(JsonObject pageData) throws PageParseException {
    flat = pageData.getString("flat");
  }

  @Override
  public boolean verify() {
    return flat != null;
  }

  @Override
  public Page build() {
    return new FlatPage(flat);
  }
}
