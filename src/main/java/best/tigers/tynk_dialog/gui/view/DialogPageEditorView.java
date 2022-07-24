package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.model.DialogPageModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public class DialogPageEditorView implements Observer, DialogPageView {
    private DialogPageModel model;
    private JPanel panel;
    private JFrame frame;
    private JTextField characterField;
    private JTextArea contentField;
    private JButton saveButton;
    final private static Dimension PREFERRED_SIZE = new Dimension(300, 200);

    public void makeTrans() {
        saveButton.addActionListener(e->{
            frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING));
        });
    }
    public DialogPageEditorView(DialogPageModel model) {
        this.model = model;
        this.model.attachSubscriber(this);
        JFrame.setDefaultLookAndFeelDecorated(true);
        panel = new JPanel();
        frame = new JFrame();

        var characterLabel = new JLabel("Character");
        characterLabel.setHorizontalAlignment(JLabel.LEFT);
        characterField = new JTextField();
        characterField.setColumns(20);

        var contentLabel = new JLabel("Content");
        contentLabel.setHorizontalAlignment(JLabel.LEFT);
        contentField = new JTextArea();
        contentField.setColumns(20);

        saveButton = new JButton("Save Changes");
        var layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(characterLabel)
                        .addComponent(contentLabel))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(characterField)
                        .addComponent(contentField)))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)));
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(characterLabel)
                        .addComponent(characterField))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(contentLabel)
                        .addComponent(contentField))
                    .addComponent(saveButton)
        );

        panel.setLayout(layout);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                unsubscribe(model);
                super.windowClosing(e);
            }
        });
    }

    public void init() {
        panel.setPreferredSize(PREFERRED_SIZE);
        frame.setPreferredSize(PREFERRED_SIZE);
        frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void update() {
        frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
        characterField.setText(model.getSpeaker());
        contentField.setText(model.getContent());
    }

    public void attachSaveFunction(ActionListener al) {
        saveButton.addActionListener(al);
    }

    @Override
    public String getSpeaker() {
        return characterField.getText();
    }

    @Override
    public String getContent() {
        return contentField.getText();
    }
}