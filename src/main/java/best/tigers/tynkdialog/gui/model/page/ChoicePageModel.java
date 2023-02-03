package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import lombok.Getter;

public class ChoicePageModel extends AbstractPageModel {
  @Getter
  private String speaker;
  @Getter
  private String content;
  @Getter
  private String blip;
  @Getter
  private boolean canSkip;
  @Getter
  private GenericListModel<ChoiceResponseModel> responseListModel;
  @Getter
  private GenericListModel<String> giftListModel;

  public ChoicePageModel(ChoicePage page) {
    super();
    setPage(page);
  }

  @Override
  public void setPage(AbstractPage page) {
    var choicePage = (ChoicePage) page;
    this.speaker = choicePage.getSpeaker();
    this.content = choicePage.getContent();
    this.blip = choicePage.getBlip();
    this.canSkip = choicePage.isCanSkip();
    this.giftListModel = new GenericListModel<>(choicePage.getGifts());
    this.responseListModel = new GenericListModel<>(
        choicePage.getResponses()
            .stream()
            .map(ChoiceResponseModel::new)
            .toList()
    );
    notifySubscribers();
  }

  @Override
  public ChoicePage getPage() {
    var responseList = responseListModel.getContent()
        .stream()
        .map(ChoiceResponseModel::getChoiceResponse)
        .toList();
    var giftList = giftListModel.getContent();
    return new ChoicePage(speaker, content, blip, canSkip, giftList, responseList);
  }

  @Override
  public ChoicePageModel continuationModel() {
    return new ChoicePageModel(getPage());
  }

  public GenericListModel<ChoiceResponseModel> cloneChoiceResponses() {
    return new GenericListModel<>(responseListModel.getContent());
  }

  public GenericListModel<String> cloneGifts() {
    return new GenericListModel<>(giftListModel.getContent());
  }

  public void setSpeaker(String speaker) {
    this.speaker = speaker;
    notifySubscribers();
  }

  public void setContent(String content) {
    this.content = content;
    notifySubscribers();
  }

  public void setBlip(String blip) {
    this.blip = blip;
    notifySubscribers();
  }

  public void setCanSkip(boolean canSkip) {
    this.canSkip = canSkip;
    notifySubscribers();
  }

  public void setResponseListModel(
      GenericListModel<ChoiceResponseModel> responseListModel) {
    this.responseListModel = responseListModel;
    notifySubscribers();
  }

  public void setGiftListModel(
      GenericListModel<String> giftListModel) {
    this.giftListModel = giftListModel;
    notifySubscribers();
  }
}
