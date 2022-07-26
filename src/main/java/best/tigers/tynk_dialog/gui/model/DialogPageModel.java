package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.game.DialogPage;
import best.tigers.tynk_dialog.util.ParseUtils;

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

  public void setBlipEnabled(boolean newState) {
    blipEnabled = newState;
    dialogPage.setBlip(null);
  }
  public boolean getBlipEnabled() {
    return blipEnabled;
  }

  public void setStyleEnabled(boolean newState) {
    styleEnabled = newState;
    dialogPage.setTextBoxStyle(null);
  }
  public boolean getStyleEnabled() {
    return styleEnabled;
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
