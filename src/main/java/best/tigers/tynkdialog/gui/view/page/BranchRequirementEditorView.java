package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.BranchRequirement.Category;
import best.tigers.tynkdialog.game.page.BranchRequirement.Comparison;
import best.tigers.tynkdialog.game.page.BranchRequirement.ValueType;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.SuperTextEditorPane;
import best.tigers.tynkdialog.gui.view.components.SuperTextToolbar;
import best.tigers.tynkdialog.util.Assets;
import best.tigers.tynkdialog.util.Log;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BranchRequirementEditorView extends JPanel implements TObserver {

  private final BranchRequirementModel model;
  private final JComboBox<BranchRequirement.Category> categoryJComboBox = new JComboBox<>();
  private final JComboBox<BranchRequirement.Comparison> comparisonJComboBox = new JComboBox<>();
  private final JComboBox<BranchRequirement.ValueType> valueTypeJComboBox = new JComboBox<>();
  private final LabeledField flag = new LabeledField("Flag");
  private final LabeledField value = new LabeledField("Value");
  private final GenericListModel<BranchRequirementModel> listModel;



  public BranchRequirementEditorView(BranchRequirementModel model,
      GenericListModel<BranchRequirementModel> listModel) {
    super();
    this.listModel = listModel;
    this.model = model;
    this.model.attachSubscriber(this);

    Arrays.stream(Category.values()).forEach(categoryJComboBox::addItem);
    Arrays.stream(Comparison.values()).forEach(comparisonJComboBox::addItem);
    Arrays.stream(ValueType.values()).forEach(valueTypeJComboBox::addItem);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    var subPanelFlag = new JPanel();
    subPanelFlag.add(flag.getLabel());
    subPanelFlag.add(flag.getField());
    var subPanelValue = new JPanel();
    subPanelValue.add(value.getLabel());
    subPanelValue.add(value.getField());

    add(subPanelFlag);
    add(categoryJComboBox);
    add(comparisonJComboBox);
    add(subPanelValue);
    add(valueTypeJComboBox);

    categoryJComboBox.setSelectedItem(model.getCategory());
    valueTypeJComboBox.setSelectedItem(model.getValueType());
    comparisonJComboBox.setSelectedItem(model.getComparison());
    flag.getField().setText(model.getFlag());
    value.getField().setText(model.getValue());

    flag.getField().addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });

    value.getField().addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });

    categoryJComboBox.addActionListener((e) -> saveChanges());
    comparisonJComboBox.addActionListener((e) -> saveChanges());
    valueTypeJComboBox.addActionListener((e) -> saveChanges());
  }

  public BranchRequirementModel getModel() {
    return model;
  }

  @Override
  public void update() {
    flag.getField().setText(model.getFlag());
    categoryJComboBox.setSelectedItem(model.getCategory());
    comparisonJComboBox.setSelectedItem(model.getComparison());
    value.setText(model.getValue());
    valueTypeJComboBox.setSelectedItem(model.getValueType());
  }

  public void saveChanges() {
    var newFlag = flag.getText();
    var newCategory = ((Category) categoryJComboBox.getSelectedItem());
    var newComparison = ((Comparison) comparisonJComboBox.getSelectedItem());
    var newValue = value.getText();
    var newValueType = ((ValueType) valueTypeJComboBox.getSelectedItem());

    model.setFlag(newFlag);
    model.setCategory(newCategory);
    model.setComparison(newComparison);
    model.setValue(newValue);
    model.setValueType(newValueType);

    model.notifySubscribers();
    listModel.notifyListeners();
  }
}
