package best.tigers.tynk_dialog.game;
import java.nio.file.Path;

public class Character {
  private static String defaultName = "???";
  private String name;
  public Character(String name) {
    this.name = name;
  }

  public Character() {
    this(defaultName);
  }

  @Override
  public String toString() {
    return name;
  }
}