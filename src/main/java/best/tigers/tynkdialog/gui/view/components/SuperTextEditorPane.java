package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.supertext.SuperTextDocument;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import best.tigers.tynkdialog.util.Log;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;

public class SuperTextEditorPane extends JEditorPane {

  public static SuperTextEditorPane lastFocused = null;

  public SuperTextEditorPane(int maxLines) {
    setMargin(new Insets(5, 5, 5, 5));
    setFont(Assets.getTerminus());
    setForeground(Constants.TextColor.WHITE.toAWT());
    setBackground(Constants.TextColor.BACKGROUND.toAWT());
    setContentType("text/supertext");
    setPreferredSize(new Dimension(600, 30));
    if (getDocument() instanceof SuperTextDocument superTextDocument) {
      superTextDocument.setMaxLines(maxLines);
    }
    var self = this;
    addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        super.focusGained(e);
        lastFocused = self;
      }
    });
    setEditorKit(new SuperTextEditorKit());
  }

  public SuperTextEditorPane(int maxLines, int insets) {
    this(maxLines);
    setMargin(new Insets(insets, insets, insets, insets));
    if (insets == 0) {
      setPreferredSize(new Dimension(600, 14));
    } else {
      setPreferredSize(new Dimension(600, insets * 6));
    }
    setEditorKit(new SuperTextEditorKit());
  }

  public SuperTextEditorPane() {
    this(4);
  }

  @Override
  public void setForeground(Color c) {
    super.setForeground(c);
    if (getDocument() instanceof SuperTextDocument doc) {
      doc.setForegroundColor(c);
    }
  }

  public SuperTextEditorKit getSuperTextEditorKit() {
    var editorKit = this.getEditorKit();
    if (!(editorKit instanceof SuperTextEditorKit)) {
      Log.error("EditorKit is not instance of SuperTextEditorKit");
    }
    return (SuperTextEditorKit) this.getEditorKit();
  }

  public void setLighterBackground() {
    setBackground(Color.decode("#695156"));
    setForeground(Color.decode("#EAEFB8"));
  }

  public void setLightestBackground() {
    setBackground(Color.decode("#8D7D7E"));
    setForeground(Color.decode("#695156"));
  }
}
