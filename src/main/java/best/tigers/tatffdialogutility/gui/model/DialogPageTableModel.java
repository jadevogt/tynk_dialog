package best.tigers.tatffdialogutility.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DialogPageTableModel extends AbstractTableModel {

  private final List<DialogPageModel> pages;

  public DialogPageTableModel(List<DialogPageModel> pages) {

    this.pages = new ArrayList<>(pages);
  }

  @Override
  public int getRowCount() {
    return pages.size();
  }

  @Override
  public int getColumnCount() {
    return 2;
  }

  @Override
  public String getColumnName(int column) {
    return switch (column) {
      case 0 -> "Character";
      case 1 -> "Text";
      default -> throw new IllegalStateException("Unexpected value: " + column);
    };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Object value = "??";
    DialogPageModel page = pages.get(rowIndex);
    switch (columnIndex) {
      case 0:
        value = page.getSpeaker();
        break;
      case 1:
        value = page.getContent();
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }

    return value;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  /* Override this if you want the values to be editable...
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      //....
  }
  */

  /*
   * This will return the user at the specified row...
   * @param row
   * @return DialogPageModel
   */
  public DialogPageModel getPageAt(int row) {
    if (row >= 0 && row < pages.size()) {
      return pages.get(row);
    }
    return null;
  }
}
