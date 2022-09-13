package best.tigers.tynk_dialog.game;

import java.awt.Color;
import java.util.Arrays;

public final class Constants {
  public final int textBoxLength = 45;
  public final int textBoxHeight = 4;

  private Constants() {}

  public enum Behavior {
    WAVE("Wave", "wave"),
    QUAKE("Quake", "quake");
    private final String behaviorName;
    private final String behaviorValue;

    Behavior(String behaviorName, String behaviorValue) {
      this.behaviorName = behaviorName;
      this.behaviorValue = behaviorValue;
    }

    public static Behavior fromString(String input) {
      return Arrays.stream(Behavior.values())
          .filter(v -> v.getBehaviorValue().equals(input.toLowerCase()))
          .findFirst()
          .orElse(null);
    }

    public String getBehaviorValue() {
      return this.behaviorValue;
    }

    public String getBehaviorName() {
      return this.behaviorName;
    }

    public String asTag() {
      return "<b=" + this.behaviorValue + ">";
    }
  }

  public enum TextColor {
    WHITE("white", "#F2E9DC"),
    GREEN("green", "#4eb569"),
    GREY("grey", "#888888"),
    RED("red", "#FF5A5F"),
    BLUE("blue", "#6DD3CE"),
    YELLOW("yellow", "#FFCC00"),
    BACKGROUND("background", "#3D2D3A");

    private final String gameName;
    private final String hexCode;

    TextColor(String gameName, String hexCode) {
      this.gameName = gameName;
      this.hexCode = hexCode;
    }

    public static TextColor fromAWT(Color color) {
      return Arrays.stream(TextColor.values())
          .filter(v -> v.toAWT().equals(color))
          .findFirst()
          .orElse(WHITE);
    }

    public static TextColor fromString(String input) {
      return Arrays.stream(TextColor.values())
          .filter(v -> v.getGameName().equals(input.toLowerCase()))
          .findFirst()
          .orElse(WHITE);
    }

    public Color toAWT() {
      return Color.decode(hexCode);
    }

    public String getGameName() {
      return gameName;
    }

    public String asTag() {
      return "<c=" + getGameName() + ">";
    }
  }
}
