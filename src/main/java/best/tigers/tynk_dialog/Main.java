package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.gui.Assets;
import best.tigers.tynk_dialog.gui.controller.PrimaryListController;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.EventQueue;
import javax.swing.JEditorPane;

public final class Main {
  public static void main(String... args) {
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType(
        "text/harlowtml", "best.tigers.tynk_dialog.gui.text.HarlowTMLEditorKit");
    FlatMaterialDesignDarkIJTheme.setup();
    EventQueue.invokeLater(PrimaryListController::launch);
  }
}
