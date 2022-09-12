package best.tigers.tynk_dialog.gui.text;

import best.tigers.tynk_dialog.game.Constants;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.IconView;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import org.apache.commons.lang3.StringUtils;

public class HarlowTMLEditorKit extends StyledEditorKit {
    public static final String TYNK_RED_TEXT = "Red";
    public static final String TYNK_YELLOW_TEXT = "Yellow";
    public static final String TYNK_GREEN_TEXT = "Green";
    public static final String TYNK_BLUE_TEXT = "Blue";
    public static final String TYNK_WHITE_TEXT = "White";
    public static final Action[] defaultActions = {
            new TynkColorAction(Constants.TextColor.RED),
            new TynkColorAction(Constants.TextColor.YELLOW),
            new TynkColorAction(Constants.TextColor.GREEN),
            new TynkColorAction(Constants.TextColor.BLUE),
            new TynkColorAction(Constants.TextColor.WHITE),
            new TynkBehaviorAction(Constants.Behavior.WAVE),
            new TynkBehaviorAction(Constants.Behavior.QUAKE),
            new ClearTynkBehaviorAction(),
            new TynkDelayAction(5),
            new TynkDelayAction(15),
            new TynkDelayAction(60),
    };
    private MouseMotionListener listener;
    private MouseListener clickListener;
    private Cursor oldCursor;
    private Boolean showInvisibles = true;
    public HarlowTMLEditorKit() {
        listener=new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                JEditorPane src=(JEditorPane)e.getSource();
                int pos=src.viewToModel2D(e.getPoint());
                View v=src.getUI().getRootView(src); while (v!=null && !(v instanceof HarlowTMLLabelView) && !(v instanceof IconView)) {
                    src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    int i=v.getViewIndex(pos, Position.Bias.Forward);
                    v=v.getView(i);
                }

                if (v!=null && v.getAttributes().getAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME) != null) {
                    String x = ((Constants.Behavior) v.getAttributes().getAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME)).getBehaviorName();
                    src.setToolTipText("Behavior: " + x);
                    javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
                } else if (v!=null && v.getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME)!=null && v instanceof IconView) {
                    src.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    src.setToolTipText("Delay: " + (int) v.getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME));
                    javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
                } else {
                    javax.swing.ToolTipManager.sharedInstance().setInitialDelay(1000);
                    src.setToolTipText("");
                }
            }
        };
        clickListener=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JEditorPane src=(JEditorPane)e.getSource();
                int pos=src.viewToModel2D(e.getPoint());
                View v=src.getUI().getRootView(src); while (v!=null && !(v instanceof IconView)) {
                    int i=v.getViewIndex(pos, Position.Bias.Forward);
                    v=v.getView(i);
                }

                if (v!=null && v.getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME)!=null) {
                    if (v.getDocument() instanceof HarlowTMLDocument doc) {
                        int newMagnitude = Integer.valueOf(JOptionPane.showInputDialog("Please enter a new duration"));
                        doc.changeDelayMagnitude(v.getElement(), newMagnitude);
                    }
                }
            }
        };
    }

    @Override
    public String getContentType() {
        return "text/harlowtml";
    }

    @Override
    public Document createDefaultDocument() {
        return new HarlowTMLDocument();
    }

    public ViewFactory getViewFactory() {
        return new HarlowTMLViewFactory();
    }



    public static void addTimeDelay(JTextComponent e, int delayMagnitude) {
        if (e instanceof JEditorPane editor) {
            var document = editor.getDocument();
            if (document instanceof HarlowTMLDocument doc) {
                doc.insertTimeDelay(e.getSelectionStart(), delayMagnitude);
            }
        }
    }

    public Stream<Action> getColorActions() {
        return Arrays.stream(defaultActions).filter((action -> action instanceof TynkColorAction));
    }

    public Stream<Action> getBehaviorActions() {
        return Arrays.stream(defaultActions).filter((action -> action instanceof TynkBehaviorAction));
    }

    public Action getClearBehaviorAction() {
        return new ClearTynkBehaviorAction();
    }

    @Override
    public Action[] getActions() {
        return StyledTextAction.augmentList(super.getActions(), defaultActions);
    }

    public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
        var reader = new HarlowTMLReader(doc, pos, in);
        reader.read();
    }

    public Object clone() {
        return new HarlowTMLEditorKit();
    }

    public void write(Writer out, Document doc, int pos, int len)
            throws IOException, BadLocationException {
        var writer = new HarlowTMLWriter();
        writer.write(doc, pos, len, out);
    }

    public void install(JEditorPane c) {
        super.install(c);
        c.addMouseMotionListener(listener);
        c.addMouseListener(clickListener);
        //c.addMouseMotionListener(lstMoveCollapse);
    }

    public void deinstall(JEditorPane c) {
        c.removeMouseMotionListener(listener);
        //c.removeMouseMotionListener(lstMoveCollapse);
        c.removeMouseListener(clickListener);
        super.deinstall(c);
    }

    static class TynkColorIcon implements Icon {
        private Constants.TextColor color;
        private Dimension size;
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

    static class TynkColorAction extends ForegroundAction {
        public TynkColorAction(Constants.TextColor tynkColor) {
            super(StringUtils.capitalize(tynkColor.getGameName()), tynkColor.toAWT());
            putValue(Action.SHORT_DESCRIPTION, "Make the selected text " + tynkColor.getGameName() + ".");
            putValue(Action.SMALL_ICON, new TynkColorIcon(new Dimension(16, 16), tynkColor));
            putValue(Action.LARGE_ICON_KEY, new TynkColorIcon(new Dimension(32, 32), tynkColor));
        }
    }

    static class ClearTynkBehaviorAction extends StyledTextAction {
        private Constants.Behavior tynkBehavior;
        public ClearTynkBehaviorAction() {
            super("Remove behaviors");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var textComponent = getTextComponent(e);
            if (textComponent instanceof JEditorPane editor) {
                var document = getStyledDocument(editor);
                if (document instanceof HarlowTMLDocument doc) {
                    doc.clearBehavior(editor.getSelectionStart(), editor.getSelectionEnd());
                }
            }
        }
    }

    static class TynkBehaviorAction extends StyledTextAction {
        private Constants.Behavior tynkBehavior;
        public TynkBehaviorAction(Constants.Behavior tynkBehavior) {
            super(tynkBehavior.getBehaviorName());
            this.tynkBehavior = tynkBehavior;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var textComponent = getTextComponent(e);
            if (textComponent instanceof JEditorPane editor) {
                var document = getStyledDocument(editor);
                if (document instanceof HarlowTMLDocument doc) {
                    doc.setBehavior(editor.getSelectionStart(), editor.getSelectionEnd(), tynkBehavior);
                }
            }
        }
    }

    public static class TynkDelayAction extends StyledTextAction {
        private int delayMagnitude;
        public TynkDelayAction(int delayMagnitude) {
            super("Delay" + String.valueOf(delayMagnitude));
            this.delayMagnitude = delayMagnitude;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HarlowTMLEditorKit.addTimeDelay(getTextComponent(e), delayMagnitude);
        }
    }

}
