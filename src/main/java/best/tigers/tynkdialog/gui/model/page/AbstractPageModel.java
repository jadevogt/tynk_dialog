package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.gui.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractPageModel extends AbstractModel {
  @Getter @Setter
  private boolean deleted;

  public abstract AbstractPage getPage();
  public abstract void setPage(AbstractPage page);

  public abstract AbstractPageModel continuationModel();

}
