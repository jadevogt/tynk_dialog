package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
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

    var layout = createGroupLayout(getPanel());

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

  public String getFlat() {
    return flatField.getText();
  }

  public void setFlat(String newFlat) {
    flatField.setText(newFlat);
  }

  @Override
  public void update() {
    getFrame().setTitle("FlatPage Editor (" + model.getFlat() + ")");
    setFlat(model.getFlat());
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
  }

}
