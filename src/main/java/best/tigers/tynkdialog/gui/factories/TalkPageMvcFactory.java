package best.tigers.tynkdialog.gui.factories;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.controller.page.TalkPageController;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.neo.NeoTalkPageEditorView;

public class TalkPageMvcFactory extends AbstractPageMvcFactory {

  @Override
  public TalkPage createPage() {
    return new TalkPage();
  }

  @Override
  public TalkPageModel createPageModel(AbstractPage page) {
    return new TalkPageModel((TalkPage) page);
  }

  @Override
  public NeoTalkPageEditorView createPageView(AbstractPageModel pageModel) {
    return new NeoTalkPageEditorView((TalkPageModel) pageModel);
  }

  @Override
  public TalkPageController createPageController(AbstractPageModel pageModel,
      AbstractPageEditorView pageView) {
    return new TalkPageController((TalkPageModel) pageModel, (NeoTalkPageEditorView) pageView);
  }
}
