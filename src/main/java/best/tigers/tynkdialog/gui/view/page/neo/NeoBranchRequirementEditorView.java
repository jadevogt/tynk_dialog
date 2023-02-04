package best.tigers.tynkdialog.gui.view.page.neo;

import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.BranchRequirement.Category;
import best.tigers.tynkdialog.game.page.BranchRequirement.Comparison;
import best.tigers.tynkdialog.game.page.BranchRequirement.ValueType;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NeoBranchRequirementEditorView implements TObserver {
  private final BranchRequirementModel model;
  private final GenericListModel<BranchRequirementModel> listModel;
  private JTextField flag;
  private JComboBox comparisonJComboBox;
  private JTextField value;
  private JComboBox valueTypeJComboBox;
  private JComboBox categoryJComboBox;
  private JPanel rootPanel;

  public NeoBranchRequirementEditorView(BranchRequirementModel model,
      GenericListModel<BranchRequirementModel> listModel) {
    super();
    this.listModel = listModel;
    this.model = model;
    this.model.attachSubscriber(this);

    Arrays.stream(Category.values()).forEach(categoryJComboBox::addItem);
    Arrays.stream(Comparison.values()).forEach(comparisonJComboBox::addItem);
    Arrays.stream(ValueType.values()).forEach(valueTypeJComboBox::addItem);

    categoryJComboBox.setSelectedItem(model.getCategory());
    valueTypeJComboBox.setSelectedItem(model.getValueType());
    comparisonJComboBox.setSelectedItem(model.getComparison());
    flag.setText(model.getFlag());
    value.setText(model.getValue());

    flag.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });

    value.addFocusListener(new FocusAdapter() {
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

  public static NeoBranchRequirementEditorView nullView() {
    var nullView = new NeoBranchRequirementEditorView(new BranchRequirementModel(new BranchRequirement()), new GenericListModel<>());
    nullView.categoryJComboBox.setEnabled(false);
    nullView.flag.setEnabled(false);
    nullView.valueTypeJComboBox.setEnabled(false);
    nullView.comparisonJComboBox.setEnabled(false);
    nullView.value.setEnabled(false);
    return nullView;
  }

  public BranchRequirementModel getModel() {
    return model;
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  @Override
  public void update() {
    flag.setText(model.getFlag());
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
