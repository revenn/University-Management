/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.usecase;

import BusinessTier.Facade_ejbRemote;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.dto.ClassesDTO;
import registrationee_desktop.Client;
import view.generic.CustomTab;

public class AddClassesToStudent extends CustomTab implements ActionListener {

    private Facade_ejbRemote facade;
    private JLabel studentInfoLabel;
    private JTextField indexField;
    private JButton showDataButton;
    private JLabel tableClassesTitle;
    private JTable tableClasses;
    private DefaultTableModel modelClasses;
    private JLabel tableScheduleTitle;
    private JTable tableSchedule;
    private DefaultTableModel modelSchedule;
    private JLabel classesLabel;
    private JComboBox<ClassesDTO> classesComboBox;
    private JButton addClassesToStudentButton;
    private String indexNumber;
    
    private final String[] columnClassesNames = {"Kurs","Prowadzacy","Sala","Termin"};
    private final String[] columnScheduleNames = {"Nazwa","ECTS","Semestr","Kurs w","Kursy czast."};
    
    public AddClassesToStudent() {
        super();
        indexNumber = null;
        initFacade();
        initTable();
        initComboBoxes();
        initComponentListener();
        initLayout();
    }
    
    

    private void initFacade() {
        facade = Client.getFacade();
    }

    private void initTable() {
        Object[][] data = null;
        modelClasses = new DefaultTableModel(data,columnClassesNames);
        tableClasses = new JTable(modelClasses);
        tableClasses.setPreferredScrollableViewportSize(new Dimension(500,200));
        tableClasses.setFillsViewportHeight(true);

        
      
        modelSchedule = new DefaultTableModel(data,columnScheduleNames);
        tableSchedule = new JTable(modelSchedule);
        tableSchedule.setPreferredScrollableViewportSize(new Dimension(500,200));
        tableSchedule.setFillsViewportHeight(true);
    }

    private void initComboBoxes() {
        classesComboBox = new JComboBox();
        classesComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ClassesDTO) {
                    String text = ((ClassesDTO) value).getCourse().getName()
                            + " | "
                            + ((ClassesDTO) value).getLecturer().getTitle()
                            + " "
                            + ((ClassesDTO) value).getLecturer().getFirstName()
                            + " "
                            + ((ClassesDTO) value).getLecturer().getLastName()
                            +" | "
                            + ((ClassesDTO) value).getTerm().getHall().getBuildingName()
                            + "/"
                            + ((ClassesDTO) value).getTerm().getHall().getHallName()
                            + " "
                            + ((ClassesDTO) value).getTerm().getDayOfTheWeek()
                            +" "
                            + ((ClassesDTO) value).getTerm().getWeekParity()
                            + " "
                            + ((ClassesDTO) value).getTerm().getTimeTable()[0]
                            + ":"
                            + ((ClassesDTO) value).getTerm().getTimeTable()[1];
                    setText(text);
                }
                return this;
            }
        });
    }

    private void initComponentListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reloadComboBox();
                reloadTable();
            }
        });
    }
    
    private void reloadComboBox() {
        if(indexNumber != null){
            DefaultComboBoxModel model = new DefaultComboBoxModel(facade.getClassesDTOs(indexNumber).toArray());
            classesComboBox.setModel(model);
        }
    }

    private void reloadTable() {
        if(indexNumber != null){
            Object[][] rows = facade.getClassesAsArray(indexNumber);
            modelClasses.setDataVector(rows, columnClassesNames);
            rows = facade.getScheduleAsArray(indexNumber);
            modelSchedule.setDataVector(rows, columnScheduleNames);
            tableScheduleTitle.setText("Kursy na ktore trzebe dokonac zapisu:");
        }
    }

    private void initLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        studentInfoLabel = new JLabel("Podaj numer indeksu");
        add(studentInfoLabel);
        
        indexField = new JTextField();
        add(indexField);
        showDataButton = new JButton("OK");
        showDataButton.addActionListener(this); 
        add(showDataButton);
        add(Box.createRigidArea(new Dimension(0,20)));
       
        tableClassesTitle = new JLabel("Zapisany na zajecia:");
        add(tableClassesTitle);
        add(new JScrollPane(tableClasses));
        add(Box.createRigidArea(new Dimension(0,20)));
        tableScheduleTitle = new JLabel("Kursy na ktore trzebe dokonac zapisu: ");
        add(tableScheduleTitle);
        add(new JScrollPane(tableSchedule));
        add(Box.createRigidArea(new Dimension(0,20)));
        classesLabel = new JLabel("Wybierz zajecia:");
        add(classesLabel);
        add(classesComboBox);
        
        addClassesToStudentButton = new JButton("ZAPISZ");
        addClassesToStudentButton.addActionListener(this);
        add(addClassesToStudentButton);
        
        add(filler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == showDataButton){
            showData();
        }
        if(e.getSource() == addClassesToStudentButton){
            addClassesToStudent();
        }
    }

    private void showData() {
        if(validateData(indexField) == null){
            return;
        }
        indexNumber = indexField.getText();
        reloadComboBox();
        reloadTable();
    }

    private String validateData(JTextField field) {
        String s = field.getText();
        if (s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartosci!", "Blad!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (isNotInteger(s)) {
            JOptionPane.showMessageDialog(this, "Błędny numer indeksu", "Błąd", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return s;
    }

    private boolean isNotInteger(String s) {
        try{
             Integer.parseInt(s);
        }catch(NumberFormatException e){
            return true;
        }
        return false;
    }

    private void addClassesToStudent() {
        ClassesDTO data = createData();
        if( data == null) {
            return;
        }
        boolean result = facade.addClassesToStudent(indexNumber, data);
        if (!result) {
            JOptionPane.showMessageDialog(this, "Zapis nie powiodl sie!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            reloadTable();
            JOptionPane.showMessageDialog(this, "Zapisano na zajecia", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private ClassesDTO createData() {
        if(validateData(indexField) == null){
            return null;
        }
        return (ClassesDTO) classesComboBox.getSelectedItem();
        
    }
}
