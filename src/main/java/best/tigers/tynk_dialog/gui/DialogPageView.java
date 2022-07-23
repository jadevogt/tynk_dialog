package best.tigers.tynk_dialog.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogPageView extends JFrame {
    final private DialogPageViewPanel panel;
    public DialogPageView() {
        var containerPanel = new JPanel();
        containerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel = new DialogPageViewPanel();
        setTitle("Edit DialogPage");
        containerPanel.add(panel);
        add(containerPanel);
        pack();

    }

    public DialogPageViewPanel getPanel() {
        return panel;
    }
}

class DialogPageViewPanel extends JPanel implements Observer {
    private final JTextField speakerText;
    private final JTextArea contentText;
    private final JTextField blipText;
    private final JTextField textboxStyleText;
    private DialogPageModel observedModel;

    @Override
    public void update() {
        speakerText.setText(observedModel.getSpeaker());
        contentText.setText(observedModel.getContent());
        blipText.setText(observedModel.getBlip());
        textboxStyleText.setText(observedModel.getBoxStyle());
    }

    public DialogPageViewPanel() {
        var layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);
        speakerText = new JTextField();
        speakerText.setColumns(10);

        contentText = new JTextArea();
        contentText.setColumns(10);
        contentText.setRows(5);
        contentText.setLineWrap(true);

        blipText = new JTextField();
        blipText.setColumns(10);

        textboxStyleText = new JTextField();
        textboxStyleText.setColumns(10);

        var saveButton = new JButton("Save Changes");
        saveButton.addActionListener((event)->{
            System.out.println("saved");
        });

        Border etched = BorderFactory.createEtchedBorder();
        Border styleBorder = BorderFactory.createTitledBorder(etched, "Style Options");
        JPanel styleArea = new JPanel();
        styleArea.setLayout(new GridLayout(1, 2));
        styleArea.setBorder(styleBorder);

        add(speakerText, BorderLayout.NORTH);
        add(contentText, BorderLayout.CENTER);
        styleArea.add(blipText);
        styleArea.add(textboxStyleText);
        styleArea.add(saveButton);
        add(styleArea, BorderLayout.SOUTH);
    }

    public DialogPageViewPanel(DialogPageModel displayPage) {
        this();
        displayPage.attachListener(this);
        observedModel = displayPage;
        update();
    }

    public void setModel(DialogPageModel displayPage) {
        detach();
        displayPage.attachListener(this);
        observedModel = displayPage;
    }

    public void setSpeaker(String newSpeaker) {
        speakerText.setText(newSpeaker);
    }

    public String getSpeaker() {
        return speakerText.getText();
    }

    public void setContent(String newContent) {
        contentText.setText(newContent);
    }

    public String getContent() {
        return contentText.getText();
    }

    public void setBlip(String newBlip) {
        blipText.setText(newBlip);
    }

    public String getBlip() {
        return blipText.getText();
    }

    public void setStyle(String newStyle) {
        textboxStyleText.setText(newStyle);
    }

    public String getStyle() {
        return textboxStyleText.getText();
    }

    public void detach() {
        if (observedModel != null) {
            observedModel.removeListener(this);
        }
    }
}
