package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import com.jthemedetecor.OsThemeDetector;
import java.awt.EventQueue;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String... args) {
    OsThemeDetector themeDetector = OsThemeDetector.getDetector();
    if (themeDetector.isDark()) {
      FlatMaterialDesignDarkIJTheme.setup();
    } else {
      FlatLightFlatIJTheme.setup();
    }
    themeDetector.registerListener(isDark -> {
      SwingUtilities.invokeLater(() -> {
        if (isDark) {
          // The OS switched to a dark theme
          FlatMaterialDesignDarkIJTheme.setup();
          PrimaryListController.getInstance().updateUI();
        } else {
          // The OS switched to a light theme
          FlatLightFlatIJTheme.setup();
          PrimaryListController.getInstance().updateUI();
        }
      });
    });
    var defaults = Assets.getInstance();
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType(
        "supertext/supertext", "best.tigers.tynkdialog.supertext.SuperTextEditorKit");
    EventQueue.invokeLater(
        PrimaryListController::getInstance);
  }
}
