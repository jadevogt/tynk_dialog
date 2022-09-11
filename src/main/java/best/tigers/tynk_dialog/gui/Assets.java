package best.tigers.tynk_dialog.gui;

import best.tigers.tynk_dialog.util.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {
  public static final String APPLICATION_NAME = "Tynk Dialog Editor";
  public static final String APPLICATION_VERSION = "0.0.1";
  public static final Dimension INTEGRATION_WINDOW_SIZE = new Dimension(300, 300);
  public static String APPLICATION_AUTHOR = "Jade Vogt @tigerstyping";
  private static Assets singleInstance = null;
  private Font terminus;
  private BufferedImage timer;

  private Assets() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("terminus.ttf").getFile());
    terminus = new Font ("Terminus (TTF)", 0, 20);
    try {
      GraphicsEnvironment ge =
              GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
      BufferedImage timer = ImageIO.read(classLoader.getResource("timer.png").openStream());
      System.out.println(timer.getWidth());
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }

  public Image getTimer() {
    return timer;
  }

  public static Assets getInstance() {
    if (singleInstance == null) {
      singleInstance = new Assets();
    }
    return singleInstance;
  }

  public Font getFont() {
    return terminus;
  }

  private static void addOSXKeyStrokes(InputMap inputMap) {
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);
  }

  public static void runIntegrations() {
    System.setProperty("apple.awt.application.name", Assets.APPLICATION_NAME);
    Desktop desktop = Desktop.getDesktop();
    if (System.getProperty("os.name", "").startsWith("Mac OS X")) {
      // Ensure OSX key bindings are used for copy, paste etc
      // Use the Nimbus keys and ensure this occurs before any component creation
      Log.info("Using MacOS keybinds...");
      addOSXKeyStrokes((InputMap) UIManager.get("EditorPane.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("FormattedTextField.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("PasswordField.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("TextField.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("TextPane.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("TextArea.focusInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("Table.ancestorInputMap"));
      addOSXKeyStrokes((InputMap) UIManager.get("Tree.focusInputMap"));
    }

  }
}