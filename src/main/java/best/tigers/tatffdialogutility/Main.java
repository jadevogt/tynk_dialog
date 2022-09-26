package best.tigers.tatffdialogutility;

import best.tigers.tatffdialogutility.gui.Assets;
import best.tigers.tatffdialogutility.gui.controller.PrimaryListController;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.EventQueue;
import javax.swing.JEditorPane;

public final class Main {
  public static void main(String... args) {
    Assets.runIntegrations();
    JEditorPane.registerEditorKitForContentType(
        "text/harlowtml", "best.tigers.tatffdialogutility.gui.text.HarlowTMLEditorKit");
    FlatMaterialDesignDarkIJTheme.setup();
    EventQueue.invokeLater(PrimaryListController::launch);
  }
}
