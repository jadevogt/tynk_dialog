package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.gui.view.TObserver;
import java.util.ArrayList;

public abstract class AbstractModel {
  private final ArrayList<TObserver> TObservers;

  public AbstractModel() {
    TObservers = new ArrayList<>();
  }

  public void attachSubscriber(TObserver TObserver) {
    TObservers.add(TObserver);
  }

  public void detachSubscriber(TObserver TObserver) {
    TObservers.remove(TObserver);
  }

  public void notifySubscribers() {
    for (TObserver TObserver : TObservers) {
      TObserver.update();
    }
  }
}
