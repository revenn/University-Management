package view.usecase;

import BusinessTier.Facade_ejbRemote;
import registrationee_desktop.Client;
import controller.Facade;
import model.misc.CourseType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.dto.FieldOfStudyDTO;
import view.generic.CustomTab;
import view.util.PanelUtil;

public class AddCoursesGroup extends CustomTab {

    private static final int MAX_COURSES = 5;
    private Facade_ejbRemote facade;
    private JTextField nameTextField;
    private JTextField semesterNumberTextField;
    private JTextField ectsTextField;
    private JComboBox[] courseTypesComboBoxes;
    private JComboBox<FieldOfStudyDTO> fieldOfStudyComboBox;
    private JPanel[] coursesPanels;
    private JButton addClassesButton;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private String[] columnNames = { "Nazwa", "Ects", "Semestr", "Kurs w.", "Kursy czast."};
    public AddCoursesGroup() {
        initFacade();
        initPanels();
        initTable();
        initTextFields();
        initComboBoxes();
        initSubmitButton();
        initComponentListener();
        initLayout();
        resetForm();
    }

    private void initFacade() {
        facade = Client.getFacade();
    }

    private void initPanels() {
        coursesPanels = new JPanel[MAX_COURSES];
        for (int i = 0; i < MAX_COURSES; i++) {
            coursesPanels[i] = new JPanel();
        }
    }
    
    private void initTable() {
        Object[][] data = Client.getFacade().getCoursesGroupsAsArray();
        defaultTableModel = new DefaultTableModel(data,columnNames);
        
        table = new JTable(defaultTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
    }
    
    private void initTextFields() {
        nameTextField = new JTextField();
        nameTextField.setSize(PanelUtil.WIDTH - 50, 1);
        semesterNumberTextField = new JTextField();
        semesterNumberTextField.setSize(PanelUtil.WIDTH - 50, 1);
        ectsTextField = new JTextField();
    }

    private void initComboBoxes() {
        courseTypesComboBoxes = new JComboBox[MAX_COURSES];
        for(int i = 0; i < MAX_COURSES; i++) {
            courseTypesComboBoxes[i] = new JComboBox<CourseType>(CourseType.values());
        }
        
        fieldOfStudyComboBox = new JComboBox(facade.getFieldsOfStudyDTOs().toArray());
       
        fieldOfStudyComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FieldOfStudyDTO) {
                    setText(((FieldOfStudyDTO)value).getName());
                }
                return this;
            }
        });
       
    }

    private void initSubmitButton() {
        addClassesButton = new JButton("Dodaj kursy");

        addClassesButton.addActionListener( e -> {
            if(checkInputsValid()) {
                saveCourse();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Nie wypelniono wymaganych pol");
            }
        });
    }

    private boolean checkInputsValid() {
        if (nameTextField.getText().isEmpty()) {
            return false;
        }
        
        if (semesterNumberTextField.getText().isEmpty()) {
            return false;
        }
        
        if(fieldOfStudyComboBox.getSelectedIndex() == -1) {
            return false;
        }
        
        if(courseTypesComboBoxes[0].getSelectedIndex() == -1) {
            return false;
        }
        
        if(ectsTextField.getText().isEmpty()) {
            return false;
        }
        
        if(semesterNumberTextField.getText().isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(ectsTextField.getText());
            Integer.parseInt(semesterNumberTextField.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void saveCourse() {
        CourseType mainCourseType = (CourseType) courseTypesComboBoxes[0].getSelectedItem();
        Object[] coursesGroupData = new Object[] {
            nameTextField.getText(),
            Integer.parseInt(ectsTextField.getText()),
            Integer.parseInt(semesterNumberTextField.getText()),
            nameTextField.getText() + " - " + mainCourseType,
            mainCourseType,
            fieldOfStudyComboBox.getSelectedItem()
        };
        
        ArrayList partialCoursesData = new ArrayList<Object[]>();
        for(int i = 1; i < MAX_COURSES; i++) {
            Object object = courseTypesComboBoxes[i].getSelectedItem();
            if(object != null)
            {
                CourseType courseType = (CourseType) object;
                Object[] newPartialCourse = new Object[] {
                    nameTextField.getText() + " - " + courseType,
                    courseType,
                };
                partialCoursesData.add(newPartialCourse);
            }
        }
        facade.addCompleteCoursesGroup(coursesGroupData, partialCoursesData);
    }

    private void resetForm() {
        nameTextField.setText("");
        ectsTextField.setText("");
        semesterNumberTextField.setText("");
        fieldOfStudyComboBox.setSelectedIndex(-1);
        for(JComboBox box : courseTypesComboBoxes) {
            box.setSelectedIndex(-1);
        }
        defaultTableModel= new DefaultTableModel(facade.getCoursesGroupsAsArray(), columnNames);
        table.setModel(defaultTableModel);
    }

    private void initLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JScrollPane(table));
        
        add(new Label("Dodaj nowa grupe kursow"));
        
        add(new Label("Nazwa grupy kursow:"));
        add(nameTextField);

        add(new Label("Numer semestru:"));
        add(semesterNumberTextField);
        
        add(new Label("Punkty ects"));
        add(ectsTextField);

        add(new Label("Kierunek, dla którego tworzona jest grupa kursow"));
        add(fieldOfStudyComboBox);
        
        coursesPanels[0].setLayout(new BoxLayout(coursesPanels[0], BoxLayout.Y_AXIS));       
        coursesPanels[0].add(new Label("Typ kursu"));
        coursesPanels[0].add(courseTypesComboBoxes[0]);       
        coursesPanels[0].setBorder(BorderFactory.createTitledBorder("Kurs wiodący"));
        add(coursesPanels[0]);
        for(int i = 1; i < MAX_COURSES; i++) {
            coursesPanels[i].setLayout(new BoxLayout(coursesPanels[i], BoxLayout.Y_AXIS));       
            coursesPanels[i].add(new Label("Typ kursu"));
            coursesPanels[i].add(courseTypesComboBoxes[i]);       
            coursesPanels[i].setBorder(BorderFactory.createTitledBorder("Kurs czastkowy " + i));
            add(coursesPanels[i]);
        }
        add(addClassesButton);
        add(filler);
    }

    private void initComponentListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reloadComboBox();
                resetForm();
            } 
        });
    }
    
    private void reloadComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(facade.getFieldsOfStudyDTOs().toArray());
        fieldOfStudyComboBox.setModel(model);
    }
}
