package best.tigers.tatffdialogutility.previewmode;

import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class OutputWindow {
  private final JFrame frame;
  private final JPanel panel;
  private int count;
  private BufferedImage displayedImage;
  ActionListener listener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      panel.repaint();
    }
  };

  public OutputWindow(BufferedImage initialImage) {
    displayedImage = initialImage;
    frame = new JFrame();
    var timer = new Timer(16, listener);
    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        setDoubleBuffered(true);
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawRect(0, 0, displayedImage.getWidth() * 4, displayedImage.getHeight() * 4);
        g2.drawImage(displayedImage, 0, 0, displayedImage.getWidth() * 4, displayedImage.getHeight() * 4, this);
      }
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(displayedImage.getWidth() * 4, displayedImage.getHeight() * 4);
      }
    };
    timer.start();
    frame.setTitle("Dialog Timing Preview");
    frame.getContentPane().add(panel);
    frame.setResizable(false);
    frame.pack();
    frame.setVisible(true);
  }

  public void setDisplayedImage(BufferedImage displayedImage) {
    this.displayedImage = displayedImage;
  }

  public JPanel getPanel() {
    return panel;
  }
}
