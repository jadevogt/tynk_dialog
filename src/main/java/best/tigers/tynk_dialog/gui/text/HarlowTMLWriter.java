package best.tigers.tynk_dialog.gui.text;
import best.tigers.tynk_dialog.game.Constants;

import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class HarlowTMLWriter {

    public static void write(Document doc, int start, int len, Writer out) throws IOException, BadLocationException {
        Element root=doc.getDefaultRootElement();
        int iStart=root.getElementIndex(start);
        int iEnd=root.getElementIndex(start+len);

        for (int i=iStart; i<=iEnd; i++) {
            Element par=root.getElement(i);
            writePar(par, start, len, out);
        }
    }

    public static void writePar(Element par, int start, int len, Writer out) throws IOException, BadLocationException {
        int iStart=par.getElementIndex(start);
        int iEnd=par.getElementIndex(start+len);
        for (int i=iStart; i<=iEnd; i++) {
            Element text=par.getElement(i);
            writeText(text, start, len, out);
        }
    }

    public static void writeText(Element text, int start, int len, Writer out) throws IOException, BadLocationException {
        if (text.getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME) != null && text.getName().equals("icon")) {
            int delay_magnitude = (int) text.getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME);
            out.write("<t=" + String.valueOf(delay_magnitude) + ">");
            return;
        }
        var color = StyleConstants.getForeground(text.getAttributes());
        var behavior = (Constants.Behavior) text.getAttributes().getAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME);
        boolean specifiedColor = false;
        boolean specifiedBehavior = false;
        if (Arrays.stream(Constants.TextColor.values())
                .anyMatch((c) -> {
                    boolean isGameColor = c.toAWT().equals(color);
                    boolean isNotDefault = !(c.equals(Constants.TextColor.WHITE));
                    return isGameColor && isNotDefault;
                })
        ) {
            out.write(Constants.TextColor.fromAWT(color).asTag());
            specifiedColor = true;
        }
        if (behavior != null) {
            out.write(behavior.asTag());
            specifiedBehavior = true;
        }
        int textStart=Math.max(start, text.getStartOffset());
        int textEnd=Math.min(start+len, text.getEndOffset());
        String s=text.getDocument().getText(textStart, textEnd-textStart);
        out.write(s);
        if (specifiedBehavior) {
            out.write("</b>");
        }
        if (specifiedColor) {
            out.write("</c>");
        }
    }
}

