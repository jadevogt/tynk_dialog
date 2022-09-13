package best.tigers.tynk_dialog.gui.text;

import best.tigers.tynk_dialog.game.Constants;
import best.tigers.tynk_dialog.game.harlowtml.HarlowTMLCharacterToken;
import best.tigers.tynk_dialog.game.harlowtml.HarlowTMLTagToken;
import best.tigers.tynk_dialog.game.harlowtml.HarlowTMLToken;
import best.tigers.tynk_dialog.game.harlowtml.HarlowTMLTokenizer;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class HarlowTMLReader {
    private final ArrayDeque<SimpleAttributeSet> tagStack = new ArrayDeque<>();
    private final Reader rawInput;
    private final Document document;
    private int position;

    public HarlowTMLReader(Document document, int position, Reader rawInput) {
        this.document = document;
        this.position = position;
        this.rawInput = rawInput;
    }

    public void read() throws IOException, BadLocationException {
        if (!(document instanceof HarlowTMLDocument doc)) {
            return;
        }
        var tokenizer = new HarlowTMLTokenizer(rawInput);
        ArrayList<HarlowTMLToken> tokens;
        try {
            tokens = tokenizer.tokenize();
        } catch (HarlowTMLTokenizer.TokenizerException e) {
            e.printStackTrace();
            return;
        }
        tagStack.push(new SimpleAttributeSet());
        StyleConstants.setForeground(tagStack.peek(), Constants.TextColor.WHITE.toAWT());
        StyleConstants.setBackground(tagStack.peek(), Constants.TextColor.BACKGROUND.toAWT());
        for (var token : tokens) {
            if (token instanceof HarlowTMLTagToken tagToken) {
                switch (tagToken.getType()) {
                    case OPEN, CLOSE -> changeAttributesBasedOnTag(tagToken);
                    case ENTITY -> insertEntityBasedOnTag(tagToken);
                }
            } else if (token instanceof HarlowTMLCharacterToken charToken) {
                doc.insertString(position++, charToken.getContent(), tagStack.peek());
            }
        }
    }

    private void insertEntityBasedOnTag(HarlowTMLTagToken entity) {
        if (document instanceof HarlowTMLDocument doc) {
            switch (entity.getTagName().toLowerCase()) {
                case "t", "wait", "time" -> {
                    System.out.println(Integer.valueOf(entity.getTagValue()));
                    doc.insertTimeDelay(position++, Integer.parseInt(entity.getTagValue()));
                }
                default -> throw new IllegalStateException(
                    "Unexpected value: " + entity.getTagName().toLowerCase());
            }
        }
    }

    private void changeAttributesBasedOnTag(HarlowTMLTagToken tag) {
        if (tag.getType() == HarlowTMLTagToken.TagType.CLOSE) {
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
                attribs.addAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME, Constants.Behavior.fromString(value));
            default -> throw new IllegalStateException("Invalid tag \"%s\" in document".formatted(tagName));
        }
    }
}