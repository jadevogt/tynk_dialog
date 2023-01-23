package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.EventQueue;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String... args) {
    Assets.getInstance().refreshAssets();
      FlatMaterialDesignDarkIJTheme.setup();
    var defaults = Assets.getInstance();
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType(
        "supertext/supertext", "best.tigers.tynkdialog.supertext.SuperTextEditorKit");
    EventQueue.invokeLater(
        PrimaryListController::getInstance);
  }
}
