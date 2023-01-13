package best.tigers.tynkdialog.supertext;

import best.tigers.tynkdialog.game.Constants;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class SuperTextWriter {

  public void write(Document doc, int start, int len, Writer out)
          throws IOException, BadLocationException {
    Element root = doc.getDefaultRootElement();
    int iStart = root.getElementIndex(start);
    int iEnd = root.getElementIndex(start + len);

    for (int i = iStart; i <= iEnd; i++) {
      Element par = root.getElement(i);
      writePar(par, start, len, out);
      if (i != iEnd) {
        out.write("<n=1>");
      }
    }
  }

  public void writePar(Element par, int start, int len, Writer out)
          throws IOException, BadLocationException {
    int iStart = par.getElementIndex(start);
    int iEnd = par.getElementIndex(start + len);
    for (int i = iStart; i <= iEnd; i++) {
      Element text = par.getElement(i);
      writeText(text, start, len, out);
    }
  }

  public void writeText(Element text, int start, int len, Writer out)
          throws IOException, BadLocationException {
    if (text.getAttributes().getAttribute(SuperTextDocument.DELAY_MAGNITUDE_NAME) != null
            && text.getName().equals("icon")) {
      int delay_magnitude =
              (int) text.getAttributes().getAttribute(SuperTextDocument.DELAY_MAGNITUDE_NAME);
      out.write("<t=" + delay_magnitude + ">");
      return;
    }
    if (text.getAttributes().getAttribute(SuperTextDocument.FUNCTION_CALL_NAME) != null
            && text.getName().equals("icon")) {
      String function_name = (String) text.getAttributes()
              .getAttribute(SuperTextDocument.FUNCTION_CALL_NAME);
      String function_param = (String) text.getAttributes()
              .getAttribute(SuperTextDocument.FUNCTION_PARAM_NAME);
      out.write("<f=" + function_name + "," + function_param + ">");
      return;
    }
    var color = StyleConstants.getForeground(text.getAttributes());
    var behavior =
            (Constants.Behavior)
                    text.getAttributes().getAttribute(SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME);
    boolean specifiedColor = false;
    boolean specifiedBehavior = false;
    if (Arrays.stream(Constants.TextColor.values())
            .anyMatch(
                    (textColor) -> {
                      boolean isGameColor = textColor.toAWT().equals(color);
                      boolean isNotDefault = !(textColor.equals(Constants.TextColor.WHITE));
                      return isGameColor && isNotDefault;
                    })) {
      out.write(Constants.TextColor.fromAWT(color).asTag());
      specifiedColor = true;
    }
    if (behavior != null) {
      out.write(behavior.asTag());
      specifiedBehavior = true;
    }

    int textStart = Math.max(start, text.getStartOffset());
    int textEnd = Math.min(start + len, text.getEndOffset());
    String s = text.getDocument().getText(textStart, textEnd - textStart);

    out.write(s.replace("\n", ""));
    if (specifiedBehavior) {
      out.write("</b>");
    }
    if (specifiedColor) {
      out.write("</c>");
    }

  }
}
