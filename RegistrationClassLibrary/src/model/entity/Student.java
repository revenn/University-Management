package model.entity;

import model.dto.ClassesDTO;
import model.generic.GenericUtils;

import java.util.*;

public class Student extends Person {

    private String indexNumber;
    private FieldOfStudy fieldOfStudy;
    private Specialty specialty;
    private int semester;
    private List<Classes> classes;
    private List<CoursesGroup> schedule;
    
    public Student(String indexNumber){
        super();
        this.indexNumber = indexNumber;
    }
    
    public Student(String indexNumber, String firstName, String lastName, String personalIdentityNumber, String address, String gender) {
        super(firstName, lastName, personalIdentityNumber, address, gender);
        this.indexNumber = indexNumber;
        this.fieldOfStudy = null;
        this.specialty = null;
        this.semester = 0;
        this.classes = new ArrayList<>();
        this.schedule = new ArrayList<>();
    }
 
    public String getIndexNumber() {
        return indexNumber;
    }
   
    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Classes> getClasses() {
        return classes;
    }
    
    public boolean addClasses(Classes classes) {
        if ( this.classes.contains(classes) ) {
            return false;
        } else {
            this.classes.add(classes);
            return true;
        }
    }

    public boolean addClass(Classes classesPattern) {
        Classes classes = null;
        for(CoursesGroup group : schedule) {
            classes = group.searchClasses(classesPattern);
            if(classes != null) {
                break;
            }
        }
        if(classes == null) {
            return false;
             
        }
        
        if(searchClasses(classes) != null) {
            return false;
        } 
        if(!checkTerm(classes.getTerm())){
            return false;
        }
        if(!checkNumberOfClasses()){
            return false;
        }
        if(!checkFreePlaces(classes)) {
            return false;
        }
        classes.decrementFreePlaces();
        this.classes.add(classes);
        classes.addStudent(this);
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public void setPersonalIdentityNumber(String personalIdentityNumber) {
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public List<CoursesGroup> getSchedule(){
        return this.schedule;
    }
    

    @Override
    public String toString() {
        return "Student{" + "indexNumber=" + indexNumber + ", fieldOfStudy=" + fieldOfStudy + ", specialty=" + specialty + ", semester=" + semester + ", classes=" + classes + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.indexNumber);
        hash = 79 * hash + Objects.hashCode(this.fieldOfStudy);
        hash = 79 * hash + Objects.hashCode(this.specialty);
        hash = 79 * hash + this.semester;
        hash = 79 * hash + Objects.hashCode(this.classes);
        return hash;
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
        final Student other = (Student) obj;
        if (this.semester != other.semester) {
            return false;
        }
        if (!Objects.equals(this.indexNumber, other.indexNumber)) {
            return false;
        }
        return true;
    }
    
    public String[] createStringArray() {
        String [] data = new String[]
            {
                indexNumber,
                firstName,
                lastName,
                personalIdentityNumber,
                address,
                gender,
                Integer.toString(semester)
            };

        return data;
    }   

    public boolean addCoursesGroupToSchedule(CoursesGroup coursesGroup) {
         return GenericUtils.addToCollection(schedule, coursesGroup);
    }
    
    CoursesGroup searchCoursesGroup(CoursesGroup coursesGroupTemplate) {
        return GenericUtils.searchInList(schedule, coursesGroupTemplate);
    }

    public void createStringScheduleForAll(ArrayList<ArrayList<String>> allSchedule) {
        for(CoursesGroup c : schedule) {
            ArrayList<String> data = new ArrayList();
            data.add(indexNumber);
            data.add(c.getName());
            data.add(Integer.toString(c.getEcts()));
            data.add(Integer.toString(c.getSemester()));
            data.add(c.getMainCourse().getCourseType().toString());
            c.createStringCourses(data);
            allSchedule.add(data);
        }
    }
    
    public void createStringSchedule(ArrayList<ArrayList<String>> schedule) {
        for (CoursesGroup c : this.schedule) {
            ArrayList<String> data = new ArrayList();
            data.add(c.getName());
            data.add(Integer.toString(c.getEcts()));
            data.add(Integer.toString(c.getSemester()));
            data.add(c.getMainCourse().getCourseType().toString());
            c.createStringCourses(data);
            schedule.add(data);
        }
    }
        
    public boolean checkNumberOfClasses(){
        ArrayList<Course> courseFromSchedule = new ArrayList();
        for(CoursesGroup group : schedule) {
            group.addPartialCourseToList(courseFromSchedule);
            courseFromSchedule.add(group.getMainCourse());
        }
        Integer numberOfClasses = classes.size();
        if(numberOfClasses >= courseFromSchedule.size()) {
            return false;
        }
        return true;
  
    }
    
    public boolean checkTerm(Term term) {
        for(Classes c : classes) {
            if(!term.checkTerm(c.getTerm())){
                return false;
            }
        }
        return true;
    }
    

    private Classes searchClasses(Classes classesTemplate) {
        return GenericUtils.searchInList(classes, classesTemplate);
    }

    public void createStringClasses(ArrayList<ArrayList<String>> classes) {
        for(Classes c : this.classes){
            c.createStringClasses(classes);
        }
    }

    public void getClassesDTOs(List<ClassesDTO> classesDTOs) {
        for(CoursesGroup group : schedule){
            group.getClassesDTOs(classesDTOs);
        }
    }

    private boolean checkFreePlaces(Classes classes) {
        return classes.checkFreePlaces();
    }

    public String getMostChosenClassesTerm() {
        Map<String, Integer> countedTerms = new HashMap<>();

        classes
            .stream()
            .map(Classes::getTermTime)
            .map(Arrays::toString)
            .forEach( time -> countedTerms.merge(time, 1, (count, increment) -> count + increment) );

        Map.Entry<String, Integer> mostChosenTerm = countedTerms
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Integer::compareTo))
                .get();

        return mostChosenTerm.getKey();
    }
}
