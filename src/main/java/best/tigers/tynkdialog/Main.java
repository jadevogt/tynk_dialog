package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
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
    System.out.println(SuperTextEditorKit.class.getCanonicalName());
    JEditorPane.registerEditorKitForContentType("text/supertext",
        SuperTextEditorKit.class.getCanonicalName());
    EventQueue.invokeLater(
        PrimaryListController::getInstance);
  }
}
