package best.tigers.tynkdialog.gui.view.page.neo;

import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.gui.controller.GenericListController;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.view.components.neo.NeoScrollingListEditor;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NeoBranchPageEditorView extends AbstractPageEditorView implements ListDataListener {
    private final BranchPageModel model;
    private JTextField result;
    private JButton saveButton;
    private JPanel rootPane;
    private JComboBox<BranchPage.Leaf> leafComboBox;

    private GenericListController<BranchRequirementModel> branchListController;
    private GenericListModel<BranchRequirementModel> requirementListModel;
    private JPanel requirementEditorPanel;
    private NeoScrollingListEditor<BranchRequirementModel> requirementListEditor;
    @Getter
    @Setter
    private NeoBranchRequirementEditorView currentEditorView = null;

    public NeoBranchPageEditorView(AbstractPageModel pageModel) {
        super();
        model = (BranchPageModel) pageModel;
        $$$setupUI$$$();
        var panel = rootPane;
        var frame = getFrame();
        getFrame().add(panel);
        leafComboBox.addItem(BranchPage.Leaf.ADD);
        leafComboBox.addItem(BranchPage.Leaf.JUMP);
        var requirementModels = model.getPage()
                .getRequirements()
                .stream()
                .map(BranchRequirementModel::new)
                .toList();

        requirementListModel = new GenericListModel<>(requirementModels);
        requirementListEditor.setList(requirementListModel);
        requirementListModel.addListDataListener(this);

        frame.pack();
        frame.setVisible(true);
        setEditorPaneModel(null);
        frame.pack();
        frame.setTitle("BranchPage Editor");
    }


    @Override
    public BranchPage asPage() {
        if (getCurrentEditorView() != null) {
            getCurrentEditorView().saveChanges();
        }
        var listModel = requirementListModel;
        var newLeaf = (BranchPage.Leaf) leafComboBox.getSelectedItem();
        return new BranchPage(
                newLeaf,
                result.getText(),
                listModel.getContent().stream().map(BranchRequirementModel::getRequirement).toList()
        );
    }

    @Override
    public AbstractPageModel getModel() {
        return model;
    }

    public void setEditorPaneModel(BranchRequirementModel requirementModel) {
        var newRequirementModel = requirementListEditor.getList().getSelectedValue();
        var requirementPanel = requirementEditorPanel;
        var editorView = currentEditorView;
        requirementPanel.removeAll();
        if (editorView != null) {
            editorView.unsubscribe(editorView.getModel());
        }
        if (newRequirementModel != null) {
            var newView = new NeoBranchRequirementEditorView(newRequirementModel, requirementListModel);
            setCurrentEditorView(newView);
            requirementPanel.add(newView.getRootPanel());
            requirementPanel.repaint();
            getFrame().pack();
        } else {
            var newView = NeoBranchRequirementEditorView.nullView();
            setCurrentEditorView(newView);
            requirementPanel.add(newView.getRootPanel());
            requirementPanel.repaint();
            getFrame().pack();
        }
    }

    @Override
    public void update() {
        super.update();
        leafComboBox.setSelectedItem(model.getLeaf());
        result.setText(model.getBranchResult());

        var requirementModels = model.getPage()
                .getRequirements()
                .stream()
                .map(BranchRequirementModel::new)
                .toList();

        requirementListModel.removeListDataListener(this);
        requirementListModel = new GenericListModel<>(requirementModels);
        requirementListModel.addListDataListener(this);
        requirementListEditor.setList(requirementListModel);

        branchListController = new GenericListController<>(requirementListModel);
        requirementListEditor.setAddAction(() -> branchListController.addChoice(new BranchRequirementModel(new BranchRequirement())));
        requirementListEditor.setDeleteItemAction((indexConsumer) -> branchListController.deleteChoice(indexConsumer, () -> {
        }));
        requirementListEditor.setMoveUpAction(branchListController::moveUp);
        requirementListEditor.setMoveDownAction(branchListController::moveDown);
        requirementListEditor.setListSelectionAction(this::setEditorPaneModel);
    }

    @Override
    public void setupSaveActions() {
        saveButton.addActionListener(getSaveAction());
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
        requirementListEditor.getList().revalidate();
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        requirementListEditor.getList().revalidate();
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        requirementListEditor.getList().revalidate();
    }

    private void createUIComponents() {
        requirementListEditor = new NeoScrollingListEditor<>();
        requirementEditorPanel = new JPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPane = new JPanel();
        rootPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(10, 10, 10, 10), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Leaf");
        rootPane.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(73, 16), null, 0, false));
        leafComboBox = new JComboBox();
        rootPane.add(leafComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(229, 30), null, 0, false));
        result = new JTextField();
        rootPane.add(result, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(229, 30), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Result");
        rootPane.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(73, 16), null, 0, false));
        rootPane.add(requirementEditorPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        requirementEditorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Requirement", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        rootPane.add(requirementListEditor.$$$getRootComponent$$$(), new com.intellij.uiDesigner.core.GridConstraints(2, 0, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save Changes (Shift + Enter)");
        rootPane.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(396, 30), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }
}
