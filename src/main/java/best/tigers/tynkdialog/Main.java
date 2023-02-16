package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.EventQueue;
import javax.swing.JEditorPane;

public class Main {

  public static void main(String... args) {
    Assets.getInstance().refreshAssets();
    FlatMaterialDesignDarkIJTheme.setup();
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType("text/supertext",
        "best.tigers.tynkdialog.supertext.SuperTextEditorKit"
    );
    EventQueue.invokeLater(
        PrimaryListController::getInstance);
  }
}
