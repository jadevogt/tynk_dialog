package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;

public class TalkPageControllerFactory implements PageControllerFactory {
  public TalkPageController fromModel(AbstractPageModel rawModel) {
    var model = (TalkPageModel) rawModel;
    var controller = new TalkPageController(model);
    controller.setupViewShortcuts();
    return controller;
  }

  public TalkPageController fromModelProceeding(AbstractPageModel rawModel) {
    var model = (TalkPageModel) rawModel;
    var controller = new TalkPageController(model, true);
    controller.setupViewShortcuts();
    return controller;
  }

  public TalkPageController fromNewModel() {
    return TalkPageController.fromModel(new TalkPageModel());
  }

  public void editModel(AbstractPageModel rawModel) {
    var model = (TalkPageModel) rawModel;
    fromModel(model);
  }

  public TalkPageModel createModel() {
    var model = new TalkPageModel();
    editModel(model);
    return model;
  }

}
