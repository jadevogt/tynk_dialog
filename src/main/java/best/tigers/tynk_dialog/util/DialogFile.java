package best.tigers.tynk_dialog.util;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.exceptions.DialogParseException;
import best.tigers.tynk_dialog.exceptions.DialogFileIOException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.json.*;

public class DialogFile {
    private String filename;
    private LinkedList<String> errors;

    public DialogFile(String filename) {
        this.errors = new LinkedList<String>();
        this.filename = filename;
    }

    /**
     * Read Dialog data from the given file location
     * @return ArrayList of Dialog objects representing the file contents
     * @throws DialogFileIOException
     */
    public ArrayList<Dialog> readFile() throws DialogFileIOException {
        JsonReader x = null;
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
                errors.add(dpe.getMessage());
            }
            dialog.add(dialogBuilder.build());
        }
        return dialog;
    }

    /**
     * Write the given ArrayList of Dialog objects to the attached file
     * @param dialogData data to be written
     */
    public void writeFile(ArrayList<Dialog> dialogData) throws DialogFileIOException {
        JsonWriter writer = null;
        try {
            writer = Json.createWriter(new FileWriter(filename));
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(1);
        }
        var builder = Json.createArrayBuilder();
        for (var dialog : dialogData) {
            builder.add(dialog.serialize());
        }
        var out = builder.build();
        System.out.println(out);
        writer.write(out);
        writer.close();
    }

    /**
     * Retrieve the accumulated list of non-fatal errors encountered during IO operations, and clear
     * the error list out to prepare for further operations.
     * @return ArrayList of error strings
     */
    public ArrayList<String> fetchErrors() {
        var returnValue = new ArrayList<String>();
        while (!errors.isEmpty()) {
            returnValue.add(errors.remove());
        }
        return returnValue;
    }
}
