package best.tigers.tatffdialogutility.previewmode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import javax.imageio.ImageIO;

public class SpriteSheetManager {
  public static final int CHAR_WIDTH = 6;
  public static final int CHAR_HEIGHT = 12;
  public static final int SCALING_FACTOR = 4;
  private static final String CHAR_SEQUENCE = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
  private BufferedImage image;
  private final HashMap<Character, BufferedImage> glyphs;
  private final BufferedImage trans;

  public SpriteSheetManager() {
    var classLoader = getClass().getClassLoader();
    var resource = classLoader.getResource("tynk_font_6x12.png");
    glyphs = new HashMap<>();
    Objects.requireNonNull(resource, "The specified image could not be found!");
    try {
      var file = new File(resource.getFile());
      image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    int columns = image.getWidth() / CHAR_WIDTH;
    trans = new BufferedImage(CHAR_WIDTH, CHAR_HEIGHT, image.getType());
    assert CHAR_SEQUENCE.length() == columns : "The image and column count don't line up- slicing is impossible.";
    for (var currentCharacter : CHAR_SEQUENCE.toCharArray()) {
      var sourceX = CHAR_WIDTH * CHAR_SEQUENCE.indexOf(currentCharacter);
      var sourceY =  0;
      var destX = sourceX + CHAR_WIDTH;
      var destY = CHAR_HEIGHT;
      var newImage = new BufferedImage(CHAR_WIDTH, CHAR_HEIGHT, image.getType());
      var graphics = newImage.createGraphics();
      graphics.drawImage(image, 0, 0, CHAR_WIDTH, CHAR_HEIGHT, sourceX, sourceY, destX, destY, null);
      glyphs.put(currentCharacter, newImage);
    }
  }

  public BufferedImage glyphForChar(char glyph) {
    if (glyph == ' ') {
      return trans;
    }
    if (glyphs.containsKey(glyph)) {
      return glyphs.get(glyph);
    }
    return glyphs.get('?');
  }
}
