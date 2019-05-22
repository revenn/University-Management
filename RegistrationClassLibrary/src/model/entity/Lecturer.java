package model.entity;

import model.generic.GenericUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lecturer extends Person{

    private String title;
    private List<Classes> classes;
    
    public Lecturer(String firstName, String lastName, String personalIdentityNumber, String address, String gender, String title) {
        super(firstName, lastName, personalIdentityNumber, address, gender);
        this.title = title;
        this.classes = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Classes> getClasses() {
        return classes;
    }

    public boolean addClasses(Classes classes) {
        if(searchClasses(classes) != null) {
            return false;
        }
        
        Term term = classes.getTerm();
        if(!checkTermLecturer(term)) {
            return false;
        }
        this.classes.add(classes);
        return true;
    }

    private boolean checkTermLecturer(Term term) {
        for (Classes c : this.classes) {
            if (!c.checkTermLecturer(term)) {
                return false;
            }
        }
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
        
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.title);
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
        final Lecturer other = (Lecturer) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if(!super.equals(obj)) {
            return false;
        }
        return true;
    }
   
    @Override
    public String toString() {
        return  title + " " + firstName + " " + lastName;
    }
    
    public String[] creatrStringArray() {
        String[] data = new String[6];
        data[0] = firstName;
        data[1] = lastName;
        data[2] = personalIdentityNumber;
        data[3] = address;
        data[4] = gender;
        data[5] = title;
        return data;
    }

    public Classes searchClasses(Classes classesTemplate) {
        return GenericUtils.searchInList(classes, classesTemplate);
    }
}

