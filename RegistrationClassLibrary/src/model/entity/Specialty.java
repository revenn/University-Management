package model.entity;

public class Specialty {

    private String acronym;
    private String name;
    private FieldOfStudy fieldOfStudy;

    public Specialty() {
    }

    public Specialty(String acronym, String name, FieldOfStudy fieldOfStudy) {
        this.acronym = acronym;
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
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
    
    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        if (acronym != null ? !acronym.equals(specialty.acronym) : specialty.acronym != null) return false;
        if (name != null ? !name.equals(specialty.name) : specialty.name != null) return false;
        return fieldOfStudy != null ? fieldOfStudy.equals(specialty.fieldOfStudy) : specialty.fieldOfStudy == null;
    }

    @Override
    public int hashCode() {
        int result = acronym != null ? acronym.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fieldOfStudy != null ? fieldOfStudy.hashCode() : 0);
        return result;
    }
}
