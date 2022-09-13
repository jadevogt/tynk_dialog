package best.tigers.tynk_dialog.game.harlowtml;

public class HarlowTMLToken {
  private String name;

  public HarlowTMLToken(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "<HarlowTML Token | name: " + name + '>';
  }
}
