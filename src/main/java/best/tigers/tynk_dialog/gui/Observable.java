package best.tigers.tynk_dialog.gui;

import java.util.ArrayList;

public abstract class Observable {
  final private ArrayList<Observer> observers;

  public Observable() {
    observers = new ArrayList<Observer>();
  }

  void attachListener(Observer observer) {
    observers.add(observer);
  }

  void removeListener(Observer observer) {
    observers.remove(observer);
  }

  void notifyListeners() {
    for (var observer : observers) {
      observer.update();
    }
  }
}
