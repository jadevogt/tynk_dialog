package best.tigers.tynkdialog.supertext;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.IconView;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import org.apache.commons.lang3.StringUtils;

public class SuperTextEditorKit extends StyledEditorKit {

  public static final String TYNK_RED_TEXT = "Red";
  public static final String TYNK_YELLOW_TEXT = "Yellow";
  public static final String TYNK_GREEN_TEXT = "Green";
  public static final String TYNK_BLUE_TEXT = "Blue";
  public static final String TYNK_WHITE_TEXT = "White";
  public static final String TYNK_GREY_TEXT = "Grey";

  public static final TynkDelayAction DELAY_ACTION_FIVE = new TynkDelayAction(5);
  public static final TynkDelayAction DELAY_ACTION_FIFTEEN = new TynkDelayAction(15);
  public static final TynkDelayAction DELAY_ACTION_SIXTY = new TynkDelayAction(60);


  public static final Action[] defaultActions = {
      new TynkColorAction(Constants.TextColor.RED, KeyStroke.getKeyStroke(KeyEvent.VK_R,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkColorAction(Constants.TextColor.YELLOW, KeyStroke.getKeyStroke(KeyEvent.VK_Y,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkColorAction(Constants.TextColor.GREEN, KeyStroke.getKeyStroke(KeyEvent.VK_G,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkColorAction(Constants.TextColor.GREY, KeyStroke.getKeyStroke(KeyEvent.VK_H,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkColorAction(Constants.TextColor.BLUE, KeyStroke.getKeyStroke(KeyEvent.VK_B,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkColorAction(Constants.TextColor.WHITE, KeyStroke.getKeyStroke(KeyEvent.VK_W,
          InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, true)),
      new TynkBehaviorAction(Constants.Behavior.WAVE, KeyStroke.getKeyStroke(KeyEvent.VK_W,
          InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK, true)),
      new TynkBehaviorAction(Constants.Behavior.QUAKE, KeyStroke.getKeyStroke(KeyEvent.VK_Q,
          InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK, true)),
      new ClearTynkBehaviorAction(),
      DELAY_ACTION_FIVE,
      DELAY_ACTION_FIFTEEN,
      DELAY_ACTION_SIXTY,
  };
  private final MouseMotionListener listener;
  private final MouseListener clickListener;
  private Cursor oldCursor;

  // private static final Boolean showInvisibles = true;
  public SuperTextEditorKit() {
    listener = new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        JEditorPane src = (JEditorPane) e.getSource();
        int pos = src.viewToModel2D(e.getPoint());
        View v = src.getUI().getRootView(src);
        while (v != null && !(v instanceof SuperTextLabelView) && !(v instanceof IconView)) {
          src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          int i = v.getViewIndex(pos, Position.Bias.Forward);
          v = v.getView(i);
        }

        if (v != null
            && v.getAttributes().getAttribute(SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME) != null) {
          String x = ((Constants.Behavior) v.getAttributes().getAttribute(
              SuperTextDocument.BEHAVIOR_ATTRIBUTE_NAME)).getBehaviorName();
          src.setToolTipText("Behavior: " + x);
          javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
        } else if (v != null
            && v.getAttributes().getAttribute(SuperTextDocument.DELAY_MAGNITUDE_NAME) != null
            && v instanceof IconView) {
          src.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          src.setToolTipText("Delay: " + (int) v.getAttributes().getAttribute(
              SuperTextDocument.DELAY_MAGNITUDE_NAME));
          javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
        } else if (v != null
            && v.getAttributes().getAttribute(SuperTextDocument.FUNCTION_CALL_NAME) != null
            && v instanceof IconView) {
          src.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          src.setToolTipText("Function Call: " + v.getAttributes().getAttribute(
              SuperTextDocument.FUNCTION_CALL_NAME) + '(' + v.getAttributes().getAttribute(SuperTextDocument.FUNCTION_PARAM_NAME) + ')');
          javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
        } else {
          javax.swing.ToolTipManager.sharedInstance().setInitialDelay(1000);
          src.setToolTipText("");
        }
      }
    };
    clickListener = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JEditorPane src = (JEditorPane) e.getSource();
        int pos = src.viewToModel2D(e.getPoint());
        View v = src.getUI().getRootView(src);
        while (v != null && !(v instanceof IconView)) {
          int i = v.getViewIndex(pos, Position.Bias.Forward);
          v = v.getView(i);
        }

        if (v != null
            && v.getAttributes().getAttribute(SuperTextDocument.DELAY_MAGNITUDE_NAME) != null) {
          if (v.getDocument() instanceof SuperTextDocument doc) {
            int newMagnitude = Integer.parseInt(
                JOptionPane.showInputDialog("Please enter a new duration"));
            doc.changeDelayMagnitude(v.getElement(), newMagnitude);
          }
        }

        if (v != null
            && v.getAttributes().getAttribute(SuperTextDocument.FUNCTION_CALL_NAME) != null) {
          if (v.getDocument() instanceof SuperTextDocument doc) {
            String[] newDetails  = FunctionCallDialog.promptForFunctionDetails((String) v.getAttributes().getAttribute(SuperTextDocument.FUNCTION_CALL_NAME), (String) v.getAttributes().getAttribute(SuperTextDocument.FUNCTION_PARAM_NAME));
            doc.changeFunctionCall(v.getElement(), newDetails[0], newDetails[1]);
          }
        }
      }
    };
  }

  public static void addTimeDelay(JTextComponent e, int delayMagnitude) {
    if (e instanceof JEditorPane editor) {
      var document = editor.getDocument();
      if (document instanceof SuperTextDocument doc) {
        doc.insertTimeDelay(e.getSelectionStart(), delayMagnitude);
      }
    }
  }

  public static void addFunctionCall(JTextComponent e, String functionName, String functionParam) {
    if (e instanceof JEditorPane editor) {
      var document = editor.getDocument();
      if (document instanceof SuperTextDocument doc) {
        doc.insertFunctionCall(e.getSelectionStart(), functionName, functionParam);
      }
    }
  }

  @Override
  public String getContentType() {
    return "supertext/supertext";
  }

  @Override
  public Document createDefaultDocument() {
    return new SuperTextDocument();
  }

  @Override
  public ViewFactory getViewFactory() {
    return new SuperTextViewFactory();
  }

  public Stream<TynkColorAction> getColorActions() {
    return Arrays.stream(defaultActions).filter((action -> action instanceof TynkColorAction))
        .map(a -> (TynkColorAction) a);
  }

  public Stream<TynkBehaviorAction> getBehaviorActions() {
    return Arrays.stream(defaultActions).filter((action -> action instanceof TynkBehaviorAction))
        .map(a -> (TynkBehaviorAction) a);
  }

  public Action getClearBehaviorAction() {
    return new ClearTynkBehaviorAction();
  }

  @Override
  public Action[] getActions() {
    return StyledTextAction.augmentList(super.getActions(), defaultActions);
  }

  @Override
  public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
    var reader = new SuperTextReader(doc, pos, in);
    reader.read();
  }

  @Override
  public Object clone() {
    return new SuperTextEditorKit();
  }

  @Override
  public void write(Writer out, Document doc, int pos, int len)
      throws IOException, BadLocationException {
    var writer = new SuperTextWriter();
    writer.write(doc, pos, len, out);
  }

  @Override
  public void install(JEditorPane c) {
    super.install(c);
    c.addMouseMotionListener(listener);
    c.addMouseListener(clickListener);
    //c.addMouseMotionListener(lstMoveCollapse);
  }

  @Override
  public void deinstall(JEditorPane c) {
    c.removeMouseMotionListener(listener);
    c.removeMouseListener(clickListener);
    super.deinstall(c);
  }

  static class TynkColorIcon implements Icon {

    private final Constants.TextColor color;
    private final Dimension size;

    public TynkColorIcon(Dimension size, Constants.TextColor color) {
      this.color = color;
      this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(color.toAWT());
      g.fillRect(x, y, getIconWidth(), getIconHeight());
    }

    @Override
    public int getIconWidth() {
      return size.width;
    }

    @Override
    public int getIconHeight() {
      return size.height;
    }
  }

  public static class TynkColorAction extends ForegroundAction {

    private final KeyStroke shortcutKey;
    private final String keyMapName;

    public TynkColorAction(Constants.TextColor tynkColor, KeyStroke shortcutKey) {
      super(StringUtils.capitalize(tynkColor.getGameName()), tynkColor.toAWT());
      this.shortcutKey = shortcutKey;
      this.keyMapName = tynkColor.getGameName() + "keyboardShortcut";
      putValue(Action.SHORT_DESCRIPTION, "Make the selected supertext " + tynkColor.getGameName() + ".");
      putValue(Action.SMALL_ICON, new TynkColorIcon(new Dimension(16, 16), tynkColor));
      putValue(Action.LARGE_ICON_KEY, new TynkColorIcon(new Dimension(32, 16), tynkColor));
    }

    public KeyStroke getShortcutKey() {
      return shortcutKey;
    }

    public String getKeyMapName() {
      return keyMapName;
    }
  }

  public static class ClearTynkBehaviorAction extends StyledTextAction {

    //private Constants.Behavior tynkBehavior;
    public ClearTynkBehaviorAction() {
      super("None");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      var textComponent = getTextComponent(e);
      if (textComponent instanceof JEditorPane editor) {
        var document = getStyledDocument(editor);
        if (document instanceof SuperTextDocument doc) {
          doc.clearBehavior(editor.getSelectionStart(), editor.getSelectionEnd());
        }
      }
    }
  }

  public static class TynkBehaviorAction extends StyledTextAction {

    private final Constants.Behavior tynkBehavior;
    private final KeyStroke shortcutKey;
    private final String keyMapName;

    public TynkBehaviorAction(Constants.Behavior tynkBehavior, KeyStroke shortcutKey) {
      super(tynkBehavior.getBehaviorName());
      this.shortcutKey = shortcutKey;
      this.keyMapName = tynkBehavior.getBehaviorName() + "keyboardShortcut";
      this.tynkBehavior = tynkBehavior;
    }

    public KeyStroke getShortcutKey() {
      return shortcutKey;
    }

    public String getKeyMapName() {
      return keyMapName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      var textComponent = getTextComponent(e);
      if (textComponent instanceof JEditorPane editor) {
        var document = getStyledDocument(editor);
        if (document instanceof SuperTextDocument doc) {
          doc.setBehavior(editor.getSelectionStart(), editor.getSelectionEnd(), tynkBehavior);
        }
      }
    }
  }

  public static class TynkDelayAction extends StyledTextAction {

    private final int delayMagnitude;

    public TynkDelayAction(int delayMagnitude) {
      super("Wait " + delayMagnitude);
      this.delayMagnitude = delayMagnitude;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      SuperTextEditorKit.addTimeDelay(getTextComponent(e), delayMagnitude);
    }
  }

  public static class TynkFunctionCallAction extends StyledTextAction {

    private final String functionName;
    private final String functionParam;

    public TynkFunctionCallAction(String functionName, String functionParam) {
      super("Function call: " + functionName + '(' + functionParam + ')');
      this.functionName = functionName;
      this.functionParam = functionParam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      SuperTextEditorKit.addFunctionCall(getTextComponent(e), functionName, functionParam);
    }
  }

}
