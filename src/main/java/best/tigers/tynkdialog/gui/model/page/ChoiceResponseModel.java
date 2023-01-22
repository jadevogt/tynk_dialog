package best.tigers.tynkdialog.gui.model.page;

import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.ChoiceResponse.ResponseIcon;
import best.tigers.tynkdialog.gui.model.AbstractModel;

public class ChoiceResponseModel extends AbstractModel {

  private final ChoiceResponse response;

  public ChoiceResponseModel(ChoiceResponse response) {
    this.response = response;
  }

  public String getContent() {
    return response.getContent();
  }

  public void setContent(String newContent) {
    response.setContent(newContent);
    notifySubscribers();
  }

  public ResponseIcon getIcon() {
    return response.getIcon();
  }

  public void setIcon(ResponseIcon newIcon) {
    response.setIcon(newIcon);
    notifySubscribers();
  }

  public String getResult() {
    return response.getChoiceResult();
  }

  public void setChoiceResult(String newChoiceResult) {
    response.setChoiceResult(newChoiceResult);
    notifySubscribers();
  }

  public ChoiceResponse getResponse() {
    return response;
  }

  public ChoiceResponseModel clone() {
    return new ChoiceResponseModel(response.clone());
  }
}
