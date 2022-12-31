package best.tigers.tynkdialog.game.supertext;

public class SuperTextToken {

  private String name;

  public SuperTextToken(String name) {
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
    return "<SuperText Token | name: " + this.name + ">";
  }
}
