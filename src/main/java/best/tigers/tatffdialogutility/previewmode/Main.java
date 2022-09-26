package best.tigers.tatffdialogutility.previewmode;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Main {
  public static void main(String... args) {
    var textBox = new TextBox();
    textBox.printString("The quick \nbrown fox jumps over the lazy dogThe quick brown fox jumps over the lazy dog");
  }
}
