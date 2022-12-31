package best.tigers.tynkdialog.gui.view;

import best.tigers.tynkdialog.gui.model.AbstractModel;

public interface TObserver {

  void update();

  default void subscribe(AbstractModel model) {
    model.attachSubscriber(this);
  }

  default void unsubscribe(AbstractModel model) {
    model.detachSubscriber(this);
  }
}
