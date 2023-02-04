package best.tigers.tynkdialog.gui.view.components.neo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NeoScrollingListEditor<T> {
    private JPanel rootPanel;
    private JList<T> list;
    private JButton addResponseButton;
    private JButton moveDownResponseButton;
    private JButton deleteResponseButton;
    private JButton moveUpResponseButton;
    private JScrollPane scrollPane;
    private JToolBar controlStrip;

    public NeoScrollingListEditor(ListModel<T> listModel) {
        setList(listModel);
    }

    public NeoScrollingListEditor() {
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public NeoScrollingListEditor(ListModel<T> listModel, ListCellRenderer<T> renderer) {
        this(listModel);
        list.setCellRenderer(renderer);
    }

    public void setAddAction(Runnable r) {
        addResponseButton.addActionListener(e -> r.run());
    }

    public void setDeleteItemAction(Consumer<Integer> indexConsumer) {
        deleteResponseButton.addActionListener(e -> indexConsumer.accept(list.getSelectedIndex()));
    }

    public void setMoveUpAction(BiConsumer<Integer, Consumer<int[]>> indexConsumer) {
        moveUpResponseButton.addActionListener(
                e -> indexConsumer.accept(list.getSelectedIndex(), list::setSelectedIndices));
    }

    public void setMoveDownAction(BiConsumer<Integer, Consumer<int[]>> indexConsumer) {
        moveDownResponseButton.addActionListener(
                e -> indexConsumer.accept(list.getSelectedIndex(), list::setSelectedIndices));
    }

    public JList<T> getList() {
        return list;
    }

    public void setList(ListModel<T> newListModel) {
        list.setModel(newListModel);
    }

    public void setListSelectionAction(Consumer<T> valueConsumer) {
        list.addListSelectionListener(e -> valueConsumer.accept(list.getSelectedValue()));
    }
}
