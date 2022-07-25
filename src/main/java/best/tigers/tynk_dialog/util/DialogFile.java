package best.tigers.tynk_dialog.util;

import best.tigers.tynk_dialog.exceptions.DialogFileIOException;
import best.tigers.tynk_dialog.exceptions.DialogParseException;
import best.tigers.tynk_dialog.game.Dialog;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DialogFile {
  private final String filename;
  private final LinkedList<String> errors;

  public DialogFile(String filename) {
    this.errors = new LinkedList<String>();
    this.filename = filename;
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
      x = Json.createReader(new FileReader(filename));
    } catch (FileNotFoundException notFound) {
      throw new DialogFileIOException("The dialog file couldn't be loaded: " + notFound);
    }
    var jsonData = x.read();
    if (jsonData.getValueType() != JsonValue.ValueType.ARRAY) {
      throw new DialogFileIOException("The JSON file's data must be presented as an array, " +
              "but this looks like " + jsonData.getValueType());
    }
    var dialog = new ArrayList<Dialog>();
    for (var item : jsonData.asJsonArray()) {
      var dialogBuilder = new DialogBuilder();
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
      Log.info("""
              Attempting to save JSON file to disk: \
              """ + filename);
      writer = Json.createWriter(new FileWriter(filename));
    } catch (IOException ioe) {
      System.err.println(ioe.getLocalizedMessage());
      Log.error("""
              (FATAL) IOException encountered while trying to save file: \
              """ + ioe.getLocalizedMessage());
      System.exit(1);
    }
    Log.info("""
            Successfully opened file, writing data...
            """);
    var builder = Json.createArrayBuilder();
    for (var dialog : dialogData) {
      builder.add(dialog.serialize());
    }
    var out = builder.build();
    writer.write(out);
    writer.close();
    Log.info("""
            Data written successfully!
            """);
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
    var returnValue = new ArrayList<String>();
    while (!errors.isEmpty()) {
      var current = errors.remove();
      returnValue.add(current);
    }
    return returnValue;
  }
}
