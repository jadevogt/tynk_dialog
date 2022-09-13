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
  @Override
  public View create(Element elem) {
    String kind = elem.getName();
    if (kind != null) {
      switch (kind) {
        case AbstractDocument.ContentElementName:
          return new HarlowTMLLabelView(elem);
      }
      if (kind.equals(AbstractDocument.ParagraphElementName)) {
        return new HarlowTMLDocument.LineView(elem);
      }
      if (kind.equals(AbstractDocument.SectionElementName)) {
        return new BoxView(elem, View.Y_AXIS);
      }
      if (kind.equals(StyleConstants.ComponentElementName)) {
        return new ComponentView(elem);
      }
      if (kind.equals(StyleConstants.IconElementName)) {
        return new IconView(elem);
      }
      if (kind.equals(HarlowTMLDocument.DELAY_ELEMENT_NAME)) {
        return new HarlowTMLEntityView(elem);
        // return new HarlowTMLLabelView(elem);
      }
    }
    // default to text display
    return new HarlowTMLLabelView(elem);
  }
}
