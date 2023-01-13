package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.Page;
import best.tigers.tynkdialog.gui.model.AbstractModel;

public abstract class AbstractPageModel extends AbstractModel {
  public abstract Page getPage();
  public abstract AbstractPageModel clone();

  public abstract String getTitleContent();
}
