package best.tigers.tynkdialog.supertext;

import javax.imageio.ImageIO;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class SuperTextEntityView extends View {

  static BufferedImage image;
  private final ImageObserver imageObserver;
  private View parent;
  private Container container;

  public SuperTextEntityView(Element elem) {
    super(elem);
    try {
      image = ImageIO.read(getClass().getClassLoader().getResource("timer.png").openStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    imageObserver = new DummyObserver();
  }

  @Override
  public void setParent(View parent) {
    View oldParent = getParent();
    super.setParent(parent);
    container = (parent != null) ? getContainer() : null;
  }

  @Override
  public float getPreferredSpan(int axis) {
    return 10;
  }

  @Override
  public void paint(Graphics g, Shape allocation) {
    var x = allocation.getBounds().x;
    var width = 10;
    var height = 10;
    var y = allocation.getBounds().y - (height / 2);
    g.drawImage(image, x, y, width, height, imageObserver);
  }

  @Override
  public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
    int p0 = getStartOffset();
    int p1 = getEndOffset();
    if ((pos >= p0) && (pos <= p1)) {
      Rectangle r = a.getBounds();
      if (pos == p1) {
        r.x += r.width;
      }
      r.width = 0;
      return r;
    }
    return null;
  }

  @Override
  public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
    Rectangle alloc = (Rectangle) a;
    if (x < alloc.x + alloc.width) {
      bias[0] = Position.Bias.Forward;
      return getStartOffset();
    }
    bias[0] = Position.Bias.Backward;
    return getEndOffset();
  }

  static class DummyObserver implements ImageObserver {

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
      return false;
    }
  }
}
