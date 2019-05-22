package view.standalone;

import model.dto.FacultyDTO;
import model.dto.SpecialtyDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class FieldsOfStudyTable extends JTable{

    private static final int WIDTH = 450;
    private static final int HEIGHT = 100;

    private static final int FACULTY_COLUMN_NUMBER = 0;
    private static final int SPECIALTY_COLUMN_NUMBER = 4;

    private DefaultTableModel tableModel;

    private final static String[] COLUMN_NAMES = {
            "Wydział",
            "Kierunek",
            "Stopien",
            "Tryb",
            "Specjalności"
    };

    public FieldsOfStudyTable() {
        super();
        initModel();
        initTable();
        initColumns();
    }

    private void initModel() {
        tableModel = new DefaultTableModel(null, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(tableModel);
    }

    private void initTable() {
        setPreferredScrollableViewportSize(new Dimension(WIDTH, HEIGHT));
        setFillsViewportHeight(true);
    }

    private void initColumns() {
        TableColumn facultyColumn = getColumnModel().getColumn(FACULTY_COLUMN_NUMBER);
        facultyColumn.setCellRenderer(new FacultyCellRenderer());

        TableColumn specialtyColumn = getColumnModel().getColumn(SPECIALTY_COLUMN_NUMBER);
        specialtyColumn.setCellRenderer(new SpecialtyCellRenderer());
    }

    public void addRow(Object[] data){
        tableModel.addRow(data);
    }

    public void setData(Object[][] data){
        tableModel.setDataVector(data, COLUMN_NAMES);
        initColumns();
    }

    private class SpecialtyCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);
            String textToDisplay = "";
            java.util.List<SpecialtyDTO> specialties = (java.util.List<SpecialtyDTO>) value;

            for (SpecialtyDTO specialty : specialties) {
                textToDisplay += specialty.getName() + ", ";
            }

            if (!textToDisplay.isEmpty()) {
                textToDisplay = textToDisplay.substring(0, textToDisplay.length() - 2);
            }

            setValue(textToDisplay);
            return this;
        }
    }

    private class FacultyCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);

            FacultyDTO faculty = (FacultyDTO) value;
            setValue(faculty.getName());

            return this;
        }
    }
}
