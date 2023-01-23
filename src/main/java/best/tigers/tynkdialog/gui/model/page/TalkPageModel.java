package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.TalkPage;
import lombok.Getter;
import lombok.experimental.Delegate;

public class TalkPageModel extends AbstractPageModel {

  @Getter
  @Delegate
  private TalkPage page;
  @Getter
  private boolean blipEnabled = true;
  @Getter
  private boolean styleEnabled = true;

  public TalkPageModel(TalkPage talkPage) {
    super();
    this.page = talkPage;
    if (talkPage.getBlip() == null) {
      blipEnabled = false;
    }
    if (talkPage.getTextStyle() == null) {
      styleEnabled = false;
    }
  }

  public TalkPageModel() {
    this(new TalkPage());
  }

  public void setBlipEnabled(boolean newState) {
    blipEnabled = newState;
    page.setBlip(null);
  }

  public void setStyleEnabled(boolean newState) {
    styleEnabled = newState;
    page.setTextStyle(null);
  }

  @Override
  public AbstractPageModel continuationModel() {
    var newModel = new TalkPageModel();
    newModel.setSpeaker(getSpeaker());
    newModel.setBlipEnabled(isBlipEnabled());
    newModel.setBlip(getBlip());
    newModel.setStyleEnabled(isStyleEnabled());
    newModel.setTextStyle(getTextStyle());
    return newModel;
  }

  @Override
  public void setPage(AbstractPage page) {
    this.page = (TalkPage) page;
  }

}
