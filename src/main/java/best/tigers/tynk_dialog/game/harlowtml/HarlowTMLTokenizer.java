package best.tigers.tynk_dialog.game.harlowtml;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class HarlowTMLTokenizer {
  private Reader input;
  private char[] ch;
  private int currentCharacter;
  private State currentState;
  private int currentPosition;
  private HarlowTMLTagToken currentTag;
  private ArrayList<HarlowTMLToken> tokens;

  public HarlowTMLTokenizer(Reader input) throws IOException {
    this.input = input;
    this.currentCharacter = input.read();
    this.currentState = State.DATA;
    this.currentPosition = 0;
    this.tokens = new ArrayList<>();
  }

  public ArrayList<HarlowTMLToken> tokenize() throws TokenizerException {
    proceed();
    return tokens;
  }

  private void proceed() throws TokenizerException {
    try {
      switch (currentState) {
        case DATA:
          data();
          break;
        case TAG_OPEN:
          tagOpen();
          break;
        case END_TAG_OPEN:
          endTagOpen();
          break;
        case TAG_NAME:
          tagName();
          break;
        case TAG_VALUE:
          tagValue();
          break;
      }
    } catch (IOException e) {
      return;
    }
  }

  public char getch() throws EOFException {
    if (currentCharacter == -1) {
      throw new EOFException("End of input reached");
    }
    return (char) currentCharacter;
  }

  public void setState(State newState) {
    this.currentState = newState;
  }

  public char consume() throws IOException {
    int temp = currentCharacter;
    if (temp == -1) {
      throw new EOFException("End of input reached");
    }
    currentCharacter = input.read();
    return (char) temp;
  }

  public void data() throws TokenizerException, IOException {
    var c = consume();
    switch (c) {
      case '<':
        setState(State.TAG_OPEN);
        break;
      default:
        tokens.add(new HarlowTMLCharacterToken(c));
    }
    proceed();
  }

  public void tagOpen() throws TokenizerException, IOException {
    var c = getch();
    switch (c) {
      case '/':
        consume();
        setState(State.END_TAG_OPEN);
        break;
      case '>':
        throw new TokenizerException("The opening tag ended before a name was specified");
      case '=':
        throw new TokenizerException(
            "The opening tag tried to define a value without having a name");
      default:
        currentTag = new HarlowTMLTagToken(HarlowTMLTagToken.TagType.OPEN);
        setState(State.TAG_NAME);
    }
    proceed();
  }

  public void endTagOpen() throws TokenizerException, IOException {
    var c = getch();
    switch (c) {
      case '>':
        throw new TokenizerException("The closing tag ended before a name was specified");
      case '=':
        throw new TokenizerException("The closing tag tried to define a value");
      default:
        currentTag = new HarlowTMLTagToken(HarlowTMLTagToken.TagType.CLOSE);
        setState(State.TAG_NAME);
    }
    proceed();
  }

  public void tagName() throws TokenizerException, IOException {
    char c = consume();
    switch (c) {
      case '=':
        setState(State.TAG_VALUE);
        break;
      case '>':
        tokens.add(currentTag);
        currentTag = null;
        setState(State.DATA);
        break;
      case '<':
        throw new TokenizerException("Attempted to open a new tag while inside of a tag");
      default:
        currentTag.setTagName(currentTag.getTagName() + c);
        break;
    }
    proceed();
  }

  public void tagValue() throws TokenizerException, IOException {
    var c = consume();
    switch (c) {
      case '=':
        throw new TokenizerException("Encountered an unexpected equals sign in the tag's value");
      case '>':
        tokens.add(currentTag);
        currentTag = null;
        setState(State.DATA);
        break;
      default:
        currentTag.setTagValue(currentTag.getTagValue() + c);
        break;
    }
    proceed();
  }

  public enum State {
    DATA,
    TAG_OPEN,
    END_TAG_OPEN,
    TAG_NAME,
    TAG_VALUE
  }

  public class TokenizerException extends Throwable {
    private String cause;

    public TokenizerException(String cause) {
      super();
      this.cause = cause;
    }

    @Override
    public String getMessage() {
      return ("Error while tokenizing tag at position #"
          + Integer.valueOf(currentPosition).toString()
          + ": "
          + this.cause);
    }
  }
}
