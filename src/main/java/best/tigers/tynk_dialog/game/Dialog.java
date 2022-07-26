package best.tigers.tynk_dialog.game;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;

/**
 * Represents what the internal documentation refers to as a "Dialog File," essentially a titled
 * collection of DialogPage objects.
 *
 * @see DialogPage
 */
public class Dialog {
  private static final String defaultTitle = "Untitled";
  private static int untitledDialogCount = 0;
  private String title;
  private final ArrayList<DialogPage> contents;

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
            Dialog.untitledDialogCount > 0 ? " " + Dialog.untitledDialogCount : "";
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
    javax.json.JsonArrayBuilder pageArray = Json.createArrayBuilder();
    for (DialogPage currentPage : contents) {
      pageArray.add(currentPage.serialize());
    }
    return Json.createObjectBuilder()
            .add("title", this.title)
            .add("contents", pageArray.build())
            .build();
  }

  public ArrayList<DialogPage> getPages() {
    return contents;
  }

  public String toString() {
    return serialize().toString();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
  }
}
