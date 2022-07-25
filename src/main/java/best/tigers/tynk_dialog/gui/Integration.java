package best.tigers.tynk_dialog.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.awt.desktop.AboutHandler;
import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

public class Integration {
  public static final String APPLICATION_NAME = "Tynk Dialog Editor";
  public static final String APPLICATION_VERSION = "0.0.1";
  public static final Dimension INTEGRATION_WINDOW_SIZE = new Dimension(300, 300);
  public static String APPLICATION_AUTHOR = "Jade Vogt @tigerstyping";

  public Integration() {
    var desktop = Desktop.getDesktop();
    desktop.setAboutHandler(new About());
    desktop.setPreferencesHandler(new Preferences());
  }

  public static void runIntegrations() {
    System.setProperty("apple.awt.application.name", Integration.APPLICATION_NAME);
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