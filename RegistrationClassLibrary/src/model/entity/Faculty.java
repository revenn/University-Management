package model.entity;

import controller.Factory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import model.dto.CourseDTO;
import model.generic.GenericUtils;

@Entity
public class Faculty implements Serializable {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String acronym;
    private String name;
    private String dean;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "faculty")
    private List<FieldOfStudy> fieldsOfStudy;

    public Faculty() {}
    
    public Faculty(String acronym, String name, String dean) {
        this.acronym = acronym;
        this.name = name;
        this.dean = dean;
        fieldsOfStudy = new ArrayList<>();
    }

    public boolean addFieldOfStudy(FieldOfStudy fieldOfStudy) {
        if(GenericUtils.addToCollection(fieldsOfStudy, fieldOfStudy)) {
            fieldOfStudy.setFaculty(this);
            return true;
        }
        return false;
    }

    public boolean addSpecialty(Specialty specialty) {
        return Optional
                .ofNullable(searchFieldOfStudy(
                        specialty.getFieldOfStudy()))
                .map(fieldOfStudy -> fieldOfStudy
                        .addSpecialty(specialty))
                .orElse(false);
    }

    public boolean addCoursesGroup(CoursesGroup coursesGroup) {
        boolean result = false; 
        
        FieldOfStudy fieldOfStudyPattern = coursesGroup.getFieldOfStudy(); 
        FieldOfStudy fieldOfStudy; 
        fieldOfStudy = this.searchFieldOfStudy(fieldOfStudyPattern); 
        
        if(fieldOfStudy == null){ 
            return result; 
        } 
        
        result = fieldOfStudy.addCoursesGroup(coursesGroup); 
        
        return result; 
    }
    
    public boolean addCoursesGroup(CoursesGroup coursesGroup, List<Object[]> partialCourses) {
        boolean result = false;
        
        Factory factory = new Factory();
        FieldOfStudy fieldOfStudyPattern = coursesGroup.getFieldOfStudy(); 
        FieldOfStudy fieldOfStudy; 
        fieldOfStudy = this.searchFieldOfStudy(fieldOfStudyPattern); 
        
        if(fieldOfStudy == null){ 
            return result; 
        } 
        
        for (Object[] partialCourse : partialCourses) {
            Course newPartialCourse = factory.createCourse(partialCourse, coursesGroup);
            coursesGroup.addPartialCourse(newPartialCourse);
        }
        
        result = fieldOfStudy.addCoursesGroup(coursesGroup);
        
        return result; 
    }

    public boolean addCourse(Course course) {
        CoursesGroup coursesGroup = course.getCoursesGroup();

        return Optional.ofNullable( searchFieldOfStudy(coursesGroup.getFieldOfStudy()) )
                .map( fieldOfStudy -> fieldOfStudy.addCourse(course) )
                .orElse(false);
    }

    public FieldOfStudy searchFieldOfStudy(FieldOfStudy fieldOfStudyTemplate) {
        return GenericUtils.searchInList(fieldsOfStudy, fieldOfStudyTemplate);
    }

    public List<FieldOfStudy> getFieldsOfStudy() {
        return fieldsOfStudy;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Faculty other = (Faculty) obj;

        if(!other.getAcronym().equals(acronym))
            return false;
        if(!other.getName().equals(name))
            return false;
        if(!other.getDean().equals(dean))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.acronym);
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.dean);
        return hash;
    }

    public String[] createStringArray() {
        String [] data = new String[3];
        data[0] = acronym;
        data[1] = name;
        data[2] = dean;
        return data;
    }

    public void createStringClasses(ArrayList<ArrayList<String>> allClasses) {
        for(FieldOfStudy f : fieldsOfStudy) {
            f.createStringClasses(allClasses);
        }
    }

    public void getCourseDTOs(List<CourseDTO> courseDTOs) {
        for(FieldOfStudy f : fieldsOfStudy) {
             f.getCourseDTOs(courseDTOs);
        }
    }

    public boolean checkTerm(Term term) {
        for(FieldOfStudy f : fieldsOfStudy) {
            if(!f.checkTerm(term)){
                return false;
            }
    }
        return true;
    }

    public boolean addClasses(Classes classes, Lecturer lecturer) {
        boolean result = false;
        Course coursePattern = classes.getCourse();
        CoursesGroup courseGroupPattern = coursePattern.getCoursesGroup();
        FieldOfStudy fieldOfStudyPattern = courseGroupPattern.getFieldOfStudy();
        FieldOfStudy fieldOfStudy;
        fieldOfStudy = this.searchFieldOfStudy(fieldOfStudyPattern);
        if(fieldOfStudy == null){
            return result;
        }
        result = fieldOfStudy.addClasses(classes,lecturer);
        return result;
    }
}
