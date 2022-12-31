package best.tigers.tynkdialog.game;

public class Character {

  private static final String DEFAULT_NAME = "???";
  private String name;

  public Character(String name) {
    this.name = name;
  }

  public Character() {
    this(DEFAULT_NAME);
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }

  @Override
  public String toString() {
    return name;
  }
}
