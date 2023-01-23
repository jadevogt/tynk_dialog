package best.tigers.tynkdialog.exceptions;

public class PageModelException extends RuntimeException {

  public PageModelException() {
  }

  public PageModelException(String message) {
    super(message);
  }

  public PageModelException(Throwable cause) {
    super(cause);
  }

  public PageModelException(String message, Throwable cause) {
    super(message, cause);
  }
}
