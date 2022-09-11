package best.tigers.tynk_dialog.game.harlowtml;

public class HarlowTMLCharacterToken extends HarlowTMLToken {
    private char tokenContent;

    public HarlowTMLCharacterToken(char tokenContent) {
        super("CharacterToken");
        this.tokenContent = tokenContent;
    }

    public String toString() {
        return "<HarlowTML Token | name: CharacterToken | content: " + this.tokenContent + ">";
    }

    public String getContent() {
        return String.valueOf(this.tokenContent);
    }
}
