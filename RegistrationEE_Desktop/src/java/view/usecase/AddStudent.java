/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.usecase;

import registrationee_desktop.Client;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.dto.FieldOfStudyDTO;
import model.dto.StudentDTO;
import view.generic.CustomTab;

public class AddStudent extends CustomTab implements ActionListener{

    JLabel firstNameLabel;
    JTextField firstNameTextField;
    JLabel lasNameLabel;
    JTextField lastNameTextField;
    JLabel personalIdentityNumberLabel;
    JTextField personalIdentityNumberTextField;
    JLabel addressLabel;
    JTextField addressTextField;
    JLabel genderLabel;
    JComboBox<String> genderComboBox;
    JLabel indexLabel;
    JTextField indexTextField;
    JComboBox<FieldOfStudyDTO> fieldOfStudyComboBox;
    JButton addButton;
    JTable table;
    DefaultTableModel model;
    
    private final String[] columnNames = {"Indeks","Imie","Nazwisko", "PESEL", "Adres", "Plec"}; 
  
    
    public AddStudent() {
        Object[][] data = Client.getFacade().getStudentsAsArray();
        model = new DefaultTableModel(data,columnNames);
        
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        indexLabel = new JLabel("Indeks");
        add(indexLabel);
        
        indexTextField = new JTextField(30);
        add(indexTextField);
        
        firstNameLabel = new JLabel("Imie");
        add(firstNameLabel);
        
        firstNameTextField = new JTextField(30);
        add(firstNameTextField);
        
        lasNameLabel = new JLabel("Nazwisko");
        add(lasNameLabel);
        
        lastNameTextField = new JTextField(30);
        add(lastNameTextField);
        
        personalIdentityNumberLabel = new JLabel("PESEL");
        add(personalIdentityNumberLabel);
        
        personalIdentityNumberTextField = new JTextField(30);
        add(personalIdentityNumberTextField);
        
        addressLabel = new JLabel("Adres");
        add(addressLabel);
        
        addressTextField = new JTextField(30);
        add(addressTextField);
        
        genderLabel = new JLabel("Plec");
        add(genderLabel);
        
        initGenderComboBox();
        add(genderComboBox);
        
        initTypeOfStudyComboBox();   
        add(new Label("Typ studiow"));
        add(fieldOfStudyComboBox);
        
        addButton = new JButton("Dodaj studenta");
        addButton.addActionListener(this);
        add(addButton);
        
        initComponentListener();

        add(filler);
    }
    
    private void initGenderComboBox() {
        genderComboBox = new JComboBox<>();
        genderComboBox.addItem("Mezczyzna");
        genderComboBox.addItem("Kobieta");
    }
    
    private void initTypeOfStudyComboBox() {
        fieldOfStudyComboBox = new JComboBox(Client.getFacade().getFieldsOfStudyDTOs().toArray());

        fieldOfStudyComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FieldOfStudyDTO) {
                    String text = ((FieldOfStudyDTO)value).getAcronym() +
                            " | " +
                            ((FieldOfStudyDTO)value).getName() + 
                            " | " +
                            ((FieldOfStudyDTO)value).getLevel() + 
                            " | " +
                            ((FieldOfStudyDTO)value).getMode();
                    setText(text);
                }
                return this;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StudentDTO data = createData();
        if(data == null) {
            return;
        }
        boolean result = Client.getFacade().addStudent(data, (FieldOfStudyDTO)fieldOfStudyComboBox.getSelectedItem());
        if(!result) {
            JOptionPane.showMessageDialog(this, "Dodawanie studenta nie powiodło się!", "Błąd!", JOptionPane.ERROR_MESSAGE); 
        } else {
            reloadTableContent(model);
            JOptionPane.showMessageDialog(this, "Dodano studenta", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public StudentDTO createData() {
        if(validateData(firstNameTextField) == null) {
            return null;
        }
        if(validateData(lastNameTextField) == null) {
            return null;
        }
        if(validateData(personalIdentityNumberTextField) == null) {
            return null;
        }
        if(validateData(addressTextField) == null) {
            return null;
        }
        if(genderComboBox.getSelectedItem() == null) {
            return null;
        }
        if(validateData(indexTextField) == null) {
            return null;
        }
        if(fieldOfStudyComboBox.getSelectedItem() == null) {
            return null;
        }
        
        return new StudentDTO(
                indexTextField.getText(),
                0, //semester
                firstNameTextField.getText(),
                lastNameTextField.getText(), 
                personalIdentityNumberTextField.getText(),
                addressTextField.getText(), 
                genderComboBox.getSelectedItem().toString());
    }
    
    public boolean checkContainsOnlyNumbers(String s) {
        if (s.matches("[0-9]+"))
            return true;
        else
            return false;
        
    }
    
    public String validateData(JTextField field) {
        String s = field.getText();
        if(s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartości!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(field == personalIdentityNumberTextField) {
            if(!checkContainsOnlyNumbers(s)) {
                JOptionPane.showMessageDialog(this, "PESEL musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if(s.length() != 11) {
                JOptionPane.showMessageDialog(this, "PESEL musi zawierać 11 znaków!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        if(field == indexTextField) {
            if(!checkContainsOnlyNumbers(s)) {
                JOptionPane.showMessageDialog(this, "Numer indeksu musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return s;
    }
    
    
    public void reloadTableContent(DefaultTableModel m){
        Object[][] rows = Client.getFacade().getStudentsAsArray();
        m.setDataVector(rows, columnNames);
    }
   
    
    private void initComponentListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reloadComboBox();
            }
        });
    }
    
   public void reloadComboBox() {
       DefaultComboBoxModel model = new DefaultComboBoxModel(Client.getFacade().getFieldsOfStudyDTOs().toArray());
       fieldOfStudyComboBox.setModel(model);
       
   }
}
