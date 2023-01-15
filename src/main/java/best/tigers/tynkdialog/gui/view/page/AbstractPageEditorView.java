package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.ShortcutSupport;
import best.tigers.tynkdialog.gui.view.TObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public abstract class AbstractPageEditorView implements TObserver, ShortcutSupport {
  private AbstractAction saveAction;
  private AbstractAction continueAction;
  private final JPanel panel;
  private final JFrame frame;

  public AbstractPageEditorView() {
    panel = new JPanel();
    frame = new JFrame();
    frame.add(panel);
  }

  JPanel getPanel() {
    return panel;
  }

  JFrame getFrame() {
    return frame;
  }



  public void close() {
    panel.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
    frame.dispose();  };

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
  };

  AbstractAction getSaveAction() {
    return saveAction;
  }

  public AbstractAction getContinueAction() {
    return continueAction;
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

  GroupLayout createGroupLayout(JPanel panel) {
    GroupLayout layout = new GroupLayout(getPanel());
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    return layout;
  }
}
