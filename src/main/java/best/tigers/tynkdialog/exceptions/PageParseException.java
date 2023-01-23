package best.tigers.tynkdialog.exceptions;

public class PageParseException extends RuntimeException {

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
