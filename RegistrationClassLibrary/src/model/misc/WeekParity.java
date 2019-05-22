package model.misc;

public enum WeekParity {

    EVEN ("Parzysty"),
    ODD ("Nieparzysty"),
    BOTH ("–");

    private final String name;

    WeekParity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
