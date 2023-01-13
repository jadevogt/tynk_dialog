package best.tigers.tynkdialog.game;

import java.awt.*;
import java.util.Arrays;

public final class Constants {

  public static final int TEXT_BOX_LENGTH = 45;
  public static final int TEXT_BOX_HEIGHT = 4;

  private Constants() {
  }

  public enum Behavior {
    WAVE("Wave", "wave", TextColor.BLUE),
    QUAKE("Quake", "quake", TextColor.RED);
    private final String behaviorName;
    private final String behaviorValue;
    private final TextColor behaviorColor;

    Behavior(String behaviorName, String behaviorValue, TextColor behaviorColor) {
      this.behaviorName = behaviorName;
      this.behaviorValue = behaviorValue;
      this.behaviorColor = behaviorColor;
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

    public TextColor getBehaviorColor() {
      return this.behaviorColor;
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
