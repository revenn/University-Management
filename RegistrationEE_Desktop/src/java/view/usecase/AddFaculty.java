/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.usecase;

import model.dto.FacultyDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import registrationee_desktop.Client;
import view.generic.CustomTab;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AddFaculty extends CustomTab{

    JLabel acronymLabel;
    JTextField acronymTextField;
    JLabel facultyNameLabel;
    JTextField facultyNameTextField;
    JLabel deanLabel;
    JTextField deanTextField;
    JButton addButton;
    JButton addToDbButton;
    JTable table;
    DefaultTableModel model;
    
    private final String[] columnNames = {"Akronim","Nazwa wydzialu","Dziekan"}; 
  
    
    public AddFaculty() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        Object[][] data = Client.getFacade().getFacultiesAsArray();
        model = new DefaultTableModel(data,columnNames);
        
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        
       
        acronymLabel = new JLabel("Akronim wydzialu");
        add(acronymLabel);
        
        acronymTextField = new JTextField(30);
        add(acronymTextField);
        
        facultyNameLabel = new JLabel("Nazwa wydzialu");
        add(facultyNameLabel);
        
        facultyNameTextField = new JTextField(30);
        add(facultyNameTextField);
        
        deanLabel = new JLabel("Dziekan");
        add(deanLabel);
        
        deanTextField = new JTextField(30);
        add(deanTextField);
        
        addButton = new JButton("Dodaj wydzial");
        addButton.addActionListener(event -> {
            FacultyDTO newFaculty = createData();
            if(newFaculty == null) {
                return;
            }
            boolean result = Client.getFacade().addFaculty(newFaculty);
            if(result) {
                JOptionPane.showMessageDialog(null, "Dodano wydział!");
            } else {
                JOptionPane.showMessageDialog(null, "Nie udalo sie dodac wydzialu!", "Blad", JOptionPane.ERROR_MESSAGE);
            }           
            reloadTableContent(model);
        });
        add(addButton);
        
        addToDbButton = new JButton("Zsynchronizuj wydzialy z baza danych");
        addToDbButton.addActionListener(event -> {
            boolean result = Client.getFacade().addFaculties_db();
            if(result) {
                JOptionPane.showMessageDialog(null, "Zaktualizowano wydzialy w bazie danych!");
            } else {
                JOptionPane.showMessageDialog(null, "Nie udalo sie Zaktualizowac wydzialow w bazie danych!", "Blad", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addToDbButton);
        
        add(filler);
        initShowListener();
    }
    
    public FacultyDTO createData() {
        if(validateData(acronymTextField) == null) {
            return null;
        }
        if(validateData(facultyNameTextField) == null) {
            return null;
        }
        if(validateData(deanTextField) == null) {
            return null;
        }
       
        return FacultyDTO.builder()
                .id(null)
                .acronym(acronymTextField.getText())
                .dean(deanTextField.getText())
                .name(facultyNameTextField.getText())
                .build();
    }
    
    public String validateData(JTextField field) {
        String s = field.getText();
        if(s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartości!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return s;
    }
    
    private void initShowListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
            }
            @Override
            public void componentShown(ComponentEvent evt) {
                reloadTableContent(model);
            }
       });
    }
    
    public void reloadTableContent(DefaultTableModel m){
        Object[][] rows = Client.getFacade().getFacultiesAsArray();
        m.setDataVector(rows, columnNames);
    }
}
