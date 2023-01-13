package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.page.PageView;

public interface PageController {
  PageControllerFactory getFactory();

  PageView getView();

  AbstractPageModel getModel();

  default void setupViewShortcuts() {
  }

  ;

  void saveChanges();

  void saveAndExit();

}
