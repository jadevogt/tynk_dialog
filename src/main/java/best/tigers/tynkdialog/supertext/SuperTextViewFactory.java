package best.tigers.tynkdialog.supertext;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class SuperTextViewFactory implements ViewFactory {

  @Override
  public View create(Element elem) {
    String kind = elem.getName();
    if (kind != null) {
      switch (kind) {
        case AbstractDocument.ContentElementName:
          return new SuperTextLabelView(elem);
        case AbstractDocument.ParagraphElementName:
          return new SuperTextDocument.LineView(elem);
        case AbstractDocument.SectionElementName:
          return new BoxView(elem, View.Y_AXIS);
        case StyleConstants.ComponentElementName:
          return new ComponentView(elem);
        case StyleConstants.IconElementName:
          return new IconView(elem);
        case SuperTextDocument.DELAY_ELEMENT_NAME:
        case SuperTextDocument.FUNCTION_ELEMENT_NAME:
          return new SuperTextEntityView(elem);
        // return new HarlowTMLLabelView(elem);
      }
    }
    // default to supertext display
    return new SuperTextLabelView(elem);
  }
}
