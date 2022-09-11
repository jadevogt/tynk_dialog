package best.tigers.tynk_dialog.gui.text;

import best.tigers.tynk_dialog.game.*;
import best.tigers.tynk_dialog.game.harlowtml.*;
import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;

public class HarlowTMLReader {
    private Stack<SimpleAttributeSet> tagStack = new Stack<>();
    private Reader rawInput;
    private Document d;
    private int position;

    public HarlowTMLReader(Document d, int position, Reader rawInput) {
        this.d = d;
        this.position = position;
        this.rawInput = rawInput;
    }

    public void read() throws IOException, BadLocationException {
        if (!(d instanceof HarlowTMLDocument)) {
            return;
        }
        HarlowTMLDocument doc = (HarlowTMLDocument) d;
        var tokenizer = new HarlowTMLTokenizer(rawInput);
        ArrayList<HarlowTMLToken> tokens = new ArrayList<>();
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
            if (token instanceof HarlowTMLTagToken && ((HarlowTMLTagToken) token).getType() != HarlowTMLTagToken.TagType.ENTITY) {
                System.out.println(token.getName());
                System.out.println(((HarlowTMLTagToken) token).getTagName());
                System.out.println(((HarlowTMLTagToken) token).getTagValue());
                System.out.println(((HarlowTMLTagToken) token).getType());
                changeAttributesBasedOnTag((HarlowTMLTagToken) token);
            } else if (token instanceof HarlowTMLCharacterToken characterToken) {
                doc.insertString(position++, characterToken.getContent(), tagStack.peek());
            } else if (token instanceof HarlowTMLTagToken && ((HarlowTMLTagToken) token).getType() == HarlowTMLTagToken.TagType.ENTITY) {
                insertEntityBasedOnTag((HarlowTMLTagToken) token);
            }
        }
    }

    private void insertEntityBasedOnTag(HarlowTMLTagToken entity) {
        if (d instanceof HarlowTMLDocument doc) {
            switch (entity.getTagName().toLowerCase()) {
                case "t", "wait", "time" -> {
                    System.out.println(Integer.valueOf(entity.getTagValue()));
                    doc.insertTimeDelay(position++, Integer.valueOf(entity.getTagValue()));
                }
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
        switch (tag.getTagName().toLowerCase()) {
            case "c", "color", "colour" -> {
                StyleConstants.setForeground(
                        tagStack.peek(),
                        Constants.TextColor.fromString(value).toAWT()
                );
            }
            case "b", "behavior", "behaviour" -> {
                tagStack.peek().addAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME,
                                      Constants.Behavior.fromString(value));
            }
            default -> {
            }
        }
        return;
    }
}