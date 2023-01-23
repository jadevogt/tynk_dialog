package best.tigers.tynkdialog.gui.view.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {

  private final JMenuBar menuBar;
  private final JMenu fileMenu;
  private final JMenu editMenu;
  private final JMenu helpMenu;
  private final Map<String, JMenu> menuMap;

  public MenuBar(JFrame frame) {
    menuMap = new HashMap<>();
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    helpMenu = new JMenu("Help");
    menuBar.add(fileMenu);
    menuMap.put("File", fileMenu);
    menuBar.add(editMenu);
    menuMap.put("File", editMenu);
    // menuBar.add(helpMenu);
    frame.setJMenuBar(menuBar);
  }

  public MenuBar(JFrame frame, String... additionalMenus) {
    this(frame);
    Arrays.stream(additionalMenus).forEach(this::addMenu);
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

  public Map<String, JMenu> getMenuMap() {
    return menuMap;
  }

  public void addMenu(String menu) {
    var newMenu = new JMenu(menu);
    menuBar.add(newMenu);
    menuMap.put(menu, newMenu);
  }

  public enum Menu {
    FILE,
    EDIT,
    HELP
  }
}
