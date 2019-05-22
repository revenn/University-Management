package view.usecase;

import BusinessTier.Facade_ejbRemote;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.dto.ClassesDTO;
import model.dto.CourseDTO;
import model.dto.HallDTO;
import model.dto.LecturerDTO;
import model.dto.TermDTO;
import registrationee_desktop.Client;
import view.generic.CustomTab;

public class AddClassesTerm extends CustomTab implements ActionListener {

    private Facade_ejbRemote facade;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lecturerLabel;
    private JComboBox<LecturerDTO> lecturerComboBox;
    private JLabel courseLabel;
    private JComboBox<CourseDTO> courseComboBox;
    private JLabel hallLabel;
    private JComboBox<HallDTO> hallComboBox;
    private JLabel weekParityLabel;
    private JComboBox<String> weekParityComboBox;
    private JLabel dayOfTheWeekLabel;
    private JComboBox<String> dayOfTheWeekComboBox;
    private JLabel hourLabel;
    private JTextField hourField;
    private JLabel minutesLabel;
    private JTextField minutesField;
    private JButton addClassesButton;

    private final String[] columnNames = {"Wydzial", "Kierunek", "Kurs", "Prowadzacy", "Sala", "Termin"};
    private final String[] weekParity = {"TN", "TP", "-"};
    private final String[] dayOfTheWeek = {"Pn", "Wt", "Sr", "Czw", "Pt"};

    public AddClassesTerm() {
        super();
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));

        lecturerLabel = new JLabel("Prowadzacy: ");
        add(lecturerLabel);
        add(lecturerComboBox);

        courseLabel = new JLabel("Kurs: ");
        add(courseLabel);
        add(courseComboBox);

        hallLabel = new JLabel("Sala: ");
        add(hallLabel);
        add(hallComboBox);

        weekParityLabel = new JLabel("Parzystosc: ");
        add(weekParityLabel);
        add(weekParityComboBox);

        dayOfTheWeekLabel = new JLabel("Dzien tygodnia: ");
        add(dayOfTheWeekLabel);
        add(dayOfTheWeekComboBox);

        hourLabel = new JLabel("Godzina: ");
        add(hourLabel);
        hourField = new JTextField();
        add(hourField);

        minutesLabel = new JLabel("Minuta: ");
        add(minutesLabel);
        minutesField = new JTextField();
        add(minutesField);

        addClassesButton = new JButton("Dodaj zajeica");
        addClassesButton.addActionListener(this);
        add(addClassesButton);

        add(filler);
    }

    private void initTable() {
        Object[][] data = facade.getClassesAsArray();
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
    }

    private void initComboBoxes() {
        lecturerComboBox = new JComboBox(facade.getLecturerDTOs().toArray());

        lecturerComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof LecturerDTO) {
                    String text = ((LecturerDTO) value).getTitle()
                            + " "
                            + ((LecturerDTO) value).getFirstName()
                            + " "
                            + ((LecturerDTO) value).getLastName();
                    setText(text);
                }
                return this;
            }
        });

        courseComboBox = new JComboBox(facade.getCourseDTOs().toArray());

        courseComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CourseDTO) {
                    String text = ((CourseDTO) value).getName();
                    setText(text);
                }
                return this;
            }
        });

        hallComboBox = new JComboBox(facade.getHallDTOs().toArray());

        hallComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof HallDTO) {
                    String text = ((HallDTO) value).getBuildingName()
                            + "/"
                            + ((HallDTO) value).getHallName();
                    setText(text);
                }
                return this;
            }
        });

        weekParityComboBox = new JComboBox(weekParity);
        dayOfTheWeekComboBox = new JComboBox(dayOfTheWeek);
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
        DefaultComboBoxModel modelLecturer = new DefaultComboBoxModel(facade.getLecturerDTOs().toArray());
        lecturerComboBox.setModel(modelLecturer);

        DefaultComboBoxModel modelCourse = new DefaultComboBoxModel(facade.getCourseDTOs().toArray());
        courseComboBox.setModel(modelCourse);

        DefaultComboBoxModel modelHall = new DefaultComboBoxModel(facade.getHallDTOs().toArray());
        hallComboBox.setModel(modelHall);

    }

    private void reloadTable() {
        Object[][] rows = facade.getClassesAsArray();
        model.setDataVector(rows, columnNames);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addClassesButton) {
            addClasses();
            reloadTable();
        }
    }

    private void addClasses() {
        ClassesDTO data = createData();
        if (data == null) {
            return;
        }
        boolean result = Client.getFacade().addClasses(data);
        if (!result) {
            JOptionPane.showMessageDialog(this, "Dodawanie zajec nie powiodło się!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            reloadTable();
            JOptionPane.showMessageDialog(this, "Dodano zajecia", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private ClassesDTO createData() {
        if (validateData(hourField) == null) {
            return null;
        }
        if (validateData(minutesField) == null) {
            return null;
        }

        Integer[] timeTable = new Integer[2];
        timeTable[0] = Integer.parseInt(hourField.getText());
        timeTable[1] = Integer.parseInt(minutesField.getText());
        
        TermDTO termDTO = new TermDTO(
                (HallDTO) hallComboBox.getSelectedItem(),
                (String) weekParityComboBox.getSelectedItem(),
                (String) dayOfTheWeekComboBox.getSelectedItem(),
                timeTable);

        return new ClassesDTO(
                (CourseDTO) courseComboBox.getSelectedItem(),
                (LecturerDTO) lecturerComboBox.getSelectedItem(),
                termDTO,
                null);
    }

    private String validateData(JTextField field) {
        String s = field.getText();
        if (s.equals("")) {
            JOptionPane.showMessageDialog(this, "Wymagane wszystkie wartosci!", "Blad!", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (field == hourField) {
            if (isNotHour(s)) {
                JOptionPane.showMessageDialog(this, "Błędna godzina (0-23)", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        if (field == minutesField) {
            if (isNotMinutes(s)) {
                JOptionPane.showMessageDialog(this, "Błędna minuta (0-60)", "Błąd", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return s;
    }

    public boolean isNotHour(String s) {
        Integer hour;
        try {
            hour = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return true;
        }
        return hour < 0 || hour > 23;
    }

    public boolean isNotMinutes(String s) {
        Integer minutes;
        try {
            minutes = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return true;
        }
        return minutes < 0 || minutes > 60;
    }

}
