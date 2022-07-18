package best.tigers.tynk_dialog.game;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

public class Dialog {
  private static int untitledDialogCount = 0;
  private static String defaultTitle = "Untitled";
  private String title;
  private ArrayList<DialogPage> contents;

  public Dialog(String title) {
    this.title = title;
    contents = new ArrayList<DialogPage>();
  }

  public Dialog() {
    String suffix = Dialog.untitledDialogCount > 0 ? " " + Integer.toString(Dialog.untitledDialogCount) : "";
    title = defaultTitle + suffix;
    contents = new ArrayList<DialogPage>();
    Dialog.untitledDialogCount += 1;
  }

  public void addPage(DialogPage newPage) {
    contents.add(newPage);
  }

  public String toString() {
    return title;
  }

  public JsonObject serialize() {
    var pageArray = Json.createArrayBuilder();
    for (DialogPage currentPage : contents) {
      pageArray.add(currentPage.serialize());
    }
    var result = Json.createObjectBuilder()
            .add("title", this.title)
            .add("contents", pageArray.build())
            .build();
    return result;
  }
}