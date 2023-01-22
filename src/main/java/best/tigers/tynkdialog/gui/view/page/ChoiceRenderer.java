package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.components.SuperTextDisplayPane;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

class ChoiceRenderer implements ListCellRenderer<ChoiceResponseModel> {
  @Override
  public Component getListCellRendererComponent(JList<? extends ChoiceResponseModel> list, ChoiceResponseModel value, int index, boolean isSelected, boolean cellHasFocus) {
    return new ChoiceCellComponent(value, isSelected);
  }
  static private class ChoiceCellComponent extends JPanel {
    private final ChoiceResponseModel model;
    private BufferedImage iconSheet;

    ChoiceCellComponent(ChoiceResponseModel choiceResponseModel, boolean selected) {
      super();

      this.model = choiceResponseModel;
      var text = new SuperTextDisplayPane(1, 0);
      var iconPanel = new ChoiceIconComponent(choiceResponseModel, selected);
      text.setFont(Assets.getLittle());
      text.setPreferredSize(new Dimension(250, text.getPreferredSize().height));
      text.setText(model.getContent());
      add(iconPanel);
      add(text);
      if (selected) {
        setBorder(new LineBorder(Color.decode("#493243")));
        text.setBackground(Color.decode("#695156"));
        text.setForeground(Color.decode("#EBEFB8"));
        setBackground(Color.decode("#695156"));
      } else {
        setBorder(new LineBorder(Color.decode("#89747A")));
        text.setBackground(Color.decode("#8D7D7D"));
        text.setForeground(Color.decode("#695156"));
        setBackground(Color.decode("#8D7D7D"));
      }
    }
  }

  static private class ChoiceIconComponent extends JPanel {
    private ChoiceResponseModel model;
    private BufferedImage iconSheet;

    ChoiceIconComponent(ChoiceResponseModel choiceResponseModel, boolean selected) {
      super();
      if (selected) {
        iconSheet = Assets.getChoiceIconsLit();
      } else {
        iconSheet = Assets.getChoiceIconsDim();
      }
      this.model = choiceResponseModel;
      this.setPreferredSize(new Dimension(14, 14));
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      var xoffset = model.getIcon().ordinal() * 7;
      var xoffset2 = (model.getIcon().ordinal() + 1) * 7;
      g.drawImage(iconSheet, 0, 0, 14, 14, xoffset, 0, xoffset2, 7, null);
    }
  }

}

