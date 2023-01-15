package best.tigers.tynkdialog.gui.factories;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.FlatPage;
import best.tigers.tynkdialog.gui.controller.page.FlatPageController;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.FlatPageEditorView;

public class FlatPageMvcFactory extends AbstractPageMvcFactory {

  @Override
  public FlatPage createPage() {
    return new FlatPage("");
  }

  @Override
  public FlatPageModel createPageModel(AbstractPage page) {
    return new FlatPageModel((FlatPage) page);
  }

  @Override
  public FlatPageEditorView createPageView(AbstractPageModel pageModel) {
    return new FlatPageEditorView((FlatPageModel) pageModel);
  }

  @Override
  public FlatPageController createPageController(AbstractPageModel pageModel,
      AbstractPageEditorView pageView) {
    return new FlatPageController((FlatPageModel) pageModel, (FlatPageEditorView) pageView);
  }
}
