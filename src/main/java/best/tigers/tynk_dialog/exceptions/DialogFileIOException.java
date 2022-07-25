package best.tigers.tynk_dialog.exceptions;

import java.io.IOException;

public class DialogFileIOException extends IOException {
  public DialogFileIOException() {
  }

  public DialogFileIOException(String message) {
    super(message);
  }

  public DialogFileIOException(Throwable cause) {
    super(cause);
  }

  public DialogFileIOException(String message, Throwable cause) {
    super(message, cause);
  }
}
