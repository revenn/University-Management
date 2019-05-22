package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.dto.ClassesDTO;
import model.dto.CourseDTO;
import model.dto.CoursesGroupDTO;
import util.ModelMapper;

public class CoursesGroup {

    private String name;
    private Course mainCourse;
    private List<Course> partialCourses;
    private int ects;
    private int semester;
    private FieldOfStudy fieldOfStudy;

    public CoursesGroup() {
        this.name = "";
        this.partialCourses = new ArrayList<>();
        
    }
    
    public CoursesGroup(String name, Course mainCourse, int ects, int semester, FieldOfStudy fieldOfStudy) {
        this.name = name;
        this.mainCourse = mainCourse;
        this.ects = ects;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
        this.partialCourses = new ArrayList<>();
    }
    
    public CoursesGroup(String name, int ects, int semester, FieldOfStudy fieldOfStudy) {
        this.name = name;
        this.mainCourse = null;
        this.ects = ects;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
        this.partialCourses = new ArrayList<>();
    }

    public CoursesGroup(String name, int ects, int semester) {
        this.name = name;
        this.mainCourse = null;
        this.ects = ects;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
        this.partialCourses = new ArrayList<>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Course getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(Course mainCourse) {
        this.mainCourse = mainCourse;
    }

    public List<Course> getPartialCourses() {
        return partialCourses;
    }

    public String getPartialCoursesTypesAsString() {
        StringBuilder builder = new StringBuilder();
        this.partialCourses.forEach(course -> builder.append(course.getCourseType().toString() + ", "));
      
        return builder.toString();
    }
    
    public boolean addPartialCourse(Course course) {
        if ( !this.partialCourses.contains(course) && !course.equals(this.mainCourse) ) {
            course.setCoursesGroup(this);
            this.partialCourses.add(course);
            return true;
        }
        return false;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }
    
    public int getSemester() {
        return this.semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>(partialCourses);
        courses.add(mainCourse);
        return courses;
    }

    public FieldOfStudy getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    @Override
    public String toString() {
        return "CoursesGroup{" + "mainCourse=" + mainCourse + ", partialCourses=" + partialCourses + ", ects=" + ects + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.ects;
        hash = 59 * hash + this.semester;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoursesGroup that = (CoursesGroup) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return fieldOfStudy != null ? fieldOfStudy.equals(that.fieldOfStudy) : that.fieldOfStudy == null;
    }
    
    void createStringCourses(ArrayList<String> data) {
        String courses="";
        for(Course c : partialCourses){
            courses+=c.getCourseType().toString();
            courses += ", ";
        }       
        data.add(courses);
    }
    
    void addPartialCourseToList(ArrayList<Course> list){
        for(Course c : partialCourses){
            list.add(c);
        }
    }

    public boolean addClasses(Classes classes, Lecturer lecturer) {
       Course coursePattern = classes.getCourse();
       Course course = searchCourse(coursePattern);
       if(course == null) {
           return false;
       }
       return course.addClasses(classes,lecturer);
    }
    
    public Course searchCourse(Course course) {
        if(mainCourse.equals(course)) {
            return mainCourse;
        }
        
        int index = partialCourses.indexOf(course);

        return index != -1 ? partialCourses.get(index) : null;
    }

    void createStringClasses(ArrayList<ArrayList<String>> allClasses) {
        mainCourse.createStringClasses(allClasses, fieldOfStudy);
        for(Course c : partialCourses) {
            c.createStringClasses(allClasses, fieldOfStudy);
        }
    }

    public void getCourseDTOs(List<CourseDTO> courseDTOs) {
        ModelMapper modelMapper = new ModelMapper();
        CoursesGroupDTO coursesGroupDTO = modelMapper.createDTO(this);
        courseDTOs.add(coursesGroupDTO.getMainCourseDTO());

        for(Course c : partialCourses) {
            CourseDTO courseDTO  = modelMapper.createDTO(c,coursesGroupDTO);
            courseDTOs.add(courseDTO);
        }
    }

    boolean checkTerm(Term term) {
        if(!mainCourse.checkTerm(term)) {
            return false;
        }
        for(Course c : partialCourses) {
            if(!c.checkTerm(term)) {
                return false;
        }
    }
        return true;
    }

    Classes searchClasses(Classes classesPattern) {
        Classes classes;
        classes = mainCourse.searchClasses(classesPattern);
        if(classes != null){
            return classes;
        }
        
        for(Course course : partialCourses) {
            classes = course.searchClasses(classesPattern);
            if(classes != null) {
                break;
            }
        }
        return classes;
    }

    void getClassesDTOs(List<ClassesDTO> classesDTOs) {
        mainCourse.getClassesDTOs(classesDTOs);
        for(Course c : partialCourses){
            c.getClassesDTOs(classesDTOs);
        }
    }

}
