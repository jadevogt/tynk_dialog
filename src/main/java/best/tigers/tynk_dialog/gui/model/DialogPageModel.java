package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.game.DialogPage;

public class DialogPageModel extends Model {
    private DialogPage dialogPage;

    public DialogPageModel(DialogPage dialogPage) {
        super();
        this.dialogPage = dialogPage;
    }

    public DialogPageModel() {
        this(new DialogPage());
    }

    public String getSpeaker() {
        return dialogPage.getSpeaker();
    }

    public void setSpeaker(String newCharacter) {
        dialogPage.setSpeaker(newCharacter);
        notifySubscribers();
    }

    public String getContent() {
        return dialogPage.getContent();
    }

    public void setContent(String newContent) {
        dialogPage.setContent(newContent);
        notifySubscribers();
    }

    public String getBlip() {
        return dialogPage.getBlip();
    }

    public void setBlip(String newBlip) {
        dialogPage.setBlip(newBlip);
        notifySubscribers();
    }
    public String getTextBoxStyle() {
        return dialogPage.getTextBoxStyle();
    }

    public void setTextBoxStyle(String newStyle) {
        dialogPage.setTextBoxStyle(newStyle);
        notifySubscribers();
    }
}
