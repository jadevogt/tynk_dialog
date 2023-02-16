package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.ChoiceResponse.ResponseIcon;
import best.tigers.tynkdialog.gui.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

public class ChoiceResponseModel extends AbstractModel {
  @Getter
  private String content;
  @Getter
  private String choiceResult;
  @Getter
  private ResponseIcon icon;

  public ChoiceResponseModel(ChoiceResponse choiceResponse) {
    super();
    setChoiceResponse(choiceResponse);
  }

  public void setChoiceResponse(ChoiceResponse choiceResponse) {
    this.content = choiceResponse.getContent();
    this.choiceResult = choiceResponse.getChoiceResult();
    this.icon = choiceResponse.getIcon();
    notifySubscribers();
  }

  public ChoiceResponse getChoiceResponse() {
    return new ChoiceResponse(content, choiceResult, icon);
  }

  public void setContent(String content) {
    this.content = content;
    notifySubscribers();
  }

  public void setChoiceResult(String choiceResult) {
    this.choiceResult = choiceResult;
    notifySubscribers();
  }

  public void setIcon(ResponseIcon icon) {
    this.icon = icon;
    notifySubscribers();
  }
}
