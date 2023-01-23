package best.tigers.tynkdialog.gui.controller;

import best.tigers.tynkdialog.gui.model.GenericListModel;
import java.awt.Toolkit;
import java.util.function.Consumer;

public class GenericListController<T> {

  private final GenericListModel<T> model;

  public GenericListController(GenericListModel<T> model) {
    this.model = model;
  }

  public void addChoice(T newItem) {
    model.addItem(newItem);
  }

  public void deleteChoice(Integer selectedIndex, Runnable onDelete) {
    if (selectedIndex > -1) {
      onDelete.run();
      var value = model.getElementAt(selectedIndex);
      model.deleteResponse(value);
      model.notifyListeners();
    }
  }

  public void moveUp(Integer selectedIndex, Consumer<int[]> setIndices) {
    if (selectedIndex > 0) {
      model.swapListItems(selectedIndex, selectedIndex - 1);
      setIndices.accept(new int[]{selectedIndex - 1});
      model.notifyListeners();
    } else {
      Toolkit.getDefaultToolkit().beep();
    }
  }

  public void moveDown(Integer selectedIndex, Consumer<int[]> setIndices) {
    var size = model.getSize();
    if (selectedIndex < size - 1 && selectedIndex > -1) {
      model.swapListItems(selectedIndex, selectedIndex + 1);
      setIndices.accept(new int[]{selectedIndex + 1});
      model.notifyListeners();
    } else {
      Toolkit.getDefaultToolkit().beep();
    }
  }
}
