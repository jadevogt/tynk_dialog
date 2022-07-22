package best.tigers.tynk_dialog.exceptions;

import java.io.IOException;

public class DialogParseException extends Exception {
    public DialogParseException() {
    }
    public DialogParseException(String message) {
        super(message);
    }
    public DialogParseException(Throwable cause) {
        super(cause);
    }
    public DialogParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

