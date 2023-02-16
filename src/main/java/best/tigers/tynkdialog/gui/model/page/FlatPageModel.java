package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.FlatPage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

public class FlatPageModel extends AbstractPageModel {
  @Getter
  private String flat;

  public FlatPageModel(FlatPage flatPage) {
    super();
    setPage(flatPage);
  }

  @Override
  public void setPage(AbstractPage page) {
    var flatPage = (FlatPage) page;
    this.flat = flatPage.getFlat();
    notifySubscribers();
  }

  @Override
  public FlatPage getPage() {
    return new FlatPage(flat);
  }

  @Override
  public FlatPageModel continuationModel() {
    return new FlatPageModel(getPage());
  }

  public void setFlat(String flat) {
    this.flat = flat;
    notifySubscribers();
  }
}
