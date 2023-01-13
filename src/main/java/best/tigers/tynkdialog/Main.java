package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;

import javax.swing.*;
import java.awt.*;

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
