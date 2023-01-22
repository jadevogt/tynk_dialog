package best.tigers.tynkdialog.gui.view.components;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class ScrollingListEditor<T> extends JPanel {

  private final JScrollPane scrollPane = new JScrollPane();
  private final JPanel buttonPanel = new JPanel();
  private final JButton addResponseButton = new JButton("add");
  private final JButton deleteResponseButton = new JButton("remove");
  private final JButton moveUpResponseButton = new JButton("up");
  private final JButton moveDownResponseButton = new JButton("down");
  private JList<T> list;


  public ScrollingListEditor(ListModel<T> listModel) {
    super();
    list = new JList<>(listModel);
    scrollPane.setViewportView(this.list);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(scrollPane);

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(addResponseButton);
    buttonPanel.add(deleteResponseButton);
    buttonPanel.add(moveUpResponseButton);
    buttonPanel.add(moveDownResponseButton);
    add(buttonPanel);
  }

  public ScrollingListEditor(ListModel<T> listModel, ListCellRenderer<T> renderer) {
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