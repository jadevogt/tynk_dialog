package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.FlatPage;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import lombok.Getter;

public class FlatPageEditorView extends AbstractPageEditorView {

  @Getter
  private final FlatPageModel model;
  private final JTextField flatField;
  private final JButton saveButton;

  public FlatPageEditorView(FlatPageModel model) {
    super();
    this.model = model;

    var flatLabel = createLabel("Flat");
    flatField = createField();
    saveButton = new JButton("Save Changes (Shift + Enter)");

    var layout = createGroupLayout();

    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addComponent(flatLabel)
                .addComponent(flatField, 10, 20, 999))
            .addComponent(saveButton)
    );
    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(flatLabel)
                .addComponent(flatField, flatField.getPreferredSize().height,
                    flatField.getPreferredSize().height, flatField.getPreferredSize().height))
            .addComponent(saveButton)
    );
    getPanel().setLayout(layout);
  }

  @Override
  public void update() {
    super.update();
    getFrame().setTitle("FlatPage Editor (" + model.getFlat() + ")");
    flatField.setText(model.getFlat());
  }

  @Override
  public AbstractPage asPage() {
    return new FlatPage(flatField.getText());
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
  }

}
