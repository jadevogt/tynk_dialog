package best.tigers.tynkdialog.gui.controller.filters;

import java.io.File;
import javax.swing.filechooser.FileFilter;

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
