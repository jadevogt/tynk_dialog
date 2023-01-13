package best.tigers.tynkdialog;

import best.tigers.tynkdialog.util.Assets;
import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.EventQueue;
import javax.swing.JEditorPane;

public class Main {

  public static void main(String... args) {
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType(
        "supertext/supertext", "best.tigers.tynkdialog.supertext.SuperTextEditorKit");
    FlatMaterialDesignDarkIJTheme.setup();
    EventQueue.invokeLater(
        PrimaryListController::launch);
  }
}
