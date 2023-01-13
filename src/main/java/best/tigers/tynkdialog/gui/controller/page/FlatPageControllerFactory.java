package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;

public class FlatPageControllerFactory implements PageControllerFactory {
  @Override
  public PageController fromModel(AbstractPageModel rawModel) {
    var model = (FlatPageModel) rawModel;
    return new FlatPageController(model);
  }

  @Override
  public PageController fromModelProceeding(AbstractPageModel rawModel) {
    return fromModel(rawModel);
  }

  @Override
  public PageController fromNewModel() {
    return null;
  }

  @Override
  public void editModel(AbstractPageModel rawModel) {
    var model = (FlatPageModel) rawModel;
    fromModel(model);
  }

  @Override
  public FlatPageModel createModel() {
    var model = new FlatPageModel();
    editModel(model);
    return model;
  }
}
