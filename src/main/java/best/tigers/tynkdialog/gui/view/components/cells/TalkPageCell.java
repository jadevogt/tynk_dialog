package best.tigers.tynkdialog.gui.view.components.cells;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class TalkPageCell extends JEditorPane {
  public static Font font = Assets.getInstance().getFont();

  public TalkPageCell() {
    super();
    setMargin(new Insets(0, 0, 0, 0));
    setFont(font);
    setForeground(Constants.TextColor.WHITE.toAWT());
    setBackground(Constants.TextColor.BACKGROUND.toAWT());
    setContentType("supertext/supertext");
    setPreferredSize(new Dimension(1000, 100));
  }

  public TalkPageCell(TalkPageModel talkPageModel, boolean isSelected) {
    this();
    setText(talkPageModel.getContent());
    if (isSelected) {
      setBackground(Color.decode("#297697"));
    }
  }

  public boolean getScrollableTracksViewportWidth() {
    Component parent = getParent();
    ComponentUI ui = getUI();

    return parent != null ? (ui.getPreferredSize(this).width <= parent.getSize().width) : true;
  }
}
