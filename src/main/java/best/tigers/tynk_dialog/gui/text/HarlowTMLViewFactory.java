package best.tigers.tynk_dialog.gui.text;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class HarlowTMLViewFactory implements ViewFactory {
  public View create(Element elem) {
    String kind = elem.getName();
    if (kind != null) {
      if (kind.equals(AbstractDocument.ContentElementName)) {
        return new HarlowTMLLabelView(elem);
      } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
        return new HarlowTMLDocument.LineView(elem);
      } else if (kind.equals(AbstractDocument.SectionElementName)) {
        return new BoxView(elem, View.Y_AXIS);
      } else if (kind.equals(StyleConstants.ComponentElementName)) {
        return new ComponentView(elem);
      } else if (kind.equals(StyleConstants.IconElementName)) {
        return new IconView(elem);
      } else if (kind.equals(HarlowTMLDocument.DELAY_ELEMENT_NAME)) {
        return new HarlowTMLEntityView(elem);
        // return new HarlowTMLLabelView(elem);
      }
    }
    // default to text display
    return new HarlowTMLLabelView(elem);
  }
}
