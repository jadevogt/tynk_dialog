package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.gui.view.components.SuperTextEditorPane;
import best.tigers.tynkdialog.gui.view.components.SuperTextToolbar;
import best.tigers.tynkdialog.util.Assets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChoiceResponseEditorView extends JPanel implements TObserver {

  private final ChoiceResponseModel model;
  private final SuperTextEditorPane superTextEditorPane = new SuperTextEditorPane(1);
  private final JComboBox<ChoiceResponse.ResponseIcon> iconJComboBox = new JComboBox<>();
  private final JTextField resultField = new JTextField();
  private final GenericListModel<ChoiceResponseModel> listModel;

  public ChoiceResponseEditorView(ChoiceResponseModel model,
      GenericListModel<ChoiceResponseModel> listModel) {
    super();
    this.listModel = listModel;
    this.model = model;
    this.model.attachSubscriber(this);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    var toolbar = new SuperTextToolbar(superTextEditorPane, () -> {
    });
    superTextEditorPane.setFont(Assets.getLittle().deriveFont(12.0F));
    add(toolbar);
    add(superTextEditorPane);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.NEUTRAL);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.ACT);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.CANCEL);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.GIFT);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.SPEAK);
    add(iconJComboBox);
    add(resultField);

    superTextEditorPane.setText(model.getContent());
    iconJComboBox.setSelectedItem(model.getIcon());
    resultField.setText(model.getChoiceResult());
    superTextEditorPane.addFocusListener(new FocusAdapter() {

      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });
    resultField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });
    iconJComboBox.addActionListener((e) -> saveChanges());
  }

  public ChoiceResponseModel getModel() {
    return model;
  }

  @Override
  public void update() {
    superTextEditorPane.setText(model.getContent());
    iconJComboBox.setSelectedItem(model.getIcon());
    resultField.setText(model.getChoiceResult());
  }

  public void saveChanges() {
    var newText = superTextEditorPane.getText();
    var newIcon = (ChoiceResponse.ResponseIcon) iconJComboBox.getSelectedItem();
    var newResult = resultField.getText();
    if (!Objects.equals(model.getContent(), superTextEditorPane.getText())) {
      model.setContent(newText);
    }
    if (model.getIcon() != newIcon) {
      model.setIcon(newIcon);
    }
    if (!Objects.equals(model.getChoiceResult(), resultField.getText())) {
      model.setChoiceResult(newResult);
    }
    listModel.notifyListeners();
  }
}
