package view.usecase;

import BusinessTier.Facade_ejbRemote;
import registrationee_desktop.Client;
import controller.Facade;
import model.dto.FieldOfStudyDTO;
import model.dto.SpecialtyDTO;
import view.generic.CustomTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AddSpecialty extends CustomTab {

    private Facade_ejbRemote facade;
    private JComboBox<FieldOfStudyDTO> fieldsOfStudyComboBox;
    private JTextField acronymTextField;
    private JTextField nameTextField;
    private JButton submitButton;

    public AddSpecialty() {
        initFacade();
        initFieldsOfStudyComboBox();
        initTextFields();
        initSubmitButton();
        initComponentListener();
        initLayout();
    }

    private void initFacade(){
        facade = Client.getFacade();
    }

    private void initFieldsOfStudyComboBox() {
        fieldsOfStudyComboBox = new JComboBox<>(facade.getFieldsOfStudyDTOs().toArray(new FieldOfStudyDTO[facade.getFieldsOfStudyDTOs().size()]));
        fieldsOfStudyComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FieldOfStudyDTO) {
                    String text;
                    FieldOfStudyDTO fieldOfStudy = (FieldOfStudyDTO)value;

                    text = fieldOfStudy.getFaculty().getAcronym() + ", "
                            + fieldOfStudy.getName() + ", "
                            + fieldOfStudy.getLevel() + ", "
                            + fieldOfStudy.getMode();
                    setText(text);
                }
                return this;
            }
        });
    }

    private void initTextFields() {
        acronymTextField = new JTextField();
        nameTextField = new JTextField();
    }

    private void initSubmitButton() {
        submitButton = new JButton("Dodaj nowa specjanolosc");

        submitButton.addActionListener(e -> {
            if (!checkInputs()) {
                JOptionPane.showMessageDialog(this, "Nalezy wypelnic wszystkie pola");
            } else {
                SpecialtyDTO specialtyDTO = new SpecialtyDTO();

                specialtyDTO.setAcronym(acronymTextField.getText());
                specialtyDTO.setName(nameTextField.getText());
                specialtyDTO.setFieldOfStudy((FieldOfStudyDTO) fieldsOfStudyComboBox.getSelectedItem());

                if (facade.addSpecialty(specialtyDTO)) {
                    JOptionPane.showMessageDialog(this, "Dodano nowa specjalnosc");
                    clearTextFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Podana spejcalnosc juz istnieje");
                }
            }
        });
    }

    private boolean checkInputs() {
        if (nameTextField.getText().isEmpty())
            return false;
        if (acronymTextField.getText().isEmpty())
            return false;

        return true;
    }

    private void clearTextFields(){
        acronymTextField.setText("");
        nameTextField.setText("");
    }

    private void initLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new Label("Dodaj specjalnosc do"));
        add(fieldsOfStudyComboBox);

        add(new Label("Akronim"));
        add(acronymTextField);

        add(new Label("Nazwa"));
        add(nameTextField);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(submitButton);
        add(Box.createRigidArea(new Dimension(0, 250)));
        add(filler);
    }

    private void initComponentListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                initFieldsOfStudyComboBoxData(facade.getFieldsOfStudyDTOs().toArray(new FieldOfStudyDTO[facade.getFieldsOfStudyDTOs().size()]));
            }
        } );
    }

    private void initFieldsOfStudyComboBoxData(FieldOfStudyDTO[] data){
        fieldsOfStudyComboBox.setModel(new DefaultComboBoxModel<>(data));
    }
}
