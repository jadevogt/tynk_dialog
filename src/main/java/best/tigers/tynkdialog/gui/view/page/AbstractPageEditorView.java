package best.tigers.tynkdialog.gui.view.page;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.ShortcutSupport;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.gui.view.components.DynamicFrame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import lombok.Getter;

public abstract class AbstractPageEditorView implements TObserver, ShortcutSupport {

  @Getter
  private final JPanel panel;
  @Getter
  private final JFrame frame;
  @Getter
  private AbstractAction saveAction;
  @Getter
  private AbstractAction continueAction;

  public abstract AbstractPage asPage();

  public AbstractPageEditorView() {
    panel = new JPanel();
    frame = new DynamicFrame();
    frame.add(panel);
  }

  public void close() {
    panel.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
    frame.dispose();
  }

  abstract AbstractPageModel getModel();

  public void init() {
    getModel().attachSubscriber(this);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        unsubscribe(getModel());
        super.windowClosing(e);
      }
    });
    getFrame().setLocationRelativeTo(null);
    getFrame().setVisible(true);
    getFrame().pack();
    setupSaveActions();
    update();
  }

  public InputMap getInputMap() {
    return panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  public ActionMap getActionMap() {
    return panel.getActionMap();
  }

  public void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      AbstractAction action) {
    getInputMap().put(keyStroke, actionMapKey);
    getActionMap().put(actionMapKey, action);
  }

  public void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      Runnable action) {
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    attachKeyboardShortcut(keyStroke, actionMapKey, actionInstance);
  }

  public void attachSaveAction(Runnable action) {
    saveAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
  }

  public void attachContinueAction(Runnable action) {
    continueAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
  }

  public abstract void setupSaveActions();

  JTextField createField() {
    var field = new JTextField();
    field.setColumns(40);
    return field;
  }

  JLabel createLabel(String text) {
    var label = new JLabel(text);
    label.setHorizontalAlignment(JLabel.LEFT);
    return label;
  }

  JToolBar createToolbar() {
    var toolbar = new JToolBar();
    toolbar.setFloatable(false);
    return toolbar;
  }

  GroupLayout createGroupLayout() {
    GroupLayout layout = new GroupLayout(getPanel());
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    return layout;
  }

  @Override
  public void update() {
    var model = getModel();
    if (model.isDeleted()) {
      frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
    }
  }
}
