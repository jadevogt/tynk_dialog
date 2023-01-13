package best.tigers.tynkdialog.gui.view.components;

import javax.swing.*;

public class MenuBar {

  private final JMenuBar menuBar;
  private final JMenu fileMenu;
  private final JMenu editMenu;
  private final JMenu helpMenu;

  public MenuBar(JFrame frame) {
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    helpMenu = new JMenu("Help");
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    // menuBar.add(helpMenu);
    frame.setJMenuBar(menuBar);
  }

  public JMenuBar getMenuBar() {
    return menuBar;
  }

  public void addItem(Menu menu, JMenuItem item) {
    if (menu == Menu.FILE) {
      addFileItem(item);
    } else if (menu == Menu.EDIT) {
      addEditItem(item);
    } else if (menu == Menu.HELP) {
      addHelpItem(item);
    }
  }

  public void addFileItem(JMenuItem item) {
    fileMenu.add(item);
  }

  public void addEditItem(JMenuItem item) {
    editMenu.add(item);
  }

  public void addHelpItem(JMenuItem item) {
    helpMenu.add(item);
  }

  public enum Menu {
    FILE,
    EDIT,
    HELP
  }
}
