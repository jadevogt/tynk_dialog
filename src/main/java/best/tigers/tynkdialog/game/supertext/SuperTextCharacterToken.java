package best.tigers.tynkdialog.game.supertext;

public class SuperTextCharacterToken extends SuperTextToken {

  private final char tokenContent;

  public SuperTextCharacterToken(char tokenContent) {
    super("CharacterToken");
    this.tokenContent = tokenContent;
  }

  @Override
  public String toString() {
    return "<SuperText Token | name: CharacterToken | content: " + this.tokenContent + ">";
  }

  public String getContent() {
    return String.valueOf(this.tokenContent);
  }
}
