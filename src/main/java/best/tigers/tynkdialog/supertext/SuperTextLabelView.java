package best.tigers.tynkdialog.supertext;

import best.tigers.tynkdialog.game.Constants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.text.Element;
import javax.swing.text.LabelView;

public class SuperTextLabelView extends LabelView {

  public SuperTextLabelView(Element elem) {
    super(elem);
  }

  @Override
  public void paint(Graphics g, Shape allocation) {
    super.paint(g, allocation);
    if (getAttributes().getAttribute(SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME) != null) {
      paintBehaviorIndicator(
          g,
          allocation,
          (Constants.Behavior)
              getAttributes().getAttribute(SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME));
    }
  }

  public void paintBehaviorIndicator(Graphics g, Shape a, Constants.Behavior tynkBehavior) {
    int y = (int) (a.getBounds().getY() + a.getBounds().getHeight());
    int x1 = (int) a.getBounds().getX();
    int x2 = (int) (a.getBounds().getX() + a.getBounds().getWidth());

    Color old = g.getColor();
    var g2 = (Graphics2D) g;
    g2.setColor(tynkBehavior.getBehaviorColor().toAWT());
    g2.setStroke(new BasicStroke(1));
    for (int i = x1; i <= x2; i += 6) {
      g.drawArc(i + 3, y - 3, 3, 3, 0, 180);
      g.drawArc(i + 6, y - 3, 3, 3, 180, 181);
    }
    g.setColor(old);
  }
}
