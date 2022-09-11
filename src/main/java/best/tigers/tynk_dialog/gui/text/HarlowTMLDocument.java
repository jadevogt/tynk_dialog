package best.tigers.tynk_dialog.gui.text;

import best.tigers.tynk_dialog.game.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class HarlowTMLDocument extends DefaultStyledDocument {
    public static final String BEHAVIOR_ATTRIBUTE_NAME = "behavior";
    public static final String DELAY_ELEMENT_NAME = "TimeDelayElement";
    public static final String DELAY_MAGNITUDE_NAME = "TimeDelayMagnitude";
    private boolean lengthLock = true;

    public void insertTimeDelay(int offset, int timeDelayQuantity) {
        lengthLock = false;
        SimpleAttributeSet attrs = new SimpleAttributeSet(getCharacterElement(offset).getAttributes());
        StyleConstants.setForeground(attrs, Constants.TextColor.WHITE.toAWT());
        BufferedImage icon = null;
        var classLoader = getClass().getClassLoader();
        try {
            icon =ImageIO.read(classLoader.getResource("timer.png").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        attrs.addAttribute(DELAY_MAGNITUDE_NAME, timeDelayQuantity);

        StyleConstants.setIcon(attrs, new ImageIcon(icon));
        System.out.println(StyleConstants.getIcon(attrs));
        System.out.println(attrs.getAttribute(DELAY_MAGNITUDE_NAME));
        try {
            insertString(offset, " ", attrs);
        } catch (BadLocationException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Can't insert delay!");
        }
        lengthLock = true;
        dump(System.out);
    }
    static class TimeDelayView extends BoxView {
        public TimeDelayView(Element elem) {
            super(elem, X_AXIS);
            setSize(5, 5);
        }

        public void paint(Graphics g, Shape a) {
            var boundaries = a.getBounds();
            Graphics2D g2 = (Graphics2D) g;
            g2.clearRect(boundaries.x, boundaries.y, boundaries.width, boundaries.height);
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.red);
            var halfHeight = boundaries.y;
            g2.drawLine(boundaries.x, boundaries.y, boundaries.x, boundaries.y + halfHeight);
            g2.drawLine(boundaries.x + 8, boundaries.y, boundaries.x + 8, boundaries.y + halfHeight);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.black);
            g2.drawLine(boundaries.x, boundaries.y + boundaries.height, boundaries.x + boundaries.width, boundaries.y + boundaries.height);
            super.paint(g, a);
        }

    }

    public void changeDelayMagnitude(Element e, int magnitude) {
        var attribs = new SimpleAttributeSet();
        attribs.addAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME, magnitude);
        setCharacterAttributes(e.getStartOffset(), e.getEndOffset() - e.getStartOffset(), attribs, false);
    }

    @Override
    public void insertString(int offs,
                             String str,
                             AttributeSet a) throws BadLocationException {
        if (!lengthLock) {
            super.insertString(offs, str, a);
            return;
        }
        try {
            Element currentElement = getParagraphElement(offs);
            Element[] rootElements = getRootElements();
            int paragraphCount = 0;
            for (var el : rootElements) {
                if (el.getName().equals("section")) {
                    paragraphCount = el.getElementCount();
                }
            }
            int timerCount = 0;
            for (int i = 0; i < currentElement.getElementCount(); i++) {
                if (currentElement.getElement(i).getName().equals("icon")) {
                    timerCount += 1;
                }
            }
            var stringLines = str.split("\n");
            var strLongestLine = Arrays.stream(stringLines).max((String line1, String line2) -> {return line1.length() - line2.length();});
            if ((currentElement.getEndOffset() - currentElement.getStartOffset() < (45 + timerCount) && (!(str.contains("\n")) || (!(str.equals("\n")) && stringLines.length <= (4 - paragraphCount) && strLongestLine.orElseGet(()->{return "";}).length() <= (45+timerCount))))
                    || (str.equals("\n") && paragraphCount <= 3)
            ) {
                    super.insertString(offs, str, a);
            } else {
                return;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    static class LineView extends ParagraphView {
        public LineView(Element element) {
            super(element);
        }
    }

    public void setBehavior(int startOffset, int endOffset, Constants.Behavior behavior) {
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        attrs.addAttribute(BEHAVIOR_ATTRIBUTE_NAME, behavior);
        setCharacterAttributes(startOffset, endOffset-startOffset, attrs, false);
    }

    public void clearBehavior(int startOffset, int endOffset) {
        for (int i =startOffset; i < endOffset; i ++) {
            var currentElement = getCharacterElement(i);
            SimpleAttributeSet attrs=new SimpleAttributeSet(currentElement.getAttributes());
            attrs.removeAttribute(BEHAVIOR_ATTRIBUTE_NAME);
            setCharacterAttributes(i, 1, attrs, true);
        }
    }

    class HarlowTMLEntityElement extends AbstractDocument.LeafElement {
        /**
         * Constructs an element that represents content within the
         * document (has no children).
         *
         * @param parent The parent element
         * @param a      The element attributes
         * @param offs0  The start offset &gt;= 0
         * @param offs1  The end offset &gt;= offs0
         * @since 1.4
         */
        public HarlowTMLEntityElement(Element parent, AttributeSet a, int offs0, int offs1) {
            super(parent, a, offs0, offs1);
        }

        /**
         * Creates a new AbstractElement.
         *
         * @param parent the parent element
         * @param a      the attributes for the element
         * @since 1.4
         */

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getEndOffset() {
            return 0;
        }

        @Override
        public Element getElement(int index) {
            return null;
        }

        @Override
        public int getElementCount() {
            return 0;
        }

        @Override
        public int getElementIndex(int offset) {
            return 0;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public boolean getAllowsChildren() {
            return false;
        }

        @Override
        public Enumeration<TreeNode> children() {
            return null;
        }
    }
}