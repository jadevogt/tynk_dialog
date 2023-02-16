package best.tigers.tynkdialog.gui.view.components.neo;

import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;

import javax.swing.*;
import java.awt.event.*;

public class NeoFunctionCallDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField functionName;
    private JTextField functionArgument;
    private JLabel warningLabel;

    public NeoFunctionCallDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public NeoFunctionCallDialog(String functionName, String functionParams) {
        this();
        this.functionName.setText(functionName);
        this.functionArgument.setText(functionParams);
    }


    public static String[] promptForFunctionDetails(String functionName, String functionParams) {
        var prompt = new NeoFunctionCallDialog(functionName, functionParams);
        prompt.pack();
        prompt.setLocationRelativeTo(null);
        prompt.setVisible(true);
        return prompt.getValue();
    }

    public static String[] promptForFunctionDetails() {
        var prompt = new NeoFunctionCallDialog();
        prompt.pack();
        prompt.setLocationRelativeTo(null);
        prompt.setVisible(true);
        return prompt.getValue();
    }

    String[] getValue() {
        return new String[] {functionName.getText(), functionArgument.getText()};
    }

    private void onOK() {
        if (functionName != null && !functionName.getText().equals("") && functionArgument != null && !functionArgument.getText().equals("")) {
            dispose();
        }
        else {
            getToolkit().beep();
        }
    }

    private void onCancel() {
        functionName.setText(null);
        functionArgument.setText(null);
        dispose();
    }

    public static void main(String[] args) {
        NeoFunctionCallDialog dialog = new NeoFunctionCallDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


}
