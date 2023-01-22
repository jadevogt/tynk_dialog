package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.model.ResponseChoiceListModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.gui.view.components.SuperTextDisplayPane;
import best.tigers.tynkdialog.util.Assets;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class ChoiceResponseEditorView extends JPanel implements TObserver {
  private final ChoiceResponseModel model;
  private final SuperTextDisplayPane superTextDisplayPane = new SuperTextDisplayPane(1);
  private final JComboBox<ChoiceResponse.ResponseIcon> iconJComboBox = new JComboBox<>();
  private final JTextField resultField = new JTextField();
  private final ResponseChoiceListModel listModel;

  public ChoiceResponseEditorView(ChoiceResponseModel model, ResponseChoiceListModel listModel) {
    super();
    this.listModel = listModel;
    this.model = model;
    this.model.attachSubscriber(this);
    //setPreferredSize(new Dimension(350, 350));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    superTextDisplayPane.setFont(Assets.getLittle().deriveFont(12.0F));
    add(superTextDisplayPane);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.NEUTRAL);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.ACT);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.CANCEL);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.GIFT);
    iconJComboBox.addItem(ChoiceResponse.ResponseIcon.SPEAK);
    add(iconJComboBox);
    add(resultField);
    superTextDisplayPane.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusLost(e);
        saveChanges();
      }
    });
    resultField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        super.focusGained(e);
        saveChanges();
      }
    });
    iconJComboBox.addActionListener((e) -> saveChanges());
    update();
  }

  public ChoiceResponseModel getModel() {
    return model;
  }

  @Override
  public void update() {
    superTextDisplayPane.setText(model.getContent());
    iconJComboBox.setSelectedItem(model.getIcon());
    resultField.setText(model.getResult());
    listModel.update();
  }

  public void saveChanges() {
    var newText = superTextDisplayPane.getText();
    var newIcon = (ChoiceResponse.ResponseIcon) iconJComboBox.getSelectedItem();
    var newResult = resultField.getText();
    if (!Objects.equals(model.getContent(), superTextDisplayPane.getText())) {
    model.setContent(newText);
    }
    if (model.getIcon() != newIcon) {
      model.setIcon(newIcon);
    }
    if (!Objects.equals(model.getResponse(), resultField.getText())) {
      model.setChoiceResult(newResult);
    }
  }

}
