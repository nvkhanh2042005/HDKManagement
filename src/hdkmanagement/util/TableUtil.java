// util/TableUtil.java
package hdkmanagement.util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.Vector;

public class TableUtil {
    
    // Xóa tất cả dòng trong table
    public static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    
    // Thêm dòng vào table
    public static void addRow(JTable table, Object[] rowData) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(rowData);
    }
    
    // Xóa dòng được chọn
    public static void deleteSelectedRow(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);
        }
    }
    
    // Lấy dữ liệu dòng được chọn
    public static Object[] getSelectedRowData(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Vector rowData = (Vector) model.getDataVector().get(selectedRow);
            return rowData.toArray();
        }
        return null;
    }
    
    // Tự động resize cột
    public static void autoResizeColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(100);
        }
    }
    
    // Đặt kích thước cột
    public static void setColumnWidth(JTable table, int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
    }
    
    // Đặt kích thước cho tất cả cột
    public static void setAllColumnWidth(JTable table, int width) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            setColumnWidth(table, i, width);
        }
    }
}