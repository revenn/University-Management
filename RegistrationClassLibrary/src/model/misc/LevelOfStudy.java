package model.misc;

public enum LevelOfStudy {

    ENGINEER ("Inzynierskie"),
    MASTER("Magisterskie");

    private String name;

    LevelOfStudy(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
