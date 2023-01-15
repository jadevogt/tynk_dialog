package best.tigers.tynkdialog.util;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;

public class Assets {

  public static final String APPLICATION_NAME = "Tynk Dialog Editor";
  public static final String APPLICATION_VERSION = "0.0.1";
  public static final Dimension INTEGRATION_WINDOW_SIZE = new Dimension(300, 300);
  public static final String APPLICATION_AUTHOR = "Jade Vogt @tigerstyping";
  private static Assets singleInstance = null;
  private final Font terminus;
  private final UIDefaults defaults;
  private BufferedImage timer;

  private Assets() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource("terminus.ttf")).getFile());
    terminus = new Font("Terminus (TTF)", Font.PLAIN, 20);
    defaults = UIManager.getDefaults();
    try {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }

  public static Assets getInstance() {
    if (singleInstance == null) {
      singleInstance = new Assets();
    }
    return singleInstance;
  }

  private static void addOSXKeyStrokes(InputMap inputMap) {
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK),
        DefaultEditorKit.copyAction);
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK),
        DefaultEditorKit.pasteAction);
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK),
        DefaultEditorKit.selectAllAction);
  }

  public static void runIntegrations() {
    System.setProperty("apple.awt.application.name", Assets.APPLICATION_NAME);
    Desktop desktop = Desktop.getDesktop();
    if (System.getProperty("os.name", "").startsWith("Mac OS X")) {
      // Ensure OSX key bindings are used for copy, paste etc.
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

  public static Font getFont() {
    return getInstance().terminus;
  }

  public static UIDefaults getDefaults() {
    return getInstance().defaults;
  }

  public static Window findWindow(Component c) {
    if (c == null) {
      return JOptionPane.getRootFrame();
    } else if (c instanceof Window) {
      return (Window) c;
    } else {
      return findWindow(c.getParent());
    }
  }
}
