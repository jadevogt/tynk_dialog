package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.FlatPage;
import lombok.Getter;

public class FlatPageModel extends AbstractPageModel {

  @Getter
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
    notifySubscribers();
  }

  @Override
  public FlatPageModel clone() {
    var newModel = new FlatPageModel();
    newModel.setFlat(getFlat());
    return newModel;
  }

}
