package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.Model;

public interface Observer {
  void update();

  default void subscribe(Model model) {
    model.attachSubscriber(this);
  }

  default void unsubscribe(Model model) {
    model.detachSubscriber(this);
  }
}
