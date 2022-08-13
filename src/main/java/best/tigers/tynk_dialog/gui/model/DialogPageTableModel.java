package best.tigers.tynk_dialog.gui.model;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class DialogPageTableModel extends AbstractTableModel {

    private List<DialogPageModel> pages;

    public DialogPageTableModel(List<DialogPageModel> pages) {

        this.pages = new ArrayList<DialogPageModel>(pages);
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
        switch(columnIndex) {
            case 0:
                return "Character";
            case 1:
                return "Text";
        }
        return "ERROR";
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
        if (row >= 0 && row < pages.size())
            return pages.get(row);
        else {
            return null;
        }
    }

}

