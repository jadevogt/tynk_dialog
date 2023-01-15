package best.tigers.tynkdialog.gui.factories;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.gui.controller.page.AbstractPageController;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;

public abstract class AbstractPageMvcFactory {

  public abstract AbstractPage createPage();

  public abstract AbstractPageModel createPageModel(AbstractPage page);

  public abstract AbstractPageEditorView createPageView(AbstractPageModel pageModel);

  public abstract AbstractPageController createPageController(AbstractPageModel pageModel,
      AbstractPageEditorView pageView);

  public AbstractPageModel createNewPageModel() {
    var page = createPage();
    return createPageModel(page);
  }
}
