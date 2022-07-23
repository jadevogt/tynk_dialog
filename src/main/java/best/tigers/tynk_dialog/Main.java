package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.game.DialogPage;
import best.tigers.tynk_dialog.gui.DialogPageModel;
import best.tigers.tynk_dialog.gui.DialogPageView;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String... args) throws FileNotFoundException {
    try {
      var lf = new NimbusLookAndFeel();
      UIManager.setLookAndFeel(lf);
    } catch (UnsupportedLookAndFeelException e) {
      System.exit(1);
    }
    EventQueue.invokeLater(
        () -> {
          var frames = new DialogPageView[10];
          for (var view : frames) {
            view = new DialogPageView();
            view.setVisible(true);
          }
        });
  }
}
