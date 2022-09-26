package best.tigers.tatffdialogutility.previewmode;

import best.tigers.tatffdialogutility.game.Constants;
import best.tigers.tatffdialogutility.game.Constants.TextColor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

public class TextBox extends JComponent {
  private static final int BOX_WIDTH = 45;
  private static final int BOX_HEIGHT = 4;
  private int cursorColumn;
  private int cursorRow;
  private final int pxWidth;
  private final int pxHeight;
  private final OutputWindow seq;
  private BufferedImage out;
  private final SpriteSheetManager sprites;

  public TextBox() {
    //setBackground(Color.black);
    sprites = new SpriteSheetManager();
    pxWidth = SpriteSheetManager.CHAR_WIDTH * BOX_WIDTH;
    pxHeight = SpriteSheetManager.CHAR_HEIGHT * BOX_HEIGHT;
    seq = new OutputWindow(
        new BufferedImage(pxWidth, pxHeight, sprites.glyphForChar('a').getType())
    );
  }

  public void printString(String input) {
    var animationTimer = new Timer();
    int cumDelay = 0;
    out = new BufferedImage(pxWidth, pxHeight, sprites.glyphForChar('a').getType());
    setBorder(new EmptyBorder(10, 10, 10, 10));
    var chs = input.toCharArray();
    var g = (Graphics2D) out.getGraphics();
    g.setColor(Color.black);
    g.fillRect(0, 0, pxWidth, pxHeight);
    for (int i = 0; i < chs.length; i++) {
      if (i % 10 == 0) {
        cumDelay += ((int) (Math.random() * 1000));
      }
      final char c = chs[i];
      if (c == '\n') {
          lineFeed();
          continue;
      }
      putch(c, g, i, animationTimer, cumDelay, TextColor.RED);
    }
  }

  public void putch(char inputCharacter, Graphics2D g, int iteration, Timer animationTimer, int cumDelay, Constants.TextColor color) {
    var charSprite = sprites.glyphForChar(inputCharacter);
    var sourceX = cursorColumn * SpriteSheetManager.CHAR_WIDTH;
    var sourceY = cursorRow * SpriteSheetManager.CHAR_HEIGHT;
    animationTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        g.drawImage(charSprite, sourceX, sourceY, SpriteSheetManager.CHAR_WIDTH, SpriteSheetManager.CHAR_HEIGHT, seq.getPanel());
        seq.setDisplayedImage(out);
      }
    }, (long) (iteration * 16L) + cumDelay);

    advanceCursor();
  }

  public void advanceCursor() {
    if (++cursorColumn == BOX_WIDTH) {
      lineFeed();
    }
  }
  public void lineFeed() {
    cursorRow++;
    cursorColumn = 0;
  }
}


