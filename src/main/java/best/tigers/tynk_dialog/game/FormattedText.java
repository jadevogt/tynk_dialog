package best.tigers.tynk_dialog.game;

public class FormattedText {
  private static String defaultContent = "-";
  private String content;

  public FormattedText(String content) {
    this.content = content;
  }

  public FormattedText() {
    this(defaultContent);
  }

  @Override
  public String toString() {
    return content;
  }
}
