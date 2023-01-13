package best.tigers.tynkdialog.supertext;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.supertext.tokens.SuperTextCharacterToken;
import best.tigers.tynkdialog.supertext.tokens.SuperTextTagToken;
import best.tigers.tynkdialog.supertext.tokens.SuperTextToken;
import best.tigers.tynkdialog.supertext.tokens.SuperTextTokenizer;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class SuperTextReader {

  private final ArrayDeque<SimpleAttributeSet> tagStack = new ArrayDeque<>();
  private final Reader rawInput;
  private final Document document;
  private int position;

  public SuperTextReader(Document document, int position, Reader rawInput) {
    this.document = document;
    this.position = position;
    this.rawInput = rawInput;
  }

  public void read() throws IOException, BadLocationException {
    if (!(document instanceof SuperTextDocument doc)) {
      return;
    }
    var tokenizer = new SuperTextTokenizer(rawInput);
    ArrayList<SuperTextToken> tokens;
    try {
      tokens = tokenizer.tokenize();
    } catch (SuperTextTokenizer.TokenizerException e) {
      e.printStackTrace();
      return;
    }
    tagStack.push(new SimpleAttributeSet());
    StyleConstants.setForeground(tagStack.peek(), Constants.TextColor.WHITE.toAWT());
    // StyleConstants.setBackground(tagStack.peek(), Constants.TextColor.BACKGROUND.toAWT());
    for (var token : tokens) {
      if (token instanceof SuperTextTagToken tagToken) {
        switch (tagToken.getTagType()) {
          case OPEN, CLOSE -> changeAttributesBasedOnTag(tagToken);
          case ENTITY -> insertEntityBasedOnTag(tagToken);
        }
      } else if (token instanceof SuperTextCharacterToken charToken) {
        doc.insertString(position++, charToken.getContent(), tagStack.peek());
      }
    }
  }

  private void insertEntityBasedOnTag(SuperTextTagToken entity) throws BadLocationException {
    if (document instanceof SuperTextDocument doc) {
      switch (entity.getTagName().toLowerCase()) {
        case "t", "wait", "time" ->
            doc.insertTimeDelay(position++, Integer.parseInt(entity.getTagValue()));
        case "f", "function" ->
            doc.insertFunctionCall(position++, entity.getTagValue().split(",")[0],
                entity.getTagValue().split(",")[1]);
        case "n" ->
            doc.insertString(position++, "\n".repeat(Integer.parseInt(entity.getTagValue())),
                tagStack.peek());
        default -> throw new IllegalStateException(
            "Unexpected value: " + entity.getTagName().toLowerCase());
      }
    }
  }

  private void changeAttributesBasedOnTag(SuperTextTagToken tag) {
    if (tag.getTagType() == SuperTextTagToken.TagType.CLOSE) {
      tagStack.pop();
      return;
    }
    tagStack.push(new SimpleAttributeSet(tagStack.peek()));
    var value = tag.getTagValue();
    var attribs = tagStack.peek();
    assert (attribs != null);
    var tagName = tag.getTagName().toLowerCase();
    switch (tagName) {
      case "c", "color", "colour" ->
          StyleConstants.setForeground(attribs, Constants.TextColor.fromString(value).toAWT());
      case "b", "behavior", "behaviour" ->
          attribs.addAttribute(SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME,
              Constants.Behavior.fromString(value));
      default ->
          throw new IllegalStateException("Invalid tag \"%s\" in document".formatted(tagName));
    }
  }
}