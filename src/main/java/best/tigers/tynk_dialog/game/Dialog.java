package best.tigers.tynk_dialog.game;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Represents what the internal documentation refers to as a "Dialog File," essentially a titled
 * collection of DialogPage objects.
 *
 * @see DialogPage
 */
public class Dialog {
  private static int untitledDialogCount = 0;
  private static String defaultTitle = "Untitled";
  private String title;
  private ArrayList<DialogPage> contents;

  public Dialog(String title, ArrayList<DialogPage> contents) {
    this.title = title;
    this.contents = contents;
  }

  /**
   * Creates a new Dialog with the specified title
   *
   * @param title a descriptive title for the Dialog
   */
  public Dialog(String title) {
    this.title = title;
    contents = new ArrayList<DialogPage>();
  }

  /**
   * Creates a new Dialog with the name "Untitled," with a number at the end in the event of
   * multiple untitled dialogs being created in the same session.
   */
  public Dialog() {
    String suffix =
        Dialog.untitledDialogCount > 0 ? " " + Integer.toString(Dialog.untitledDialogCount) : "";
    title = defaultTitle + suffix;
    contents = new ArrayList<DialogPage>();
    Dialog.untitledDialogCount += 1;
  }

  /**
   * Appends a page to the end of the collection of DialogPages
   *
   * @param newPage a DialogPage object to be added
   */
  public void addPage(DialogPage newPage) {
    contents.add(newPage);
  }

  /**
   * Serializes all contained DialogPages and itself
   *
   * @return the JSON representation of the Dialog
   */
  public JsonObject serialize() {
    var pageArray = Json.createArrayBuilder();
    for (DialogPage currentPage : contents) {
      pageArray.add(currentPage.serialize());
    }
    var result =
        Json.createObjectBuilder()
            .add("title", this.title)
            .add("contents", pageArray.build())
            .build();
    return result;
  }

  public String toString() {
    return serialize().toString();
  }
}
