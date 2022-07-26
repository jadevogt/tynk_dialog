package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.AbstractModel;

public interface Observer {
  void update();

  default void subscribe(AbstractModel model) {
    model.attachSubscriber(this);
  }

  default void unsubscribe(AbstractModel model) {
    model.detachSubscriber(this);
  }
}
