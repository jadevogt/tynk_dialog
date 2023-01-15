package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.gui.view.TObserver;
import java.util.ArrayList;

public abstract class AbstractModel {

  private final ArrayList<TObserver> tObservers;

  public AbstractModel() {
    tObservers = new ArrayList<>();
  }

  public void attachSubscriber(TObserver tObserver) {
    tObservers.add(tObserver);
  }

  public void detachSubscriber(TObserver tObserver) {
    tObservers.remove(tObserver);
  }

  public void notifySubscribers() {
    var observersClone = new ArrayList<>(tObservers);
    observersClone.forEach(TObserver::update);
  }
}
