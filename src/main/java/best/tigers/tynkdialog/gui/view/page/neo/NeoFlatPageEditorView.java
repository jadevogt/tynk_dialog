package best.tigers.tynkdialog.gui.view.page.neo;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.FlatPage;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NeoFlatPageEditorView extends AbstractPageEditorView {
  private JButton saveChangesButton;
  private JTextField flatField;
  private JPanel rootPanel;
  private FlatPageModel model;

  public NeoFlatPageEditorView(FlatPageModel model) {
    super();
    this.model = model;
  }

  @Override
  public void init() {
    getModel().attachSubscriber(this);
    var frame = getFrame();
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        unsubscribe(getModel());
        super.windowClosing(e);
      }
    });
    getFrame().add(rootPanel);
    getFrame().setLocationRelativeTo(null);
    getFrame().setVisible(true);
    getFrame().pack();
    setupSaveActions();
    update();
  }

  @Override
  public AbstractPage asPage() {
    return new FlatPage(flatField.getText());
  }

  @Override
  public AbstractPageModel getModel() {
    return model;
  }

  @Override
  public void setupSaveActions() {
    saveChangesButton.addActionListener(getSaveAction());
  }
}
