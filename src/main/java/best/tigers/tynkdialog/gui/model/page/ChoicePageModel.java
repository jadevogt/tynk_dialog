package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.gui.model.ResponseChoiceListModel;
import java.util.ArrayList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import lombok.Getter;
import lombok.experimental.Delegate;

public class ChoicePageModel extends AbstractPageModel implements ListDataListener {

  @Delegate
  private ChoicePage page;
  private final ResponseChoiceListModel responseModels;

  @Getter
  private boolean blipEnabled;

  public ChoicePageModel(ChoicePage page) {
    this.page = page;
    this.responseModels = new ResponseChoiceListModel();
    this.responseModels.addListDataListener(this);
    page.getResponses().forEach(m -> responseModels.addResponse(new ChoiceResponseModel(m)));
  }

  public ChoicePageModel() {
    this(new ChoicePage());
  }

  @Override
  public AbstractPage getPage() {
    return page;
  }

  @Override
  public void setPage(AbstractPage page) {
    this.page = (ChoicePage) page;
  }

  public void setBlipEnabled(boolean blipEnabled) {
    this.blipEnabled = blipEnabled;
    if (!blipEnabled) {
      this.page.setBlip(null);
    }
  }

  public ResponseChoiceListModel getResponseModels() {
    return responseModels;
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

  public void updateList() {
    page.setResponses(getResponseModels().getContent().stream().map(ChoiceResponseModel::getResponse).toList());
  }

  @Override
  public void intervalAdded(ListDataEvent e) {
    updateList();
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    updateList();
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    updateList();
  }
}
