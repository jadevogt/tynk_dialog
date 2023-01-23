package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.gui.controller.DialogController;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class DialogCellRenderer extends JLabel implements ListCellRenderer<DialogController> {

  @Override
  public Component getListCellRendererComponent(
      JList<? extends DialogController> list,
      DialogController value,
      int index,
      boolean isSelected,
      boolean cellHasFocus) {
    setText(value.getModel().getTitle());

    Color background;
    Color foreground;
    setBorder(new EmptyBorder(5, 5, 5, 5));

    // check if this cell represents the current DnD drop location
    setOpaque(true);
    JList.DropLocation dropLocation = list.getDropLocation();
    if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
      background = Assets.getDefaults().getColor("List.background");
      foreground = Assets.getDefaults().getColor("List.foreground");
      // check if this cell is selected
    } else if (isSelected) {
      background = Assets.getDefaults().getColor("List.selectionBackground");
      foreground = Assets.getDefaults().getColor("List.selectionForeground");

      // unselected, and not the DnD drop location
    } else {
      background = Assets.getDefaults().getColor("List.background");
      foreground = Assets.getDefaults().getColor("List.foreground");
    }
    setBackground(background);
    setForeground(foreground);

    return this;
  }
}
