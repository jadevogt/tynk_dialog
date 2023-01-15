package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.TalkPage;
import lombok.Getter;

public class TalkPageModel extends AbstractPageModel {

  @Getter
  private final TalkPage page;
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

  public String getSpeaker() {
    return page.getSpeaker();
  }

  public void setSpeaker(String newCharacter) {
    page.setSpeaker(newCharacter);
    notifySubscribers();
  }

  public String getContent() {
    return page.getContent();
  }

  public void setContent(String newContent) {
    page.setContent(newContent);
    notifySubscribers();
  }

  public String getBlip() {
    return page.getBlip();
  }

  public void setBlip(String newBlip) {
    page.setBlip(newBlip);
    notifySubscribers();
  }

  public void setBlipEnabled(boolean newState) {
    blipEnabled = newState;
    page.setBlip(null);
    notifySubscribers();
  }

  public void setStyleEnabled(boolean newState) {
    styleEnabled = newState;
    page.setTextStyle(null);
    notifySubscribers();
  }

  public String getTextBoxStyle() {
    return page.getTextStyle();
  }

  public void setTextBoxStyle(String newStyle) {
    page.setTextStyle(newStyle);
    notifySubscribers();
  }

  public boolean isCanSkip() {
    return page.isCanSkip();
  }

  public void setCanSkip(boolean canSkip) {
    this.page.setCanSkip(canSkip);
    notifySubscribers();
  }


  @Override
  public AbstractPageModel clone() {
    var newModel = new TalkPageModel();
    newModel.setSpeaker(getSpeaker());
    newModel.setBlip(getBlip());
    newModel.setBlipEnabled(isBlipEnabled());
    newModel.setStyleEnabled(isStyleEnabled());
    newModel.setTextBoxStyle(getTextBoxStyle());
    return newModel;
  }

}
