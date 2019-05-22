package view.util;

import view.generic.CustomTab;
import view.usecase.AddCoursesGroup;
import view.usecase.AddClassesTerm;
import view.usecase.AddFaculty;
import view.usecase.AddFieldOfStudy;
import view.usecase.AddHall;
import view.usecase.AddLecturer;
import view.usecase.AddSpecialty;
import view.usecase.AddStudent;
import view.usecase.GenerateSchedule;
import view.usecase.AddClassesToStudent;

import javax.swing.*;
import java.awt.*;

public class PanelUtil {

    //for backward compatibility
    public final static int WIDTH = 800;
    public final static int HEIGHT = 460;

    private static int maxWidth;
    private static int maxHeight;

    private static JTabbedPane tabbedPane;

    public static void createAndShowGUI() {
        PanelUtil panelUtil = new PanelUtil();

        JFrame frame = new JFrame("Zapisy");
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panelUtil.createContentPane());

        panelUtil.initChangeListener(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initChangeListener(JFrame frame) {
        tabbedPane.addChangeListener(e -> {
            Component newTab = ((JTabbedPane) e.getSource()).getSelectedComponent();
            Dimension newTabDimension = newTab.getPreferredSize();

            ((CustomTab)tabbedPane.getSelectedComponent()).setFillerSize(maxWidth, maxHeight - newTabDimension.height);
            frame.pack();
        });
    }

    private Container createContentPane() {
        tabbedPane = new JTabbedPane();

        addTab("Zajecia", new AddClassesTerm(), 500, 700);
        addTab("Kursy", new AddCoursesGroup(), 800, 700);
        addTab("Wydzialy", new AddFaculty(), 800, 370);
        addTab("Kierunki", new AddFieldOfStudy(), 800, 400);
        addTab("Sale", new AddHall(), 800, 370);
        addTab("Prowadzacy", new AddLecturer(), 800, 400);
        addTab("Specjalnosci", new AddSpecialty(), 800, 200);
        addTab("Studenci", new AddStudent(), 800, 200);
        addTab("Rozklad", new GenerateSchedule(), 800, 200);
        addTab("Zapisy", new AddClassesToStudent(),800,200);

        return tabbedPane;
    }

    private void addTab(String title, Container component, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));

        maxWidth = Math.max(width, maxWidth);
        maxHeight = Math.max(height, maxHeight);

        tabbedPane.addTab(title, component);
    }
}

