package best.tigers.tatffdialogutility.gui.view;

import best.tigers.tatffdialogutility.gui.model.AbstractModel;

public interface TObserver {
  void update();

  default void subscribe(AbstractModel model) {
    model.attachSubscriber(this);
  }

  default void unsubscribe(AbstractModel model) {
    model.detachSubscriber(this);
  }
}
