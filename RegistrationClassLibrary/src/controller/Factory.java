package controller;

import model.misc.CourseType;
import model.entity.Course;
import model.entity.Term;
import model.entity.Faculty;
import model.entity.CoursesGroup;
import model.entity.FieldOfStudy;
import model.entity.Lecturer;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import model.entity.Specialty;
import model.entity.Student;
import model.entity.Hall;
import model.entity.Classes;
import java.util.Arrays;
import model.dto.FacultyDTO;
import model.dto.StudentDTO;

public class Factory {
    
    public Factory() {}
    
    public Lecturer createLecturer(String[] data) {
        String firstName = data[0];
        String lastName = data[1];
        String personalIdentityNumber = data[2];
        String address = data[3];
        String gender = data[4];
        String title = data[5];
                   
        return new Lecturer(firstName, lastName, personalIdentityNumber, address, gender, title);
    }
    
    public Hall createHall(String[] data) {
        String buildingName = data[0];
        String hallName = data[1];
        Integer size = Integer.parseInt(data[2]);
        
        return new Hall(buildingName,hallName,size);
    }
    
    public Student createStudent(StudentDTO student) {
        return new Student(
                student.getIndexNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getPersonalIdentityNumber(),
                student.getAddress(),
                student.getGender());
    }
    
    public FieldOfStudy createFieldOfStudy(Object[] data){
        String acronym = (String) data[0];
        String name = (String) data[1];
        LevelOfStudy levelOfStudy = (LevelOfStudy) data[2];
        ModeOfStudy modeOfStudy = (ModeOfStudy) data[3] ;
        Faculty faculty = (Faculty) data[4];

        return new FieldOfStudy(acronym, name, levelOfStudy, modeOfStudy, faculty);
    }
    
    public Term createTerm(Object data[]) {
        Hall hall = (Hall) data[0];
        String weekParity = (String) data[1];
        String dayOfTheWeek = (String) data[2];
        Integer timeTable[] = (Integer[]) data[3];
        
        return new Term(hall, weekParity, dayOfTheWeek, timeTable[0], timeTable[1]);
    }
    
    public Course createCourse(Object data[]) {
        String name = (String) data[0];
        CourseType courseType = CourseType.valueOf((String) data[1]);
        CoursesGroup group = (CoursesGroup) data[3];

        return new Course(name, courseType, group);
    }
    
    public Course createCourse(Object data[], CoursesGroup group) {
        String name = (String) data[0];
        CourseType courseType = (CourseType) data[1];
        
        return new Course(name, courseType, group);
    }
    
    public CoursesGroup createCoursesGroup(Object data[]) {
        String name = (String) data[0];     
        int ects = (Integer) data[1];
        int semester = (Integer) data[2];   
        CoursesGroup coursesGroup = new CoursesGroup(name, ects, semester); 
        
        Course mainCourse = createCourse(Arrays.copyOfRange(data, 3, 5), coursesGroup);
        coursesGroup.setMainCourse(mainCourse);
        coursesGroup.addPartialCourse(mainCourse);
        
        return coursesGroup;
    }

    public Specialty createSpecialty(Object data[]) {
        String acronym = (String)data[0];
        String name = (String)data[1];
        FieldOfStudy fieldOfStudy = (FieldOfStudy) data[2];
        return new Specialty(acronym, name, fieldOfStudy);
    }
    
    public Classes createClasses(Object[] data) {
        Course course = (Course)data[0];
        Lecturer lecturer = (Lecturer)data[1];
        Term term = (Term)data[2];
   
        return new Classes(course, lecturer, term);
    }
    
    public Faculty createFaculty(FacultyDTO faculty) {
    
        return new Faculty(
                faculty.getAcronym(),
                faculty.getName(),
                faculty.getDean());
    }
}