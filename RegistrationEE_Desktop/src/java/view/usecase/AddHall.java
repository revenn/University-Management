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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.dto.HallDTO;
import view.generic.CustomTab;

/**
 *
 * @author Bartek
 */
public class AddHall extends CustomTab implements ActionListener{

    JLabel buildingNameLabel;
    JTextField buildingNameField;
    JLabel hallNameLabel;
    JTextField hallNameField;
    JLabel sizeLabel;
    JTextField sizeField;
    JButton addButton;
    JTable table;
    DefaultTableModel model;
    
    private final String[] columnNames = {"Nazwa budynku","Nazwa sali","Liczba miejsc"}; 
  
    
    public AddHall() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        Object[][] data = Client.getFacade().getHallsAsArray();
        model = new DefaultTableModel(data,columnNames);
        
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        
       
        buildingNameLabel = new JLabel("Nazwa budynku");
        add(buildingNameLabel);
        
        buildingNameField = new JTextField(30);
        add(buildingNameField);
        
        hallNameLabel = new JLabel("Nazwa sali");
        add(hallNameLabel);
        
        hallNameField = new JTextField(30);
        add(hallNameField);
        
        sizeLabel = new JLabel("Liczba miejsc");
        add(sizeLabel);
        
        sizeField = new JTextField(30);
        add(sizeField);
        
        addButton = new JButton("Dodaj salę");
        addButton.addActionListener(this);
        add(addButton);
        add(filler);
        
        initShowListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HallDTO data = createData();
        if(data == null) {
            return;
        }
        boolean result = Client.getFacade().addHall(data);
        if(!result) {
            JOptionPane.showMessageDialog(this, "Dodawanie sali nie powiodło się! Taka sala już istnieje", "Błąd!", JOptionPane.ERROR_MESSAGE); 
        } else {
            reloadTableContent(model);
            JOptionPane.showMessageDialog(this, "Dodano sale", "", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public HallDTO createData() {
        if(validateData(buildingNameField) == null) {
            return null;
        }
        if(validateData(hallNameField) == null) {
            return null;
        }
        if(validateData(sizeField) == null) {
            return null;
        }
        try{
            return new HallDTO (
                    buildingNameField.getText(),
                    hallNameField.getText(),
                    Integer.parseInt(sizeField.getText()));
        }
        catch(NumberFormatException e) {
            return null;
        }
    }
    
    public String validateData(JTextField field) {
        String s = field.getText();
        if(s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartości!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(field == sizeField) {
            if(isNotInteger(s)) {
                JOptionPane.showMessageDialog(this, "Liczba miejsc musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return s;
    }
    
    public boolean isNotInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return true;
        }
        return false;
    }
    
    public void reloadTableContent(DefaultTableModel m){
        Object[][] rows = Client.getFacade().getHallsAsArray();
        m.setDataVector(rows, columnNames);
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
}
