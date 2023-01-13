package best.tigers.tynkdialog.exceptions;

public class PageParseException extends Exception {

  public PageParseException() {
  }

  public PageParseException(String message) {
    super(message);
  }

  public PageParseException(Throwable cause) {
    super(cause);
  }

  public PageParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
