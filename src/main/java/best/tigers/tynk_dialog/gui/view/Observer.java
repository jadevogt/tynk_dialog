package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.Model;

public interface Observer {
    public void update();
    public default void subscribe(Model model) {
        model.attachSubscriber(this);
    }
    public default void unsubscribe(Model model) {
        model.detachSubscriber(this);
    }
}
