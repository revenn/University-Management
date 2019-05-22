package model.entity;

import java.io.Serializable;

import model.generic.GenericUtils;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import model.dto.CourseDTO;

@Entity
public class FieldOfStudy implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    private String acronym;
    private String name;
    private LevelOfStudy level;
    private ModeOfStudy mode;
    @ManyToOne
    private Faculty faculty;
    @Transient
    private List<Specialty> specialties;
    @Transient
    private List<CoursesGroup> coursesGroups;

    public FieldOfStudy() {
        specialties = new ArrayList<>();
        coursesGroups = new ArrayList<>();
    }

    public FieldOfStudy(String acronym, String name, LevelOfStudy level, ModeOfStudy mode, Faculty faculty) {
        this();
        this.acronym = acronym;
        this.name = name;
        this.level = level;
        this.mode = mode;
        this.faculty = faculty;
    }

    public boolean addSpecialty(Specialty specialty) {
        if (GenericUtils.addToCollection(specialties, specialty)) {
            specialty.setFieldOfStudy(this);
            return true;
        }
        return false;
    }

    public boolean addCoursesGroup(CoursesGroup coursesGroup) {
        if (GenericUtils.addToCollection(coursesGroups, coursesGroup)) {
            coursesGroup.setFieldOfStudy(this);
            return true;
        }
        return false;
    }

    public boolean addCourse(Course course) {
        return Optional.ofNullable( searchCourseGroup(course.getCoursesGroup()) )
                .map( coursesGroup -> coursesGroup.addPartialCourse(course) )
                .orElse(false);
    }

    public CoursesGroup searchCourseGroup(CoursesGroup coursesGroupTemplate) {
        return GenericUtils.searchInList(coursesGroups, coursesGroupTemplate);
    }

    public List<CoursesGroup> getCoursesGroups() {
        return coursesGroups;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode(){
        return faculty.getAcronym() + " | " + name + " | " + level + " | " + mode + " | ";
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LevelOfStudy getLevel() {
        return level;
    }

    public void setLevel(LevelOfStudy level) {
        this.level = level;
    }

    public ModeOfStudy getMode() {
        return mode;
    }

    public void setMode(ModeOfStudy mode) {
        this.mode = mode;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    @Override
    public String toString() {
        return "FieldOfStudy{" +
                "acronym='" + acronym + '\'' +
                ", name='" + name + '\'' +
                ", faculty=" + faculty +
                ", specialties=" + specialties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldOfStudy that = (FieldOfStudy) o;

        if (acronym != null ? !acronym.equals(that.acronym) : that.acronym != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (mode != null ? !mode.equals(that.mode) : that.mode != null) return false;
        return faculty != null ? faculty.equals(that.faculty) : that.faculty == null;
    }

    public void generateStudentSchedule(Student student) {
        for (CoursesGroup c : coursesGroups) {
            student.addCoursesGroupToSchedule(c);
        }
    }

    public boolean addClasses(Classes classes, Lecturer lecturer) {
        Course coursePattern = classes.getCourse();
        CoursesGroup coursesGroupPattern = coursePattern.getCoursesGroup();

        CoursesGroup courseGroup = this.searchCourseGroup(coursesGroupPattern);
        if(courseGroup == null) {
            return false;
        }
        return courseGroup.addClasses(classes,lecturer);
    }

    void createStringClasses(ArrayList<ArrayList<String>> allClasses) {
        for(CoursesGroup group : coursesGroups){
            group.createStringClasses(allClasses);
        }
    }

    public void getCourseDTOs(List<CourseDTO> courseDTOs) {
        for(CoursesGroup group : coursesGroups) {
            group.getCourseDTOs(courseDTOs);
        }
    }

    boolean checkTerm(Term term) {
        for(CoursesGroup group : coursesGroups) {
           if(!group.checkTerm(term)){
               return false;
           }
        }
        return true;
    }
}
