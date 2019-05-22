package model.entity;

import model.misc.CourseType;

import java.util.ArrayList;
import java.util.List;
import model.dto.ClassesDTO;
import model.dto.CourseDTO;
import model.dto.CoursesGroupDTO;
import util.ModelMapper;

public class Course {

    private String name;
    private CourseType courseType;
    private CoursesGroup coursesGroup;
    private List<Classes> classes;

    public Course() {
    }

    public Course(String name, CourseType courseType, CoursesGroup coursesGroup) {
        this.name = name;
        this.courseType = courseType;
        this.coursesGroup = coursesGroup;
        this.classes = new ArrayList<>();
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public CoursesGroup getCoursesGroup() {
        return coursesGroup;
    }

    public void setCoursesGroup(CoursesGroup coursesGroup) {
        this.coursesGroup = coursesGroup;
    }
    
    public List<Classes> getClasses() {
        return this.classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        return courseType == course.courseType;
    }

    @Override
    public String toString() {
        return "MainCourse{" +
                "name='" + name + '\'' +
                ", leadingFormType='" + courseType.toString() + '\'' +
                ", classes=" + classes + 
                '}';
    }    

    public boolean addClasses(Classes c, Lecturer lecturer) {
        if(searchClasses(c) != null) {
            return false;
        }
        c.setCourse(this);
        c.setLecturer(lecturer);
        if(!lecturer.addClasses(c)){
            return false;
        }
        classes.add(c);
        return true;
    }
    
    public Classes searchClasses(Classes c) {
        int index = classes.indexOf(c);

        return index != -1 ? classes.get(index) : null;
    }

    void createStringClasses(ArrayList<ArrayList<String>> allClasses, FieldOfStudy fieldOfStudy) {
        for(Classes c : classes) {
            c.createStringClasses(allClasses,fieldOfStudy);
        }
    }

    boolean checkTerm(Term term) {
        for(Classes c : classes) {
            if(!c.checkTerm(term)){
                return false;
            }
        }
        return true;
    }

    void getClassesDTOs(List<ClassesDTO> classesDTOs) {
        ModelMapper modelMapper = new ModelMapper();

        for (Classes c : classes) {
            ClassesDTO classesDTO = modelMapper.createDTO(c);
            CoursesGroupDTO courseGroupDTO = modelMapper.createDTO(coursesGroup);
            CourseDTO courseDTO = modelMapper.createDTO(this, courseGroupDTO);
            classesDTO.setCourse(courseDTO);
            classesDTOs.add(classesDTO);
        }
    }
    
}
