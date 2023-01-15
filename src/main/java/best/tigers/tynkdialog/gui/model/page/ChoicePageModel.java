package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.Delegate;

public class ChoicePageModel extends AbstractPageModel {
  @Getter @Delegate
  private ChoicePage page;

  @Getter
  private boolean blipEnabled;

  public ChoicePageModel(ChoicePage page) {
    this.page = page;
  }

  public ChoicePageModel() {
    this(new ChoicePage());
  }

  @Override
  public void setPage(AbstractPage page) {
    this.page = (ChoicePage) page;
  }

  public void setBlipEnabled(boolean blipEnabled) {
    this.blipEnabled = blipEnabled;
    if (!blipEnabled) {
      setBlip(null);
    }
  }

  @Override
  public ChoicePageModel continuationModel() {
    var newModel = new ChoicePageModel();
    newModel.setSpeaker(getSpeaker());
    newModel.setContent(getContent());
    newModel.setBlip(getBlip());
    newModel.setBlipEnabled(isBlipEnabled());
    newModel.setGifts(new ArrayList<>(getGifts()));
    newModel.setResponses(new ArrayList<>(getResponses()));
    newModel.setCanSkip(isCanSkip());
    return newModel;
  }
}
