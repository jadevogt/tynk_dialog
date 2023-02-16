package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.TalkPage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

public class TalkPageModel extends AbstractPageModel {
  @Getter @Setter
  private String speaker;
  @Getter @Setter
  private String content;
  @Getter @Setter
  private String textStyle;
  @Getter @Setter
  private String blip;
  @Getter @Setter
  private boolean canSkip;

  @Getter
  private boolean blipEnabled = true;
  @Getter
  private boolean styleEnabled = true;

  public TalkPageModel(TalkPage talkPage) {
    super();
    setPage(talkPage);
  }

  public TalkPageModel() {
    this(new TalkPage());
  }

  @Override
  public AbstractPageModel continuationModel() {
    return new TalkPageModel(getPage());
  }

  @Override
  public void setPage(AbstractPage page) {
    var talkPage = (TalkPage) page;
    this.speaker = talkPage.getSpeaker();
    this.content = talkPage.getContent();
    this.textStyle = talkPage.getTextStyle();
    this.styleEnabled = textStyle != null;
    this.blip = talkPage.getBlip();
    this.blipEnabled = blip != null;
    this.canSkip = talkPage.isCanSkip();
  }

  @Override
  public TalkPage getPage() {
    var newTextStyle = styleEnabled ? textStyle : null;
    var newBlip = blipEnabled ? blip : null;
    return new TalkPage(speaker, content, newTextStyle, newBlip, canSkip);
  }
}
