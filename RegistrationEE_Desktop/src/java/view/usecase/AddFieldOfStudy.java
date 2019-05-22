package view.usecase;

import BusinessTier.Facade_ejbRemote;
import registrationee_desktop.Client;
import controller.Facade;
import model.dto.FacultyDTO;
import model.dto.FieldOfStudyDTO;
import model.dto.SpecialtyDTO;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import view.generic.CustomTab;
import view.standalone.FieldsOfStudyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class AddFieldOfStudy extends CustomTab {
    private Facade_ejbRemote facade;
    private JTextField acronymTextField;
    private JTextField nameTextField;
    private JComboBox<LevelOfStudy> levelComboBox;
    private JComboBox<ModeOfStudy> modeComboBox;
    private JComboBox<FacultyDTO> facultiesComboBox;
    private JButton submitButton;
    private JButton submitToDbButton;
    private FieldsOfStudyTable table;

    public AddFieldOfStudy() {
        initFacade();
        initTable();
        initTextFields();
        initLevelComboBox();
        initModeComboBox();
        initFacultiesComboBox();
        initSubmitButton();
        initSubmitToDbButton();
        initComponentListener();
        initLayout();
    }

    private void initFacade(){
        facade = Client.getFacade();
    }

    private void initTable() {
        table = new FieldsOfStudyTable();
    }

    private void initTextFields() {
        acronymTextField = new JTextField();
        nameTextField = new JTextField();
    }

    private void initLevelComboBox() {
        levelComboBox = new JComboBox<>(LevelOfStudy.values());
    }

    private void initModeComboBox() {
        modeComboBox = new JComboBox<>(ModeOfStudy.values());
    }

    private void initFacultiesComboBox() {
        facultiesComboBox = new JComboBox<>(facade.getFacultyDTOs().toArray(new FacultyDTO[facade.getFacultyDTOs().size()]));

        facultiesComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FacultyDTO) {
                    setText(((FacultyDTO)value).getName());
                }
                return this;
            }
        });
    }

    private void initSubmitButton() {
        submitButton = new JButton("Dodaj nowy kierunek");
        submitButton.addActionListener(e -> {
         if (!checkInputs()) {
                JOptionPane.showMessageDialog(this, "Nalezy wypelnic wszystkie pola");
            } else {
                FieldOfStudyDTO fieldOfStudyDTO = new FieldOfStudyDTO();

                fieldOfStudyDTO.setAcronym(acronymTextField.getText());
                fieldOfStudyDTO.setName(nameTextField.getText());
                fieldOfStudyDTO.setLevel((LevelOfStudy) levelComboBox.getSelectedItem());
                fieldOfStudyDTO.setMode((ModeOfStudy) modeComboBox.getSelectedItem());
                fieldOfStudyDTO.setFaculty((FacultyDTO) facultiesComboBox.getSelectedItem());

                if (facade.addFieldOfStudy(fieldOfStudyDTO)) {
                    JOptionPane.showMessageDialog(this, "Dodano kierunek");
                    addNewRow();
                    clearTextFields();
                    reloadTable();
                    reloadComboBox();
                } else {
                    JOptionPane.showMessageDialog(this, "Podany kierunek juz istnieje");
                }
            }
         
        });
    }
    
    private void initSubmitToDbButton() {
        submitToDbButton = new JButton("Synchronizuj kierunki z bazą danych");
        submitToDbButton.addActionListener(e -> {
            boolean result = facade.addFieldsOfStudy_db();
            if(result) {
                JOptionPane.showMessageDialog(null, "Zaktualizowano kierunki!");
            } else {
                JOptionPane.showMessageDialog(null, "Nie udalo sie zsynchronizowac kierunkow!", "Blad", JOptionPane.ERROR_MESSAGE);
            }   
        });
    }

    private void addNewRow() {
        table.addRow(new Object[]{
                facultiesComboBox.getSelectedItem(),
                nameTextField.getText(),
                levelComboBox.getSelectedItem(),
                modeComboBox.getSelectedItem(),
                new ArrayList<SpecialtyDTO>()
        });
    }

    private void clearTextFields(){
        acronymTextField.setText("");
        nameTextField.setText("");
    }

    private boolean checkInputs() {
        if (nameTextField.getText().isEmpty())
            return false;
        if (acronymTextField.getText().isEmpty())
            return false;
        if (facultiesComboBox.getSelectedItem() == null)
            return false;
        if (levelComboBox.getSelectedItem() == null)
            return false;
        if (modeComboBox.getSelectedItem() == null)
            return false;

        return true;
    }

    private void initLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JScrollPane(table));

        add(new Label("Dodaj nowy kierunek:"));
        add(new Label("Akronim"));
        add(acronymTextField);

        add(new Label("Nazwa:"));
        add(nameTextField);

        add(new Label("Wydzial:"));
        add(facultiesComboBox);

        add(new Label("Stopien studiow:"));
        add(levelComboBox);

        add(new Label("Tryb studiow:"));
        add(modeComboBox);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(submitButton);
        add(submitToDbButton);
        add(filler);
    }

    private void initComponentListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reloadTable();
                reloadComboBox();
            }
        } );
    }
    
    private void reloadComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(facade.getFacultyDTOs().toArray(new FacultyDTO[facade.getFacultyDTOs().size()]));
        facultiesComboBox.setModel(model);
    }
    
    private void reloadTable(){
        List<FieldOfStudyDTO> fieldOfStudyDTOS = facade.getFieldsOfStudyDTOs();

                Object[][] array = new Object[fieldOfStudyDTOS.size()][5];

                for (int i = 0; i < fieldOfStudyDTOS.size(); i++) {
                    array[i][0] = fieldOfStudyDTOS.get(i).getFaculty();
                    array[i][1] = fieldOfStudyDTOS.get(i).getName();
                    array[i][2] = fieldOfStudyDTOS.get(i).getLevel();
                    array[i][3] = fieldOfStudyDTOS.get(i).getMode();
                    array[i][4] = fieldOfStudyDTOS.get(i).getSpecialties();
                }
                table.setData(array);
    }
}
