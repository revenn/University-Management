package model.misc;

public enum ModeOfStudy {
    FULL_TIME ("Stacjonarne"),
    EXTRAMURAL ("Niestacjonarne");

    private String name;

    ModeOfStudy(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
