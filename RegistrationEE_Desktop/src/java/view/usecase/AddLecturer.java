/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.usecase;

import registrationee_desktop.Client;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.dto.LecturerDTO;
import view.generic.CustomTab;

public class AddLecturer extends CustomTab implements ActionListener{

    JLabel firstNameLabel;
    JTextField firstNameField;
    
    JLabel lastNameLabel;
    JTextField lastNameField;
    
    JLabel personalIdentityNumberLabel;
    JTextField personalIdentityNumberField;
    
    JLabel addressLabel;
    JTextField addressField;
    
    JLabel genderLabel;
    JTextField genderField;
    
    JLabel titleLabel;
    JTextField titleField;
    
    JButton addButton;
    
    JTable table;
    DefaultTableModel model;
    
    private final String[] COLUMN_NAMES = {"Imię","Nazwisko","Pesel","Adres","Płeć","Tytuł"};
    
    public AddLecturer() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        Object[][] data = Client.getFacade().getLecturerAsArray();
        model = new DefaultTableModel(data,COLUMN_NAMES);
        
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 120));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        
        firstNameLabel = new JLabel("Imię");
        add(firstNameLabel);
        
        firstNameField = new JTextField(30);
        add(firstNameField);
        
        lastNameLabel = new JLabel("Nazwisko:");
        add(lastNameLabel);
        
        lastNameField = new JTextField(30);
        add(lastNameField);

        personalIdentityNumberLabel = new JLabel("Pesel");
        add(personalIdentityNumberLabel);
        
        personalIdentityNumberField = new JTextField(30);
        add(personalIdentityNumberField);

        addressLabel = new JLabel("Adres");
        add(addressLabel);
        
        addressField = new JTextField(30);
        add(addressField);

        genderLabel = new JLabel("Płeć");
        add(genderLabel);
        
        genderField = new JTextField(30);
        add(genderField);

        titleLabel = new JLabel("Tytuł naukowy");
        add(titleLabel);
       
        titleField = new JTextField(30);
        add(titleField);
        
        addButton = new JButton("Dodaj prowadzącego");
        addButton.addActionListener(this);
        add(addButton);

        add(filler);
        
        initShowListener();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        LecturerDTO data = createData();
        if(data == null) {
            return;
        }
        boolean result = Client.getFacade().addLecturer(data);
        if(!result) {
            JOptionPane.showMessageDialog(this, "Dodawanie prowadzącego nie powiodło się!","Błąd",JOptionPane.ERROR_MESSAGE);
        } else {
            reloadTable(model);
            JOptionPane.showMessageDialog(this, "Dodano prowadzącego","",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public LecturerDTO createData() {
        if (validateData(firstNameField) == null) {
            return null;
        }
        
        if(validateData(lastNameField) == null) {
            return null;
        }
        
        if(validateData(personalIdentityNumberField) == null) {
            return null;
        }
        
        if(validateData(addressField) == null) {
            return null;
        }
        
        if(validateData(genderField) == null) {
            return null;
        }
        
        if(validateData(titleField) == null) {
            return null;
        }
        
        return new LecturerDTO(
                firstNameField.getText(), 
                lastNameField.getText(),
                personalIdentityNumberField.getText(),
                addressField.getText(),
                genderField.getText(),
                titleField.getText());
    }
    
    public String validateData(JTextField field) {
        String s = field.getText();
        if(s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartości!","Błąd",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        if(field == personalIdentityNumberField) {
            if(isNotInteger(s) || s.length() != 11) {
                JOptionPane.showMessageDialog(this, "Błędny pesel","Błąd",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        
        if(field == genderField) {
            if(!(s.equals("mężczyzna") || s.equals("kobieta"))) {
                JOptionPane.showMessageDialog(this, "Błędna płęc", "Błąd",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        
        if(field == titleField) {
            String[] titles = {"Mgr","Dr inż."};
            if(!Arrays.asList(titles).contains(s)) {
                JOptionPane.showMessageDialog(this, "Błędny tytuł","Błąd",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return s;
    }
    
    public boolean isNotInteger(String s) {
        try{
            Long.parseLong(s);
        }catch(NumberFormatException e){
            return true;
        }
        return false;
    }
    
    public void reloadTable(DefaultTableModel model) {
        Object[][] rows = Client.getFacade().getLecturerAsArray();
        model.setDataVector(rows, COLUMN_NAMES);
    }
    
    private void initShowListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
            }
            @Override
            public void componentShown(ComponentEvent evt) {
                reloadTable(model);
            }
       });
    }
}
