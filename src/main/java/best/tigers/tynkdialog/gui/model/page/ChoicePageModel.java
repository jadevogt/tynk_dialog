package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Delegate;

public class ChoicePageModel extends AbstractPageModel {

  private GenericListModel<ChoiceResponseModel> responseModels;
  private GenericListModel<String> giftModels;
  @Delegate
  private ChoicePage page;
  @Getter
  private boolean blipEnabled;

  public ChoicePageModel(ChoicePage page) {
    this.page = page;
    this.responseModels = new GenericListModel<>();
    this.giftModels = new GenericListModel<>(page.getGifts());
    page.getResponses().forEach(m -> responseModels.addItem(new ChoiceResponseModel(m)));
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
    this.giftModels = new GenericListModel<>(((ChoicePage) page).getGifts());
    ((ChoicePage) page).getResponses().forEach(m -> responseModels.addItem(new ChoiceResponseModel(m)));
  }

  public void setBlipEnabled(boolean blipEnabled) {
    this.blipEnabled = blipEnabled;
    if (!blipEnabled) {
      this.page.setBlip(null);
    }
  }

  public GenericListModel<ChoiceResponseModel> getResponseListModel() {
    return responseModels;
  }

  public GenericListModel<String> getGiftListModel() {
    return giftModels;
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


  public GenericListModel<ChoiceResponseModel> cloneChoiceResponses() {
    return new GenericListModel<ChoiceResponseModel>(
        getResponses().stream().map(r -> new ChoiceResponseModel(r.clone())).toList());
  }

  public void setResponseModels(List<ChoiceResponse> input) {
    responseModels = new GenericListModel<>(
        input.stream().map(r -> new ChoiceResponseModel(r.clone())).toList());
  }

  public GenericListModel<String> cloneGifts() {
    return new GenericListModel<String>(getGifts().stream().map(String::new).toList());
  }

  public void setGiftModels(List<String> input) {
    giftModels = new GenericListModel<String>(input.stream().map(String::new).toList());
  }
}
