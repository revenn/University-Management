package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Classes {
   
    private Course course;
    private Lecturer lecturer;
    private Term term;
    private List<Student> students;
    private int freePlaces; 
    
    public Classes() {
    }

    public Classes(Course course, Lecturer lecturer, Term term) {
        this.course = course;
        this.lecturer = lecturer;
        this.term = term;
        this.students = new ArrayList<>();
        freePlaces = term.getHall().getSize();
    }
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
    
    public List<Student> getStudents() {
        return this.students;
    }
    
    public void addStudent(Student student) {
        this.students.add(student);
    }
    
    public int getFreeSeatsAmount() {
        return this.term.getHall().getSize() - this.students.size();
    }

    @Override
    public String toString() {
        return "Class{" + "course=" + course + ", lecturer=" + lecturer + ", term=" + term + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.course);
        hash = 47 * hash + Objects.hashCode(this.lecturer);
        hash = 47 * hash + Objects.hashCode(this.term);
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
        final Classes other = (Classes) obj;
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.lecturer, other.lecturer)) {
            return false;
        }
        if (!Objects.equals(this.term, other.term)) {
            return false;
        }
        return true;
    }

    void createStringClasses(ArrayList<ArrayList<String>> allClasses, FieldOfStudy fieldOfStudy) {
        ArrayList<String> data = new ArrayList();
        Faculty faculty = fieldOfStudy.getFaculty();
        String facultyAcronym = faculty.getAcronym();
        String fieldOfStudyAcronym = fieldOfStudy.getAcronym();
        
        data.add(facultyAcronym);
        data.add(fieldOfStudyAcronym);
        data.add(course.getName() +" "+course.getCourseType().toString());
        data.add(lecturer.toString());
        data.add(term.getHall().toString());
        data.add(term.toString());

        allClasses.add(data);
    }

    boolean checkTerm(Term term) {
        if(this.term.equals(term)){
            return false;
        }
        return true;
    }

    boolean checkTermLecturer(Term term) {
        return this.term.checkTerm(term);
    }

    void createStringClasses(ArrayList<ArrayList<String>> classes) {
        ArrayList<String> data = new ArrayList<>();
        
        data.add(course.getName() + " " + course.getCourseType().toString());
        data.add(lecturer.toString());
        data.add(term.getHall().toString());
        data.add(term.toString());

        classes.add(data);
    }

    void decrementFreePlaces() {
        freePlaces--;
    }

    boolean checkFreePlaces() {
        return freePlaces > 0;
    }

    public Integer[] getTermTime() {
        return term.getTimeTable();
    }
}
