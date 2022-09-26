package best.tigers.tatffdialogutility.gui.text;

import java.awt.Color;
import java.util.HashMap;

final class HarlowTMLAttributeMappings {
  private static final HashMap<String, Color> colorHashMap = new HashMap<>();

  static {
    colorHashMap.put("red", Color.red);
    colorHashMap.put("yellow", Color.decode("FFCC02"));
    colorHashMap.put("green", Color.green);
    colorHashMap.put("blue", Color.blue);
  }

  public static Color tynkColorFromString(String input) {
    Color out = colorHashMap.get(input);
    if (out == null) {
      return Color.white;
    }
    return out;
  }
}
