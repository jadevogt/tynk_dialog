package best.tigers.tynkdialog.game.supertext;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class SuperTextTokenizer {

  private final Reader input;
  private final ArrayList<SuperTextToken> tokens;
  private int currentCharacter;
  private int currentPosition;
  private State currentState;
  private SuperTextTagToken currentTag;

  public SuperTextTokenizer(Reader input) throws IOException {
    this.input = input;
    this.currentCharacter = input.read();
    this.currentState = State.DATA;
    this.currentPosition = 0;
    this.tokens = new ArrayList<>();
  }

  public ArrayList<SuperTextToken> tokenize() throws TokenizerException {
    proceed();
    return tokens;
  }

  private void proceed() throws TokenizerException {
    currentPosition++;
    try {
      switch (currentState) {
        case DATA -> data();
        case TAG_OPEN -> tagOpen();
        case END_TAG_OPEN -> endTagOpen();
        case TAG_NAME -> tagName();
        case TAG_VALUE -> tagValue();
      }
    } catch (IOException ignored) {
    }
  }

  public char getCharacter() throws EOFException {
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
    if (c == '<') {
      setState(State.TAG_OPEN);
    } else {
      tokens.add(new SuperTextCharacterToken(c));
    }
    proceed();
  }

  public void tagOpen() throws TokenizerException, IOException {
    var c = getCharacter();
    switch (c) {
      case '/' -> {
        consume();
        setState(State.END_TAG_OPEN);
      }
      case '>' -> throw new TokenizerException("The opening tag ended before a name was specified");
      case '=' -> throw new TokenizerException("Nameless opening tag tried defining a value");
      default -> {
        currentTag = new SuperTextTagToken(SuperTextTagToken.TagType.OPEN);
        setState(State.TAG_NAME);
      }
    }
    proceed();
  }

  public void endTagOpen() throws TokenizerException, IOException {
    var c = getCharacter();
    switch (c) {
      case '>' -> throw new TokenizerException("The closing tag ended before a name was specified");
      case '=' -> throw new TokenizerException("The closing tag tried to define a value");
      default -> {
        currentTag = new SuperTextTagToken(SuperTextTagToken.TagType.CLOSE);
        setState(State.TAG_NAME);
      }
    }
    proceed();
  }

  public void tagName() throws TokenizerException, IOException {
    char c = consume();
    switch (c) {
      case '=' -> setState(State.TAG_VALUE);
      case '>' -> closeTag();
      case '<' -> throw new TokenizerException("Attempted to open a new tag while inside of a tag");
      default -> currentTag.setTagName(currentTag.getTagName() + c);
    }
    proceed();
  }

  public void tagValue() throws TokenizerException, IOException {
    var c = consume();
    if (c == '>') {
      closeTag();
    } else {
      currentTag.setTagValue(currentTag.getTagValue() + c);
    }
    proceed();
  }

  public void closeTag() {
    tokens.add(currentTag);
    currentTag = null;
    setState(State.DATA);
  }

  public enum State {
    DATA,
    TAG_OPEN,
    END_TAG_OPEN,
    TAG_NAME,
    TAG_VALUE
  }

  public class TokenizerException extends Exception {

    private final String cause;

    public TokenizerException(String cause) {
      super();
      this.cause = cause;
    }

    @Override
    public String getMessage() {
      return ("Error while tokenizing tag at position #" + currentPosition + ": " + this.cause);
    }
  }
}
