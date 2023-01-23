package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PageTableModel extends AbstractTableModel {

  private final List<AbstractPageModel> pages;

  public PageTableModel(List<AbstractPageModel> pages) {

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
      case 0 -> "Details";
      case 1 -> "Content";
      default -> "ERROR";
    };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Object value = "??";
    AbstractPageModel page = pages.get(rowIndex);
    switch (columnIndex) {
      //case 0 -> value = page.getSpeaker();
      //case 1 -> value = page.getContent();
      default -> value = page;
    }

    return value;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    Class<?> columnClass;
    switch (columnIndex) {
      default -> columnClass = AbstractPageModel.class;
    }
    return columnClass;
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
  public AbstractPageModel getPageAt(int row) {
    if (row >= 0 && row < pages.size()) {
      return pages.get(row);
    } else {
      return null;
    }
  }
}
