package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.game.DialogPage;
import javax.json.JsonObject;

public class DialogPageModel extends AbstractModel {

  private final DialogPage dialogPage;
  private boolean blipEnabled = true;
  private boolean styleEnabled = true;

  public DialogPageModel(DialogPage dialogPage) {
    super();
    this.dialogPage = dialogPage;
    if (dialogPage.getBlip() == null) {
      blipEnabled = false;
    }
    if (dialogPage.getTextBoxStyle() == null) {
      styleEnabled = false;
    }
  }

  public DialogPageModel() {
    this(new DialogPage());
  }

  public String getSpeaker() {
    return dialogPage.getSpeaker();
  }

  public void setSpeaker(String newCharacter) {
    dialogPage.setSpeaker(newCharacter);
    notifySubscribers();
  }

  public String getContent() {
    return dialogPage.getContent();
  }

  public void setContent(String newContent) {
    dialogPage.setContent(newContent);
    notifySubscribers();
  }

  public String getBlip() {
    return dialogPage.getBlip();
  }

  public void setBlip(String newBlip) {
    dialogPage.setBlip(newBlip);
    notifySubscribers();
  }

  public boolean getBlipEnabled() {
    return blipEnabled;
  }

  public void setBlipEnabled(boolean newState) {
    blipEnabled = newState;
    dialogPage.setBlip(null);
  }

  public boolean getStyleEnabled() {
    return styleEnabled;
  }

  public void setStyleEnabled(boolean newState) {
    styleEnabled = newState;
    dialogPage.setTextBoxStyle(null);
  }

  public String getTextBoxStyle() {
    return dialogPage.getTextBoxStyle();
  }

  public void setTextBoxStyle(String newStyle) {
    dialogPage.setTextBoxStyle(newStyle);
    notifySubscribers();
  }

  public JsonObject asJson() {
    return dialogPage.serialize();
  }

  public DialogPage getDialogPage() {
    return dialogPage;
  }
}
