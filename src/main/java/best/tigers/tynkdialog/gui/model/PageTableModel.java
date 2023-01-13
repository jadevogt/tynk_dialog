package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PageTableModel extends AbstractTableModel {

  private final List<TalkPageModel> pages;

  public PageTableModel(List<TalkPageModel> pages) {

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
  public String getColumnName(int columnIndex) {
    return switch (columnIndex) {
      case 0 -> "Character";
      case 1 -> "Text";
      default -> "ERROR";
    };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Object value = "??";
    TalkPageModel page = pages.get(rowIndex);
    switch (columnIndex) {
      case 0 -> value = page.getSpeaker();
      case 1 -> value = page.getContent();
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
  public TalkPageModel getPageAt(int row) {
    if (row >= 0 && row < pages.size()) {
      return pages.get(row);
    } else {
      return null;
    }
  }
}
