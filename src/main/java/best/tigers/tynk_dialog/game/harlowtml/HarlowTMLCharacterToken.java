package best.tigers.tynk_dialog.game.harlowtml;

public class HarlowTMLCharacterToken extends HarlowTMLToken {
  private final char tokenContent;

  public HarlowTMLCharacterToken(char tokenContent) {
    super("CharacterToken");
    this.tokenContent = tokenContent;
  }

  @Override
  public String toString() {
    return "<HarlowTML Token | name: CharacterToken | content: " + tokenContent + '>';
  }

  public String getContent() {
    return String.valueOf(tokenContent);
  }
}
