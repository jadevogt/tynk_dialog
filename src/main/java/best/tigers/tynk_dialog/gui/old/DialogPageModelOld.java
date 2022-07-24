package best.tigers.tynk_dialog.gui.old;

import best.tigers.tynk_dialog.game.DialogPage;

public class DialogPageModelOld implements Observable {
  private final DialogPage dialogPage;

  public DialogPageModelOld(DialogPage dialogPage) {
    super();
    this.dialogPage = dialogPage;
  }

  public String getSpeaker() {
    return dialogPage.getSpeaker().toString();
  }

  public void setSpeaker(String newSpeaker) {
    dialogPage.setSpeaker(newSpeaker);
    notifyListeners();
  }

  public String getContent() {
    return dialogPage.getContent().toString();
  }

  public void setContent(String newContent) {
    dialogPage.setContent(newContent);
    notifyListeners();
  }

  public String getBlip() {
    return dialogPage.getBlip().toString();
  }

  public void setBlip(String newBlip) {
    dialogPage.setBlip(newBlip);
    notifyListeners();
  }

  public String getBoxStyle() {
    return dialogPage.getTextBoxStyle().toString();
  }

  public void setBoxStyle(String newStyle) {
    dialogPage.setTextBoxStyle(newStyle);
    notifyListeners();
  }

  @Override
  public void attachListener(Observer observer) {

  }

  @Override
  public void removeListener(Observer observer) {

  }

  @Override
  public void notifyListeners() {

  }
}
