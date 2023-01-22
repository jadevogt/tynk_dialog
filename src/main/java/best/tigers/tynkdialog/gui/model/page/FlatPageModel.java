package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.FlatPage;
import lombok.Getter;
import lombok.experimental.Delegate;

public class FlatPageModel extends AbstractPageModel {

  @Getter
  @Delegate
  private FlatPage page;

  public FlatPageModel(FlatPage flatPage) {
    this.page = flatPage;
  }

  public FlatPageModel() {
    this(new FlatPage(null));
  }

  @Override
  public void setPage(AbstractPage page) {
    this.page = (FlatPage) page;
  }

  @Override
  public FlatPageModel continuationModel() {
    var newModel = new FlatPageModel();
    newModel.setFlat(getFlat());
    return newModel;
  }

}
