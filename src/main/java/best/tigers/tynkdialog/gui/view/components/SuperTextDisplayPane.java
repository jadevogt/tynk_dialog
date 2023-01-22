package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.supertext.SuperTextDocument;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JEditorPane;

public class SuperTextDisplayPane extends JEditorPane {

  public SuperTextDisplayPane(int maxLines) {
    setMargin(new Insets(5, 5, 5, 5));
    setFont(Assets.getTerminus());
    setForeground(Constants.TextColor.WHITE.toAWT());
    setBackground(Constants.TextColor.BACKGROUND.toAWT());
    setContentType("supertext/supertext");
    setPreferredSize(new Dimension(600, 30));
    if (getDocument() instanceof SuperTextDocument superTextDocument) {
      superTextDocument.setMaxLines(maxLines);
    }
  }

  public SuperTextDisplayPane(int maxLines, int insets) {
    this(maxLines);
    setMargin(new Insets(insets, insets, insets, insets));
    if (insets == 0)
      setPreferredSize(new Dimension(600, 14));
    else
      setPreferredSize(new Dimension(600, insets * 6));
  }

  @Override
  public void setForeground(Color c) {
    super.setForeground(c);
    if (getDocument() instanceof SuperTextDocument doc) {
      doc.setForegroundColor(c);
    }
  }

  public void setLighterBackground() {
    setBackground(Color.decode("#695156"));
    setForeground(Color.decode("#EAEFB8"));
  }

  public void setLightestBackground() {
    setBackground(Color.decode("#8D7D7E"));
    setForeground(Color.decode("#695156"));
  }

  public SuperTextDisplayPane() {
    this(4);
  }
}
