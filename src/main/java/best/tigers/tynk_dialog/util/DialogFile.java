package best.tigers.tynk_dialog.util;

import best.tigers.tynk_dialog.exceptions.DialogFileIOException;
import best.tigers.tynk_dialog.exceptions.DialogParseException;
import best.tigers.tynk_dialog.game.Dialog;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DialogFile {
  final private static String SEP = System.getProperty("file.separator");
  final private static String DEFAULT_PATH = System.getProperty("user.home") + SEP + "dialog.json";

  private String path;
  private boolean customized = true;
  private final LinkedList<String> errors;

  public DialogFile(String path) {
    errors = new LinkedList<>();
    this.path = path;
  }

  public DialogFile() {
    this(DEFAULT_PATH);
    customized = false;
  }

  /**
   * Read Dialog data from the given file location
   *
   * @return ArrayList of Dialog objects representing the file contents
   * @throws DialogFileIOException Errors encountered while reading / writing file from / to disk
   */
  public ArrayList<Dialog> readFile() throws DialogFileIOException {
    JsonReader x;
    try {
      x = Json.createReader(new FileReader(path));
    } catch (FileNotFoundException notFound) {
      throw new DialogFileIOException("The dialog file couldn't be loaded: " + notFound);
    }
    JsonStructure jsonData = x.read();
    if (jsonData.getValueType() != JsonValue.ValueType.ARRAY) {
      throw new DialogFileIOException("The JSON file's data must be presented as an array, " +
              "but this looks like " + jsonData.getValueType());
    }
    ArrayList<Dialog> dialog = new ArrayList<Dialog>();
    for (JsonValue item : jsonData.asJsonArray()) {
      DialogBuilder dialogBuilder = new DialogBuilder();
      try {
        dialogBuilder.ParseJSON(item.asJsonObject());
      } catch (DialogParseException dpe) {
        addError(dpe.getMessage());
      }
      dialog.add(dialogBuilder.build());
    }
    return dialog;
  }

  /**
   * Write the given ArrayList of Dialog objects to the attached file
   *
   * @param dialogData data to be written
   */
  public void writeFile(ArrayList<Dialog> dialogData) throws DialogFileIOException {
    JsonWriter writer = null;
    try {
      Log.info("Attempting to save JSON file to disk: " + path);
      writer = Json.createWriter(new FileWriter(path));
    } catch (IOException ioe) {
      System.err.println(ioe.getLocalizedMessage());
      Log.error("(FATAL) IOException encountered while trying to save file " + ioe.getLocalizedMessage());
      System.exit(1);
    }
    Log.info("Successfully opened file, writing data...");
    JsonArrayBuilder builder = Json.createArrayBuilder();
    for (Dialog dialog : dialogData) {
      builder.add(dialog.serialize());
    }
    JsonArray out = builder.build();
    writer.write(out);
    writer.close();
    Log.info("Data written successfully!");
  }

  public void addError(String newError) {
    errors.add(newError);
    Log.error("(NON-FATAL) Error while performing IO: " + newError);
  }

  /**
   * Retrieve the accumulated list of non-fatal errors encountered during IO operations, and clear
   * the error list out to prepare for further operations.
   *
   * @return ArrayList of error strings
   */
  public ArrayList<String> fetchErrors() {
    ArrayList<String> returnValue = new ArrayList<String>();
    while (!errors.isEmpty()) {
      String current = errors.remove();
      returnValue.add(current);
    }
    return returnValue;
  }

  public boolean isCustomized() {
    return customized;
  }

  public void setPath(String newPath) {
    this.path = newPath;
    this.customized = true;
  }
}
