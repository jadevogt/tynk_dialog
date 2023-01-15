package best.tigers.tynkdialog.gui.factories;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.gui.controller.page.AbstractPageController;
import best.tigers.tynkdialog.gui.controller.page.ChoicePageController;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.ChoicePageEditorView;

public class ChoicePageMvcFactory extends AbstractPageMvcFactory {

  @Override
  public AbstractPage createPage() {
    return new ChoicePage();
  }

  @Override
  public AbstractPageModel createPageModel(AbstractPage page) {
    return new ChoicePageModel((ChoicePage) page);
  }

  @Override
  public AbstractPageEditorView createPageView(AbstractPageModel pageModel) {
    return new ChoicePageEditorView((ChoicePageModel) pageModel);
  }

  @Override
  public AbstractPageController createPageController(AbstractPageModel pageModel,
      AbstractPageEditorView pageView) {
    return new ChoicePageController((ChoicePageModel) pageModel, (ChoicePageEditorView) pageView);
  }
}
