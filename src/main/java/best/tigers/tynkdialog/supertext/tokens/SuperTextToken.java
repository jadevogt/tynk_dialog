package best.tigers.tynkdialog.supertext.tokens;

import lombok.Getter;
import lombok.Setter;

public class SuperTextToken {

  @Getter
  @Setter
  private String name;

  public SuperTextToken(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "<SuperText Token | name: " + this.name + ">";
  }
}
