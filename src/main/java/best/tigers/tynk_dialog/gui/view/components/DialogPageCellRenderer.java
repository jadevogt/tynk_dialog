package best.tigers.tynk_dialog.gui.view.components;

import best.tigers.tynk_dialog.gui.model.DialogPageModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogPageCellRenderer extends JLabel implements ListCellRenderer<DialogPageModel> {
  @Override
  public Component getListCellRendererComponent(JList<? extends DialogPageModel> list, DialogPageModel value, int index, boolean isSelected, boolean cellHasFocus) {
    setText(value.getSpeaker() + " - \"" + value.getContent() + "\"");

    Color background;
    Color foreground;
    setBorder(new EmptyBorder(5,5,5,5));

    // check if this cell represents the current DnD drop location
    setOpaque(true);
    JList.DropLocation dropLocation = list.getDropLocation();
    if (dropLocation != null
        && !dropLocation.isInsert()
        && dropLocation.getIndex() == index) {

      background = Color.BLUE;
      foreground = Color.WHITE;

      // check if this cell is selected
    } else if (isSelected) {
      background = Color.decode("#39698a");
      foreground = Color.WHITE;

      // unselected, and not the DnD drop location
    } else {
      if (index % 2 != 0) {
        background = Color.decode("#f7f8fa");
      } else {
        background = Color.WHITE;
      }
      foreground = Color.BLACK;
    }
    setBackground(background);
    setForeground(foreground);

    return this;
  }
}
