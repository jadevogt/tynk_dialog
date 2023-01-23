package best.tigers.tynkdialog.gui.factories;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.gui.controller.page.AbstractPageController;
import best.tigers.tynkdialog.gui.controller.page.BranchPageController;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.BranchPageEditorView;
import best.tigers.tynkdialog.util.page.BranchPageBuilder;

public class BranchPageMvcFactory extends AbstractPageMvcFactory {

  @Override
  public AbstractPage createPage() {
    return null;
  }

  @Override
  public BranchPageModel createPageModel(AbstractPage page) {
    return new BranchPageModel((BranchPage) page);
  }

  public BranchPageModel createNewPageModel() {
    return new BranchPageModel(new BranchPage());
  }

  @Override
  public BranchPageEditorView createPageView(AbstractPageModel pageModel) {
    return new BranchPageEditorView((BranchPageModel) pageModel);
  }

  @Override
  public BranchPageController createPageController(AbstractPageModel pageModel,
      AbstractPageEditorView pageView) {
    return new BranchPageController(pageModel, pageView);
  }
}
