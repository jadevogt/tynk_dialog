package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.gui.view.Observer;

import java.util.ArrayList;

public abstract class AbstractModel {
  private final ArrayList<Observer> observers;

  public AbstractModel() {
    observers = new ArrayList<>();
  }

  public void attachSubscriber(Observer observer) {
    observers.add(observer);
  }

  public void detachSubscriber(Observer observer) {
    observers.remove(observer);
  }

  public void notifySubscribers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
