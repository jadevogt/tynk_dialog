package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;

public interface PageControllerFactory {
  PageController fromModel(AbstractPageModel model);
  PageController fromModelProceeding(AbstractPageModel model);
  PageController fromNewModel();
  void editModel(AbstractPageModel model);
  AbstractPageModel createModel();
}
