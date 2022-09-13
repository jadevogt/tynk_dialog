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
  private static final String defaultTitle = "Untitled";
  private static int untitledDialogCount;
  private final ArrayList<DialogPage> contents;
  private String title;

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
    contents = new ArrayList<>();
  }

  /**
   * Creates a new Dialog with the name "Untitled," with a number at the end in the event of
   * multiple untitled dialogs being created in the same session.
   */
  public Dialog() {
    String suffix = Dialog.untitledDialogCount > 0 ? " " + Dialog.untitledDialogCount : "";
    title = defaultTitle + suffix;
    contents = new ArrayList<>();
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
        .add("title", title)
        .add("contents", pageArray.build())
        .build();
  }

  public ArrayList<DialogPage> getPages() {
    return contents;
  }

  @Override
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
