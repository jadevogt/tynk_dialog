package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.gui.Integration;
import best.tigers.tynk_dialog.gui.controller.PrimaryListController;
import best.tigers.tynk_dialog.util.DialogFile;
import best.tigers.tynk_dialog.util.Log;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
  public static void main(String... args) throws FileNotFoundException {
    Integration.runIntegrations();

    try {
      NimbusLookAndFeel lf = new NimbusLookAndFeel();
      UIManager.setLookAndFeel(lf);
    } catch (UnsupportedLookAndFeelException e) {
      Log.error("Nimbus LAF is not supported in this environment, so application appearance may be inconsistent with other platforms");
    }

    EventQueue.invokeLater(
            () -> {
              PrimaryListController x = new PrimaryListController();
            });
  }
}

