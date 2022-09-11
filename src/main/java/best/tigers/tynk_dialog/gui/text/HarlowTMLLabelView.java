package best.tigers.tynk_dialog.gui.text;

import best.tigers.tynk_dialog.game.Constants;
import best.tigers.tynk_dialog.gui.Assets;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.View;
import java.awt.*;

public class HarlowTMLLabelView extends LabelView {
        public HarlowTMLLabelView(Element elem) {
            super(elem);
        }

        public void paint(Graphics g, Shape allocation) {
            super.paint(g, allocation);
            if (getAttributes().getAttribute(HarlowTMLDocument.ElementNameAttribute)!=null
                    && getAttributes().getAttribute(HarlowTMLDocument.ElementNameAttribute).equals(HarlowTMLDocument.DELAY_ELEMENT_NAME)) {
                System.out.println("h");
                //paintDelayIndicator(g, allocation, (int) getAttributes().getAttribute(HarlowTMLDocument.DELAY_MAGNITUDE_NAME));
            }
            if (getAttributes().getAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME)!=null)
                paintBehaviorIndicator(g, allocation, (Constants.Behavior) getAttributes().getAttribute(HarlowTMLDocument.BEHAVIOR_ATTRIBUTE_NAME));
        }

        public void paintDelayIndicator(Graphics g, Shape a, int delayMagnitude) {
            int y = (int) (a.getBounds().getY() + a.getBounds().getHeight());
            int x1 = (int) a.getBounds().getX();
            int x2 = (int) a.getBounds().getX() + a.getBounds().width;

            Color old = g.getColor();
            var g2 = (Graphics2D) g;
            g2.setColor(Constants.TextColor.YELLOW.toAWT());
            g2.drawImage(Assets.getInstance().getTimer(), x1, (int) a.getBounds().getY(), x2, y, null);
            g.setColor(old);
        }

        public void paintBehaviorIndicator(Graphics g, Shape a, Constants.Behavior tynkBehavior) {
            int y = (int) (a.getBounds().getY() + a.getBounds().getHeight());
            int x1 = (int) a.getBounds().getX();
            int x2 = (int) (a.getBounds().getX() + a.getBounds().getWidth());

            Color old = g.getColor();
            var g2 = (Graphics2D) g;
            g2.setColor(Constants.TextColor.WHITE.toAWT());
            g2.setStroke(new BasicStroke(1));
            for (int i = x1; i <= x2; i += 6) {
                    g.drawArc(i + 3, y - 3, 3, 3, 0, 180);
                    g.drawArc(i + 6, y - 3, 3, 3, 180, 181);
            }
            g.setColor(old);
        }

    }