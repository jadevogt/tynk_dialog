package best.tigers.tynk_dialog;

class DialogPage {
  private Character speaker;
  private FormattedText content;
  private Blip blip;
  private boolean canSkip;

  public DialogPage(Character speaker, FormattedText content, Blip blip, boolean canSkip) {
    this.content = content;
    this.speaker = speaker;
    this.blip = blip;
    this.canSkip = canSkip;
  }

  public DialogPage() {
  }

}
