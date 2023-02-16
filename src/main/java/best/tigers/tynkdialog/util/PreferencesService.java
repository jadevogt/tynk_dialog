package best.tigers.tynkdialog.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public class PreferencesService {
  private static PreferencesService instance = null;
  private Preferences prefs;

  private PreferencesService() {
    prefs = Preferences.userRoot().node("tynkDialogTool");
  }

  public static PreferencesService getInstance() {
    if (instance == null) {
      instance = new PreferencesService();
    }
    return instance;
  }

  public Path getLastOpenedPath() {
    Path path;
    try {
      path = Paths.get(prefs.get("lastOpenedPath", null));
    } catch (NullPointerException e) {
      return Paths.get(System.getProperty("user.home"));
    }
    if (Files.isDirectory(path)) {
      return path;
    }
    return Paths.get(System.getProperty("user.home"));
  }

  public void setLastOpenedPath(Path lastOpenedPath) {
    prefs.put("lastOpenedPath", lastOpenedPath.toString());
  }
}
