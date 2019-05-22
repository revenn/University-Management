package testsFitNesse;

import controller.Factory;
import fit.ColumnFixture;
import model.entity.Classes;
import model.entity.Course;
import model.entity.CoursesGroup;
import model.entity.Faculty;
import model.entity.FieldOfStudy;
import model.entity.Hall;
import model.entity.Lecturer;
import model.entity.Student;
import model.entity.Term;
import model.misc.CourseType;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;

import java.util.ArrayList;
import java.util.List;

public class addClassesToStudent extends ColumnFixture {
    
    private Factory factory;
    private String[] facultyData;
    private String[] fieldOfStudyData;
    private String[] coursesGroupData;
    private String[] coursesData;
    private String[] lecturersData;
    private String[] hallsData;
    private String[] termsData;
    private String[] studentsData;
    private boolean classesInSchedule;

    public boolean addClassesToStudent() {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < studentsData.length; i=i+6) {
            students.add(new Student(studentsData[i], studentsData[i + 1], studentsData[i + 2], studentsData[i + 3], studentsData[i + 4], studentsData[i + 5]));
        }

        Faculty faculty = new Faculty(facultyData[0], facultyData[1], facultyData[2]);
        FieldOfStudy fieldOfStudy = new FieldOfStudy(fieldOfStudyData[0], fieldOfStudyData[1], LevelOfStudy.valueOf(fieldOfStudyData[2]), ModeOfStudy.valueOf(fieldOfStudyData[3]), faculty);
        CoursesGroup coursesGroup = new CoursesGroup(coursesGroupData[0], Integer.parseInt(coursesGroupData[1]), Integer.parseInt(coursesGroupData[2]));
        Course course = new Course(coursesData[0], CourseType.valueOf(coursesData[1]), coursesGroup);
        Lecturer lecturer = new Lecturer(lecturersData[0], lecturersData[1], lecturersData[2], lecturersData[3], lecturersData[4], lecturersData[5]);
        Hall hall = new Hall(hallsData[0], hallsData[1], Integer.parseInt(hallsData[2]));
        Term term = new Term(hall, termsData[0], termsData[1], Integer.parseInt(termsData[2]), Integer.parseInt(termsData[3]));
        Classes classes = new Classes(course, lecturer, term);

        course.addClasses(classes, lecturer);
        coursesGroup.setMainCourse(course);

        if (classesInSchedule) {
            students.get(0).addCoursesGroupToSchedule(coursesGroup);
        }

        return students.get(0).addClass(classes);
    }
}
