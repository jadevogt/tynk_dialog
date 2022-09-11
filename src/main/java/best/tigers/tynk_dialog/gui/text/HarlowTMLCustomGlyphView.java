package best.tigers.tynk_dialog.gui.text;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class HarlowTMLCustomGlyphView extends GlyphView {
    /**
     * Constructs a new view wrapped on an element.
     *
     * @param elem the element
     */
    private byte[] selections;
    public HarlowTMLCustomGlyphView(Element elem) {
        super(elem);
    }

    private void initSelections(int p0, int p1) {
        int viewPosCount = p1 - p0 + 1;
        if (selections == null || viewPosCount > selections.length) {
            selections = new byte[viewPosCount];
            return;
        }
        for (int i = 0; i < viewPosCount; selections[i++] = 0);
    }
    @Override
    public void paint(Graphics g, Shape a) {
        checkPainter();

        boolean paintedText = false;
        Component c = getContainer();
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a : a.getBounds();
        Color bg = getBackground();
        Color fg = getForeground();

        if (c != null && ! c.isEnabled()) {
            fg = (c instanceof JTextComponent ?
                    ((JTextComponent)c).getDisabledTextColor() :
                    UIManager.getColor("textInactiveText"));
        }
        if (bg != null) {
            g.setColor(bg);
            g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
        }
        if (c instanceof JTextComponent) {
            JTextComponent tc = (JTextComponent) c;
            Highlighter h = tc.getHighlighter();
            if (h instanceof LayeredHighlighter) {
                ((LayeredHighlighter)h).paintLayeredHighlights
                        (g, p0, p1, a, tc, this);
            }
        }

        if(c instanceof JTextComponent) {
            JTextComponent tc = (JTextComponent) c;
            Color selFG = tc.getSelectedTextColor();

            if (// there's a highlighter (bug 4532590), and
                    (tc.getHighlighter() != null) &&
                            // selected text color is different from regular foreground
                            (selFG != null) && !selFG.equals(fg)) {

                Highlighter.Highlight[] h = tc.getHighlighter().getHighlights();
                if(h.length != 0) {
                    boolean initialized = false;
                    int viewSelectionCount = 0;
                    for (int i = 0; i < h.length; i++) {
                        Highlighter.Highlight highlight = h[i];
                        int hStart = highlight.getStartOffset();
                        int hEnd = highlight.getEndOffset();
                        if (hStart > p1 || hEnd < p0) {
                            // the selection is out of this view
                            continue;
                        }
                        if (hStart <= p0 && hEnd >= p1){
                            // the whole view is selected
                            paintTextInColor(g, a, selFG, p0, p1);
                            paintedText = true;
                            break;
                        }
                        // the array is lazily created only when the view
                        // is partially selected
                        if (!initialized) {
                            initSelections(p0, p1);
                            initialized = true;
                        }
                        hStart = Math.max(p0, hStart);
                        hEnd = Math.min(p1, hEnd);
                        paintTextInColor(g, a, selFG, hStart, hEnd);
                        // the array represents view positions [0, p1-p0+1]
                        // later will iterate this array and sum its
                        // elements. Positions with sum == 0 are not selected.
                        selections[hStart-p0]++;
                        selections[hEnd-p0]--;

                        viewSelectionCount++;
                    }

                    if (!paintedText && viewSelectionCount > 0) {
                        // the view is partially selected
                        int curPos = -1;
                        int startPos = 0;
                        int viewLen = p1 - p0;
                        while (curPos++ < viewLen) {
                            // searching for the next selection start
                            while(curPos < viewLen &&
                                    selections[curPos] == 0) curPos++;
                            if (startPos != curPos) {
                                // paint unselected text
                                paintTextInColor(g, a, fg,
                                        p0 + startPos, p0 + curPos);
                            }
                            int checkSum = 0;
                            // searching for next start position of unselected text
                            while (curPos < viewLen &&
                                    (checkSum += selections[curPos]) != 0) curPos++;
                            startPos = curPos;
                        }
                        paintedText = true;
                    }
                }
            }
        }
        if(!paintedText)
            paintTextInColor(g, a, fg, p0, p1);
    }

    final void paintTextInColor(Graphics g, Shape a, Color c, int startPosition, int endPosition) {
        // render the glyphs
        g.setColor(c);
        var painter = super.getGlyphPainter();
        painter.paint(this, g, a, startPosition, endPosition);

        // render underline or strikethrough if set.
        boolean underline = isUnderline();
        boolean strike = isStrikeThrough();
        if (underline || strike) {
            // calculate x coordinates
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a : a.getBounds();
            View parent = getParent();
            if ((parent != null) && (parent.getEndOffset() == endPosition)) {
                // strip whitespace on end
                Segment segment = getText(startPosition, endPosition);
                while (Character.isWhitespace(segment.last())) {
                    endPosition -= 1;
                    segment.count -= 1;
                }
            }
            int x0 = alloc.x;
            int p = getStartOffset();
            if (p != startPosition) {
                x0 += (int) painter.getSpan(this, p, startPosition, getTabExpander(), x0);
            }
            int x1 = x0 + (int) painter.getSpan(this, startPosition, endPosition, getTabExpander(), x0);

            // calculate y coordinate
            int y = alloc.y + (int)(painter.getHeight(this) - painter.getDescent(this));
            if (underline) {
                int yTmp = y + 1;
                g.drawLine(x0, yTmp, x1, yTmp);
            }
            if (strike) {
                // move y coordinate above baseline
                int yTmp = y - (int) (painter.getAscent(this) * 0.3f);
                g.drawLine(x0, yTmp, x1, yTmp);
            }

        }
    }
}