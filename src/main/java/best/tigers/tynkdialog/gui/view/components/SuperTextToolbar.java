package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.gui.view.components.neo.NeoFunctionCallDialog;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.*;

public class SuperTextToolbar extends JToolBar {

  public SuperTextToolbar(SuperTextEditorPane editorPane, Runnable autoDisableSkip) {
    super();
    var map = editorPane.getActionMap();
    var inputMap = editorPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    var function = new JButton(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = NeoFunctionCallDialog.promptForFunctionDetails();
        SuperTextEditorKit.addFunctionCall(editorPane, details[0], details[1]);
        autoDisableSkip.run();
      }
    });
    function.setText("Function call...");
    var actions = new Object[]{
        SuperTextEditorKit.TYNK_RED_TEXT,
        SuperTextEditorKit.TYNK_YELLOW_TEXT,
        SuperTextEditorKit.TYNK_BLUE_TEXT,
        SuperTextEditorKit.TYNK_GREEN_TEXT,
        SuperTextEditorKit.TYNK_GREY_TEXT,
        SuperTextEditorKit.TYNK_WHITE_TEXT,
        SuperTextEditorKit.DELAY_ACTION_FIVE,
        SuperTextEditorKit.DELAY_ACTION_FIFTEEN,
        SuperTextEditorKit.DELAY_ACTION_SIXTY
    };
    Arrays.stream(actions).forEach(action -> {
      add(map.get(action));
    });
    add(function);
  }
}