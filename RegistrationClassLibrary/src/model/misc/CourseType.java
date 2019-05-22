package model.misc;

public enum CourseType {

    NONE ("-"),
    LECTURE ("Wyklad"),
    EXERCISES ("Cwiczenia"),
    LABORATORIES ("Laboratoria"),
    PROJECT ("Projekt"),
    SEMINAR ("Seminarium");


    private final String name;

    CourseType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
