package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.gui.ToolFrame;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String... args) throws FileNotFoundException {
    EventQueue.invokeLater(
        () -> {
          var frame = new ToolFrame();
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setVisible(true);
        });
  }
}
