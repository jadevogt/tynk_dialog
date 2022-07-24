package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.gui.Integration;
import best.tigers.tynk_dialog.gui.controller.DialogListViewController;
import best.tigers.tynk_dialog.util.DialogFile;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
  public static void main(String... args) throws FileNotFoundException {
      System.setProperty("apple.awt.application.name", Integration.APPLICATION_NAME);
    //System.setProperty("apple.laf.useScreenMenuBar", "true");
    try {
        /*
      UIManager.put("nimbusBase", Color.decode("#AA0077"));
      UIManager.put("nimbusBlueGrey", Color.decode("#CCCCCC"));
      UIManager.put("control", Color.decode("#FFCCFF"));
      UIManager.put("nimbusSelectionBackground", Color.decode("#BBBBBB"));
      */
      var lf = new NimbusLookAndFeel();
      var desktopHooks = new Integration();
      UIManager.setLookAndFeel(lf);
    } catch (UnsupportedLookAndFeelException e) {
      System.exit(1);
    }
    EventQueue.invokeLater(
        () -> {
          JFrame.setDefaultLookAndFeelDecorated(true);
          var chooser = new JFileChooser();
          //chooser.setDialogType(JFileChooser.FILES_ONLY);
          //chooser.setApproveButtonText("Open File");
          //chooser.setDialogTitle("Open a Dialog File");
          chooser.setDialogType(JFileChooser.SAVE_DIALOG);
          var jFilter = new JSONFilter();
          var tFilter = new TextFilter();
            chooser.addChoosableFileFilter(jFilter);
            chooser.addChoosableFileFilter(tFilter);
            chooser.showDialog(null, "Select or Create");
          var path = chooser.getSelectedFile().getAbsolutePath();
          var modeledFile = new DialogFile(path);
          var theDialogs = new ArrayList<Dialog>();
          try {
            theDialogs = modeledFile.readFile();
          } catch (Exception e) {
            System.out.println("aaa");
          }
          var x = new DialogListViewController(theDialogs.get(0));
        });
  }
}

class JSONFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.getName().toLowerCase().endsWith("json") || f.isDirectory()) {
            return true;
        }
        return false;
    }
    @Override
    public String getDescription() {
        return "JSON (*.json, *.JSON)";
    }
}

class TextFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.getName().toLowerCase().endsWith("txt") || f.isDirectory()) {
            return true;
        }
        return false;
    }
    @Override
    public String getDescription() {
        return "Plain Text (*.txt, *.TXT)";
    }
}