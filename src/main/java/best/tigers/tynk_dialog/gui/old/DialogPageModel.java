package best.tigers.tynk_dialog.gui.old;
import best.tigers.tynk_dialog.game.DialogPage;

import java.util.ArrayList;

public class DialogPageModel extends DialogPage implements Observable {
  private ArrayList<Observer> observers;
  {
    observers = new ArrayList<>();
  }

  public void setSpeaker(String newSpeaker) {
    super.setSpeaker(newSpeaker);
    notifyListeners();
  }

  public void setContent(String newContent) {
    super.setContent(newContent);
    notifyListeners();
  }

  public void setBlip(String newBlip) {
    super.setBlip(newBlip);
    notifyListeners();
  }

  public void setBoxStyle(String newStyle) {
    super.setTextBoxStyle(newStyle);
    notifyListeners();
  }

  @Override
  public void attachListener(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeListener(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyListeners() {
    for (var observer : observers) {
      observer.update();
    }
  }
}
