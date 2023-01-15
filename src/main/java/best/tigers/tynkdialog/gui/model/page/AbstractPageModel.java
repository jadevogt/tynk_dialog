package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.gui.model.AbstractModel;

public abstract class AbstractPageModel extends AbstractModel {

  public abstract AbstractPage getPage();

  public abstract AbstractPageModel clone();

}
