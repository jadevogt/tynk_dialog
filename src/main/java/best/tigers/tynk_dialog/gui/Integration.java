package best.tigers.tynk_dialog.gui;

import best.tigers.tynk_dialog.util.Log;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.awt.desktop.AboutHandler;
import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;
import java.awt.event.KeyEvent;

public class Integration {
  public static final String APPLICATION_NAME = "Tynk Dialog Editor";
  public static final String APPLICATION_VERSION = "0.0.1";
  public static final Dimension INTEGRATION_WINDOW_SIZE = new Dimension(300, 300);
  public static String APPLICATION_AUTHOR = "Jade Vogt @tigerstyping";

  private static void addOSXKeyStrokes(InputMap inputMap) {
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);
  }

  public static void runIntegrations() {
    System.setProperty("apple.awt.application.name", Integration.APPLICATION_NAME);
    var desktop = Desktop.getDesktop();
    desktop.setAboutHandler(new About());
    desktop.setPreferencesHandler(new Preferences());
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

class About implements AboutHandler {
  @Override
  public void handleAbout(AboutEvent e) {
    var about = new JDialog();
    about.setModal(true);
    about.setTitle("About " + Integration.APPLICATION_NAME);
    var panel = new JPanel();
    var layout = new GroupLayout(panel);
    var heading = new JLabel(Integration.APPLICATION_NAME);
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    var headingFont = heading.getFont();
    headingFont = headingFont.deriveFont(headingFont.getStyle() | Font.BOLD, 16);
    heading.setFont(headingFont);
    var version = new JLabel("version " + Integration.APPLICATION_VERSION);
    var versionFont = version.getFont();
    versionFont = versionFont.deriveFont(versionFont.getStyle() | Font.ITALIC, 14);
    version.setFont(versionFont);
    version.setHorizontalAlignment(SwingConstants.CENTER);
    panel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(heading, 300, 300, 300)
        .addComponent(version, 300, 300, 300));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addGroup(layout.createSequentialGroup()
            .addComponent(heading)
            .addComponent(version)
        ));
    about.add(panel);
    panel.setMinimumSize(Integration.INTEGRATION_WINDOW_SIZE);
    panel.setMaximumSize(Integration.INTEGRATION_WINDOW_SIZE);
    about.setMinimumSize(Integration.INTEGRATION_WINDOW_SIZE);
    about.setMaximumSize(Integration.INTEGRATION_WINDOW_SIZE);
    about.setPreferredSize(Integration.INTEGRATION_WINDOW_SIZE);
    panel.setPreferredSize(Integration.INTEGRATION_WINDOW_SIZE);
    about.pack();
    about.setLocationRelativeTo(null);
    about.setVisible(true);
    about.requestFocus();
  }
}

class Preferences implements PreferencesHandler {
  @Override
  public void handlePreferences(PreferencesEvent e) {
    var preferences = new JDialog();
    preferences.setSize(Integration.INTEGRATION_WINDOW_SIZE);
    preferences.setModal(true);
    preferences.setTitle("Preferences");
    preferences.setLocationRelativeTo(null);
    preferences.setVisible(true);
    preferences.requestFocus();
  }
}