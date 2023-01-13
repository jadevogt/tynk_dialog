package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.FlatPage;

import javax.json.JsonObject;

public class FlatPageModel extends AbstractPageModel {
  private final FlatPage page;

  public FlatPageModel(FlatPage flatPage) {
    this.page = flatPage;
  }

  public FlatPageModel() {
    this(new FlatPage(null));
  }

  public String getFlat() {
    return page.getFlat();
  }

  public void setFlat(String newFlat) {
    page.setFlat(newFlat);
  }

  public JsonObject asJson() {
    return page.asPage();
  }

  @Override
  public FlatPage getPage() {
    return page;
  }

  @Override
  public FlatPageModel clone() {
    var newModel = new FlatPageModel();
    newModel.setFlat(getFlat());
    return newModel;
  }

  @Override
  public String getTitleContent() {
    return getFlat();
  }
}
