package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.Page;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.AbstractModel;
import javax.json.JsonObject;

public class TalkPageModel extends AbstractPageModel {

  private final TalkPage page;
  private boolean blipEnabled = true;
  private boolean styleEnabled = true;

  public TalkPageModel(TalkPage talkPage) {
    super();
    this.page = talkPage;
    if (talkPage.getBlip() == null) {
      blipEnabled = false;
    }
    if (talkPage.getTextBoxStyle() == null) {
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

  public boolean getBlipEnabled() {
    return blipEnabled;
  }

  public void setBlipEnabled(boolean newState) {
    blipEnabled = newState;
    page.setBlip(null);
  }

  public boolean getStyleEnabled() {
    return styleEnabled;
  }

  public void setStyleEnabled(boolean newState) {
    styleEnabled = newState;
    page.setTextBoxStyle(null);
  }

  public String getTextBoxStyle() {
    return page.getTextBoxStyle();
  }

  public void setTextBoxStyle(String newStyle) {
    page.setTextBoxStyle(newStyle);
    notifySubscribers();
  }

  public JsonObject asJson() {
    return page.asPage();
  }

  public TalkPage getDialogPage() {
    return page;
  }

  public Page getPage() {
    return page;
  }

  @Override
  public AbstractPageModel clone() {
    var newModel = new TalkPageModel();
    newModel.setSpeaker(getSpeaker());
    newModel.setBlip(getBlip());
    newModel.setBlipEnabled(getBlipEnabled());
    newModel.setStyleEnabled(getStyleEnabled());
    newModel.setTextBoxStyle(getTextBoxStyle());
    return newModel;
  }

  @Override
  public String getTitleContent() {
    return getSpeaker();
  }
}
