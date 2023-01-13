package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.gui.model.AbstractModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FlatPageEditorView implements PageView, TObserver {
  private final FlatPageModel model;
  private final JPanel panel;
  private final JFrame frame;
  private final JLabel flatLabel;
  private final JTextField flatField;
  private final JButton saveButton;

  public FlatPageEditorView(FlatPageModel model) {
    this.model = model;
    panel = new JPanel();
    frame = new JFrame();
    
    flatLabel = new JLabel("Flat");
    flatField = new JTextField();
    
    saveButton = new JButton("Save");
    var layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
    panel.setLayout(layout);
    panel.add(flatLabel);
    panel.add(flatField);
    panel.add(saveButton);
    frame.add(panel);
  }

  public FlatPageEditorView init() {
    model.attachSubscriber(this);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        unsubscribe(model);
        super.windowClosing(e);
      }
    });
    frame.setTitle("FlatPage Editor (" + model.getFlat() + ")");
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.pack();
    update();
    return this;
  }

  public void setFlat(String newFlat) {
    flatField.setText(newFlat);
  }

  public String getFlat() {
    return flatField.getText();
  }

  @Override
  public void update() {
    frame.setTitle("FlatPage Editor (" + model.getFlat() + ")");
    setFlat(model.getFlat());
  }

  @Override
  public void attachSaveAction(Runnable action) {
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    saveButton.addActionListener(actionInstance);
  }

  @Override
  public void attachContinueAction(Runnable action) {
  }

  @Override
  public JPanel getPanel() {
    return panel;
  }

  @Override
  public JFrame getFrame() {
    return frame;
  }
}
