package best.tigers.tynk_dialog.gui.old;

import best.tigers.tynk_dialog.game.DialogPage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogPageController {
  final private DialogPageModel model;
  final private DialogPageView view;

  public DialogPageController(DialogPage displayedPage) {
    model = new DialogPageModel();
    view = new DialogPageView();

    view.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        view.getPanel().detach();
        super.windowClosing(e);
      }
    });
  }

  public DialogPageController() {
    this(new DialogPage("test", "test", "test", "test", true));
  }

  public DialogPageModel getModel() {
    return model;
  }
}
