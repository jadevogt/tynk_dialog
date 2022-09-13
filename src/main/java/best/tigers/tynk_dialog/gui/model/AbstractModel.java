package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.gui.view.TObserver;
import java.util.ArrayList;

public abstract class AbstractModel {
  private final ArrayList<TObserver> tObservers;

  protected AbstractModel() {
    tObservers = new ArrayList<>();
  }

  public void attachSubscriber(TObserver TObserver) {
    tObservers.add(TObserver);
  }

  public void detachSubscriber(TObserver TObserver) {
    tObservers.remove(TObserver);
  }

  public void notifySubscribers() {
    for (TObserver TObserver : tObservers) {
      TObserver.update();
    }
  }
}
