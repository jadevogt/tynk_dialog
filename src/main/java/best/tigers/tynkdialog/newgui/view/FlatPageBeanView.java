package best.tigers.tynkdialog.newgui.view;

import best.tigers.tynkdialog.newgui.model.FlatPageBean;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FlatPageBeanView implements PropertyChangeListener {
    private JTextField flatField;
    private JButton doneButton;
    private JPanel panel;
    private FlatPageBean pageBean;

    public FlatPageBeanView(FlatPageBean bean)  {
        this.pageBean = bean;
        bean.addPropertyChangeListener(this);
        flatField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bean.setFlat(flatField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bean.setFlat(flatField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                bean.setFlat(flatField.getText());
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public void detach() {
        pageBean.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof FlatPageBean sourceBean && evt.getPropertyName().equals("flat")) {
            var newFlat = sourceBean.getFlat();
            if (!flatField.getText().equals(newFlat)) {
                flatField.setText(newFlat);
            }
        }
    }
}
