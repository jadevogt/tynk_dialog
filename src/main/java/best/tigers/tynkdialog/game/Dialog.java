package best.tigers.tynkdialog.game;

import best.tigers.tynkdialog.game.page.Page;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;

/**
 * Represents what the internal documentation refers to as a "Dialog File," essentially a titled
 * collection of objects implementing the Page interface.
 *
 * @see Page
 */
public class Dialog {

  private static final String DEFAULT_TITLE = "Untitled";
  private static int untitledDialogCount = 0;
  private final ArrayList<Page> contents;
  private String title;

  public Dialog(String title, ArrayList<Page> contents) {
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
    title = DEFAULT_TITLE + suffix;
    contents = new ArrayList<>();
    Dialog.untitledDialogCount += 1;
  }

  /**
   * Appends a page to the end of the collection of DialogPages
   *
   * @param newPage a DialogPage object to be added
   */
  public void addPage(Page newPage) {
    contents.add(newPage);
  }

  /**
   * Serializes all contained DialogPages and itself
   *
   * @return the JSON representation of the Dialog
   */
  public JsonObject serialize() {
    javax.json.JsonArrayBuilder pageArray = Json.createArrayBuilder();
    for (Page currentPage : contents) {
      pageArray.add(currentPage.asPage());
    }
    return Json.createObjectBuilder()
            .add("title", this.title)
            .add("contents", pageArray.build())
            .build();
  }

  public ArrayList<Page> getPages() {
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
