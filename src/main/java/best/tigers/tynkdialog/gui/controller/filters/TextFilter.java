package best.tigers.tynkdialog.gui.controller.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TextFilter extends FileFilter {

  @Override
  public boolean accept(File f) {
    return f.getName().toLowerCase().endsWith("txt") || f.isDirectory();
  }

  @Override
  public String getDescription() {
    return "Plain Text (*.txt, *.TXT)";
  }
}
