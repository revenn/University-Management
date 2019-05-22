/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.usecase;

import BusinessTier.Facade_ejbRemote;
import registrationee_desktop.Client;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.dto.StudentDTO;
import view.generic.CustomTab;

public class GenerateSchedule extends CustomTab implements ActionListener{

    private Facade_ejbRemote facade;
    private JTable table;
    private DefaultTableModel model;
    private JLabel studentLabel;
    private JComboBox<StudentDTO> studentComboBox;
    private JButton generateStudentsSchedule;
    private JButton generateAllStudentsSchedule;
   
    
    private final String[] columnNames = {"Indeksu","Nazwa","ECTS","Semestr","Kurs gł.","Kursy"};
    
    
    public GenerateSchedule() {
        initFacade();
        initTable();
        initComboBoxes();
        initComponentListener();
        initLayout();
    }

    private void initFacade() {
        facade = Client.getFacade();
    }

    private void initLayout() {
       
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
        
        studentLabel = new JLabel("Student: ");
        add(studentLabel);
        add(studentComboBox);
        

        
        generateStudentsSchedule = new JButton("Generuj rozkład dla wybranego studenta");
        generateStudentsSchedule.addActionListener(this);
        add(generateStudentsSchedule);
        
        generateAllStudentsSchedule = new JButton("Generuj rozklad wszyskim studentom");
        generateAllStudentsSchedule.addActionListener(this);
        add(generateAllStudentsSchedule);
        add(filler);
    }

    private void initTable() {
        Object[][] data = facade.getScheduleAsArray();
        model = new DefaultTableModel(data,columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500,200));
        table.setFillsViewportHeight(true);
    }

    private void initComboBoxes() {
        studentComboBox = new JComboBox(facade.getStudentDTOs().toArray());
        
        studentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof StudentDTO) {
                    String text = ((StudentDTO) value).getIndexNumber() +
                            " " + 
                            ((StudentDTO) value).getFirstName() + 
                            " " +
                            ((StudentDTO) value).getLastName();
                    setText(text);
                }
                return this;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == generateStudentsSchedule) {
            generateSchedule();
            reloadTable();
        }
        if(e.getSource() == generateAllStudentsSchedule) {
            generataAllSchedule();
            reloadTable();
        }
    }

    private void generateSchedule() {
        facade.generateStudentSchedule((StudentDTO) studentComboBox.getSelectedItem());
    }

    private void reloadTable() {
        Object[][] rows = facade.getScheduleAsArray();
        model.setDataVector(rows, columnNames);
    }

    private void generataAllSchedule() {
        facade.generateAllStudentsSchedule();
    }  
    
    public void reloadComboBoxes() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(facade.getStudentDTOs().toArray());
        studentComboBox.setModel(model);
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
        DefaultComboBoxModel model = new DefaultComboBoxModel(facade.getStudentDTOs().toArray());
        studentComboBox.setModel(model);
    }
}
