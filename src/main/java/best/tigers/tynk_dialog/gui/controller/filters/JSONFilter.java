package best.tigers.tynk_dialog.gui.controller.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class JSONFilter extends FileFilter {
  @Override
  public boolean accept(File f) {
    return f.getName().toLowerCase().endsWith("json") || f.isDirectory();
  }

  @Override
  public String getDescription() {
    return "JSON (*.json, *.JSON)";
  }
}
