package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit.TynkFunctionCallAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.text.DefaultEditorKit;

public class SuperTextContentMenuBar extends JMenuBar {

  public SuperTextContentMenuBar(Runnable autoDisableSkip) {
    super();

    var editMenu = new JMenu("Edit");

    var cut = new DefaultEditorKit.CutAction();
    cut.putValue(Action.NAME, "Cut");
    editMenu.add(cut);

    var copy = new DefaultEditorKit.CopyAction();
    copy.putValue(Action.NAME, "Copy");
    editMenu.add(copy);

    var paste = new DefaultEditorKit.PasteAction();
    paste.putValue(Action.NAME, "Paste");
    editMenu.add(paste);

    var colorMenu = new JMenu("Color");
    SuperTextEditorKit.getColorActions().forEach(colorMenu::add);

    var behaviorMenu = new JMenu("Behavior");
    SuperTextEditorKit.getBehaviorActions().forEach(behaviorMenu::add);

    var insertMenu = new JMenu("Insert");
    insertMenu.add(new SuperTextEditorKit.TynkDelayAction());
    insertMenu.add(new TynkFunctionCallAction(autoDisableSkip));

    add(editMenu);
    add(insertMenu);
    add(colorMenu);
    add(behaviorMenu);
  }
}
